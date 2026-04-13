package org.unify4j.model.builder;

import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.xpath.*;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.List;

/**
 * Utility class for extracting values from XML using XPath expressions.
 * Designed to work fluently with SOAP XML responses.
 */
public class SoapXmlValueBuilder {

    private final Document document;
    private final XPath xpath;

    /**
     * Private constructor to initialize XML Document and XPath engine.
     *
     * @param xml the raw XML string
     */
    private SoapXmlValueBuilder(String xml) {
        try {
            this.document = DocumentBuilderFactory.newInstance()
                    .newDocumentBuilder()
                    .parse(new InputSource(new StringReader(xml)));

            this.xpath = XPathFactory.newInstance().newXPath();
        } catch (Exception e) {
            throw new RuntimeException("Failed to parse XML", e);
        }
    }

    /**
     * Creates a new instance from XML string.
     *
     * @param xml the raw XML response
     * @return SoapXmlValueBuilder instance
     */
    public static SoapXmlValueBuilder from(String xml) {
        return new SoapXmlValueBuilder(xml);
    }

    /**
     * Extracts a single string value using XPath expression.
     *
     * @param expression XPath expression
     * @return extracted string value
     */
    public String get(String expression) {
        try {
            return xpath.evaluate(expression, document);
        } catch (XPathExpressionException e) {
            throw new RuntimeException("XPath evaluation failed", e);
        }
    }

    /**
     * Extracts a single Node using XPath expression.
     *
     * @param expression XPath expression
     * @return Node result or null if not found
     */
    public Node getNode(String expression) {
        try {
            return (Node) xpath.evaluate(expression, document, XPathConstants.NODE);
        } catch (XPathExpressionException e) {
            throw new RuntimeException("XPath evaluation failed", e);
        }
    }

    /**
     * Extracts a list of string values using XPath expression.
     *
     * @param expression XPath expression
     * @return list of extracted values
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
            throw new RuntimeException("XPath evaluation failed", e);
        }
    }

    /**
     * Extracts an attribute value using XPath expression.
     * <p>
     * Example: //user/@id
     *
     * @param expression XPath expression
     * @return attribute value
     */
    public String getAttr(String expression) {
        return get(expression);
    }

    /**
     * Checks whether a node exists for the given XPath expression.
     *
     * @param expression XPath expression
     * @return true if node exists, otherwise false
     */
    public boolean exists(String expression) {
        return getNode(expression) != null;
    }
}