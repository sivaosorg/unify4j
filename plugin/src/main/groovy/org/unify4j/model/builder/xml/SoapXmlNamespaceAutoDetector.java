package org.unify4j.model.builder.xml;

import org.w3c.dom.*;

import javax.xml.parsers.DocumentBuilderFactory;
import java.io.StringReader;
import java.util.HashMap;
import java.util.Map;

import org.xml.sax.InputSource;

@SuppressWarnings({"SpellCheckingInspection", "ConstantConditions"})
public class SoapXmlNamespaceAutoDetector {

    /**
     * Parses XML and extracts namespace mappings.
     *
     * @param xml raw XML string
     * @return map of prefix -> namespace URI
     */
    public static Map<String, String> detect(String xml) {
        try {
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            factory.setNamespaceAware(true);

            Document doc = factory.newDocumentBuilder()
                    .parse(new InputSource(new StringReader(xml)));

            Element root = doc.getDocumentElement();

            Map<String, String> namespaceMap = new HashMap<>();

            // Extract namespace declarations from root element
            NamedNodeMap attributes = root.getAttributes();

            for (int i = 0; i < attributes.getLength(); i++) {
                Node attr = attributes.item(i);

                String name = attr.getNodeName();
                String value = attr.getNodeValue();

                // Handle xmlns:prefix="URI"
                if (name.startsWith("xmlns:")) {
                    String prefix = name.substring(6);
                    namespaceMap.put(prefix, value);
                }

                // Handle default namespace xmlns="URI"
                else if (name.equals("xmlns")) {
                    namespaceMap.put("default", value);
                }
            }

            return namespaceMap;

        } catch (Exception e) {
            throw new RuntimeException("Failed to detect XML namespaces", e);
        }
    }
}