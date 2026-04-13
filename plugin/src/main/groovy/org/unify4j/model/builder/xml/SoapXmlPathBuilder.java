package org.unify4j.model.builder.xml;

import org.w3c.dom.*;
import org.xml.sax.InputSource;

import javax.xml.XMLConstants;
import javax.xml.namespace.NamespaceContext;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.xpath.*;
import java.io.StringReader;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * JsonPath-like XML query utility.
 * <p>
 * Supports:
 * - dot path: Body.Response.value
 * - index: items[0]
 * - wildcard: items[*]
 * - attribute filter: user[@id='1']
 * <p>
 * Namespace-aware with automatic fallback.
 */
public class SoapXmlPathBuilder {

    private final Document document;
    private final XPath xpath;
    private final SafeXPath safeXPath;
    private final String defaultPrefix;

    private SoapXmlPathBuilder(String xml, Map<String, String> ns) {
        try {
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            factory.setNamespaceAware(true);

            this.document = factory.newDocumentBuilder().parse(new InputSource(new StringReader(xml)));

            this.xpath = XPathFactory.newInstance().newXPath();

            if (ns != null && !ns.isEmpty()) {
                this.xpath.setNamespaceContext(new SimpleNamespaceContext(ns));
            }

            this.safeXPath = new SafeXPath(xpath, document);

            // choose default prefix (prefer non-soap)
            this.defaultPrefix = ns != null ? ns.keySet().stream().filter(p -> !"soap".equals(p)).findFirst().orElse("ns") : "ns";

        } catch (Exception e) {
            throw new RuntimeException("Failed to initialize XmlPath", e);
        }
    }

    public static SoapXmlPathBuilder of(String xml, Map<String, String> ns) {
        return new SoapXmlPathBuilder(xml, ns);
    }

    public static SoapXmlPathBuilder auto(String xml) {
        Map<String, String> ns = SoapXmlNamespaceAutoDetector.detect(xml);

        if (ns.containsKey("default")) {
            String uri = ns.remove("default");
            ns.put("ns", uri);
        }

        return new SoapXmlPathBuilder(xml, ns);
    }

    /**
     * Get single value.
     */
    public String get(String path) {
        String xpathExpr = compile(path);
        return safeXPath.eval(xpathExpr);
    }

    /**
     * Get list of values.
     */
    public List<String> getList(String path) {
        String xpathExpr = compile(path);

        try {
            NodeList nodes = (NodeList) xpath.evaluate(xpathExpr, document, XPathConstants.NODESET);
            List<String> result = new ArrayList<>();

            for (int i = 0; i < nodes.getLength(); i++) {
                result.add(nodes.item(i).getTextContent());
            }

            return result;
        } catch (XPathExpressionException e) {
            throw new RuntimeException("XPath list evaluation failed: " + xpathExpr, e);
        }
    }

    /**
     * Check existence.
     */
    public boolean exists(String path) {
        return !get(path).isEmpty();
    }

    /**
     * Compile DSL path to XPath.
     */
    private String compile(String path) {
        String[] parts = path.split("\\.");
        StringBuilder xpath = new StringBuilder("//");

        for (int i = 0; i < parts.length; i++) {
            if (i > 0) xpath.append("/");

            xpath.append(buildSegment(parts[i]));
        }

        return xpath.toString();
    }

    /**
     * Build XPath segment from DSL token.
     */
    private String buildSegment(String token) {

        // match patterns: name, name[0], name[*], name[@id='1']
        Pattern p = Pattern.compile("([a-zA-Z0-9_-]+)(\\[(.*?)])?");
        Matcher m = p.matcher(token);

        if (!m.matches()) {
            throw new IllegalArgumentException("Invalid path token: " + token);
        }

        String name = m.group(1);
        String bracket = m.group(3);

        String base = defaultPrefix + ":" + name;

        if (bracket == null) {
            return base;
        }

        // index: [0]
        if (bracket.matches("\\d+")) {
            int idx = Integer.parseInt(bracket) + 1; // XPath is 1-based
            return base + "[" + idx + "]";
        }

        // wildcard: [*]
        if ("*".equals(bracket)) {
            return base;
        }

        // attribute filter: [@id='1']
        if (bracket.startsWith("@")) {
            return base + "[" + bracket + "]";
        }

        throw new IllegalArgumentException("Unsupported token: " + token);
    }

    /**
     * Safe XPath wrapper with fallback.
     */
    protected static class SafeXPath {

        private final XPath xpath;
        private final Document document;

        public SafeXPath(XPath xpath, Document document) {
            this.xpath = xpath;
            this.document = document;
        }

        public String eval(String expr) {
            try {
                String result = xpath.evaluate(expr, document);
                if (result != null && !result.isEmpty()) {
                    return result;
                }
            } catch (Exception ignored) {
            }

            // fallback using local-name()
            String fallback = expr.replaceAll("([a-zA-Z0-9_-]+):([a-zA-Z0-9_-]+)", "*[local-name()='$2']");

            try {
                return xpath.evaluate(fallback, document);
            } catch (Exception e) {
                throw new RuntimeException("XPath failed: " + expr, e);
            }
        }
    }

    /**
     * NamespaceContext implementation.
     */
    protected static class SimpleNamespaceContext implements NamespaceContext {

        private final Map<String, String> map;

        public SimpleNamespaceContext(Map<String, String> map) {
            this.map = map;
        }

        @Override
        public String getNamespaceURI(String prefix) {
            return map.getOrDefault(prefix, XMLConstants.NULL_NS_URI);
        }

        @Override
        public String getPrefix(String uri) {
            return map.entrySet().stream().filter(e -> e.getValue().equals(uri)).map(Map.Entry::getKey).findFirst().orElse(null);
        }

        @Override
        public Iterator<String> getPrefixes(String uri) {
            return map.entrySet().stream().filter(e -> e.getValue().equals(uri)).map(Map.Entry::getKey).iterator();
        }
    }
}