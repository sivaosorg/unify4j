package org.unify4j.model.builder;

import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;

import javax.xml.XMLConstants;
import javax.xml.namespace.NamespaceContext;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.xpath.*;
import java.io.StringReader;
import java.util.*;

/**
 * Utility class for extracting values from XML using XPath with namespace support.
 * Designed for SOAP XML processing.
 */
public class SoapXmlValueBuilder {

    private final Document document;
    private final XPath xpath;

    /**
     * Private constructor.
     *
     * @param xml          raw XML string
     * @param namespaceMap prefix -> namespace URI mapping
     */
    protected SoapXmlValueBuilder(String xml, Map<String, String> namespaceMap) {
        try {
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            factory.setNamespaceAware(true);

            this.document = factory
                    .newDocumentBuilder()
                    .parse(new InputSource(new StringReader(xml)));

            this.xpath = XPathFactory.newInstance().newXPath();

            if (namespaceMap != null && !namespaceMap.isEmpty()) {
                this.xpath.setNamespaceContext(new SimpleNamespaceContext(namespaceMap));
            }

        } catch (Exception e) {
            throw new RuntimeException("Failed to parse XML", e);
        }
    }

    /**
     * Create builder without namespace support.
     */
    public static SoapXmlValueBuilder from(String xml) {
        return new SoapXmlValueBuilder(xml, null);
    }

    /**
     * Create builder with manual namespace mapping.
     */
    public static SoapXmlValueBuilder from(String xml, Map<String, String> namespaces) {
        return new SoapXmlValueBuilder(xml, namespaces);
    }

    /**
     * Create builder with auto-detected namespace mapping.
     */
    public static SoapXmlValueBuilder auto(String xml) {
        Map<String, String> ns = SoapXmlNamespaceAutoDetector.detect(xml);

        // Fix default namespace (assign a usable prefix)
        if (ns.containsKey("default")) {
            String uri = ns.remove("default");
            ns.put("ns", uri);
        }

        return new SoapXmlValueBuilder(xml, ns);
    }

    /**
     * Extract a single string value using XPath.
     *
     * @param expression XPath expression
     * @return extracted value
     */
    public String get(String expression) {
        try {
            return xpath.evaluate(expression, document);
        } catch (XPathExpressionException e) {
            throw new RuntimeException("XPath evaluation failed: " + expression, e);
        }
    }

    /**
     * Extract a single node using XPath.
     *
     * @param expression XPath expression
     * @return Node or null
     */
    public Node getNode(String expression) {
        try {
            return (Node) xpath.evaluate(expression, document, XPathConstants.NODE);
        } catch (XPathExpressionException e) {
            throw new RuntimeException("XPath evaluation failed: " + expression, e);
        }
    }

    /**
     * Extract multiple string values using XPath.
     *
     * @param expression XPath expression
     * @return list of values
     */
    public List<String> getList(String expression) {
        try {
            NodeList nodes = (NodeList) xpath.evaluate(expression, document, XPathConstants.NODESET);
            List<String> result = new ArrayList<>();

            for (int i = 0; i < nodes.getLength(); i++) {
                result.add(nodes.item(i).getTextContent());
            }

            return result;
        } catch (XPathExpressionException e) {
            throw new RuntimeException("XPath evaluation failed: " + expression, e);
        }
    }

    /**
     * Extract attribute value using XPath.
     * <p>
     * Example: //user/@id
     */
    public String getAttr(String expression) {
        return get(expression);
    }

    /**
     * Check if a node exists.
     *
     * @param expression XPath expression
     * @return true if exists
     */
    public boolean exists(String expression) {
        return getNode(expression) != null;
    }

    /**
     * Internal NamespaceContext implementation.
     */
    protected static class SimpleNamespaceContext implements NamespaceContext {

        private final Map<String, String> prefixMap;

        public SimpleNamespaceContext(Map<String, String> prefixMap) {
            this.prefixMap = prefixMap;
        }

        @Override
        public String getNamespaceURI(String prefix) {
            if (prefix == null) {
                throw new IllegalArgumentException("Prefix cannot be null");
            }
            return prefixMap.getOrDefault(prefix, XMLConstants.NULL_NS_URI);
        }

        @Override
        public String getPrefix(String namespaceURI) {
            if (namespaceURI == null) {
                return null;
            }
            return prefixMap.entrySet().stream()
                    .filter(e -> e.getValue().equals(namespaceURI))
                    .map(Map.Entry::getKey)
                    .findFirst()
                    .orElse(null);
        }

        @Override
        public Iterator<String> getPrefixes(String namespaceURI) {
            if (namespaceURI == null) {
                return Collections.emptyIterator();
            }
            return prefixMap.entrySet().stream()
                    .filter(e -> e.getValue().equals(namespaceURI))
                    .map(Map.Entry::getKey)
                    .iterator();
        }
    }
}