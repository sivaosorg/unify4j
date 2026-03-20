package org.unify4j.model.builder;

import org.w3c.dom.*;

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.*;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.StringWriter;

public class SoapXmlBuilder {
    private final Document doc;
    private Element current;

    /**
     * Constructs a new `SoapXmlBuilder` with the specified root element name.
     *
     * @param rootName the name of the root element
     * @throws RuntimeException if an error occurs while creating the XML document
     */
    public SoapXmlBuilder(String rootName) {
        try {
            doc = DocumentBuilderFactory.newInstance()
                    .newDocumentBuilder()
                    .newDocument();

            Element root = doc.createElement(rootName);
            doc.appendChild(root);
            current = root;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Creates a new `SoapXmlBuilder` instance with the specified root element name.
     *
     * @param root the name of the root element
     * @return a new `SoapXmlBuilder` instance, class {@link SoapXmlBuilder}
     */
    public static SoapXmlBuilder create(String root) {
        return new SoapXmlBuilder(root);
    }

    /**
     * Adds an attribute to the current XML element.
     *
     * @param name  the name of the attribute
     * @param value the value of the attribute
     * @return the current `SoapXmlBuilder` instance, class {@link SoapXmlBuilder}
     */
    public SoapXmlBuilder attr(String name, String value) {
        current.setAttribute(name, value);
        return this;
    }

    /**
     * Adds a child element to the current XML element and sets it as the current element.
     *
     * @param name the name of the child element
     * @return the current `SoapXmlBuilder` instance, class {@link SoapXmlBuilder}
     */
    public SoapXmlBuilder child(String name) {
        Element child = doc.createElement(name);
        current.appendChild(child);
        current = child;
        return this;
    }

    /**
     * Adds a child element with text content to the current XML element.
     *
     * @param name the name of the child element
     * @param text the text content of the child element
     * @return the current `SoapXmlBuilder` instance, class {@link SoapXmlBuilder}
     */
    public SoapXmlBuilder child(String name, String text) {
        Element child = doc.createElement(name);
        child.setTextContent(text);
        current.appendChild(child);
        return this;
    }

    /**
     * Sets the text content of the current XML element.
     *
     * @param text the text content to set
     * @return the current `SoapXmlBuilder` instance, class {@link SoapXmlBuilder}
     */
    public SoapXmlBuilder text(String text) {
        current.setTextContent(text);
        return this;
    }

    /**
     * Moves up to the parent of the current XML element.
     *
     * @return the current `SoapXmlBuilder` instance, class {@link SoapXmlBuilder}
     */
    public SoapXmlBuilder up() {
        current = (Element) current.getParentNode();
        return this;
    }

    /**
     * Builds the XML document as a formatted string.
     *
     * @return the XML document as a string
     * @throws RuntimeException if an error occurs during the transformation
     */
    public String build() {
        try {
            Transformer transformer = TransformerFactory.newInstance().newTransformer();
            transformer.setOutputProperty(OutputKeys.INDENT, "yes");

            StringWriter writer = new StringWriter();
            transformer.transform(new DOMSource(doc), new StreamResult(writer));

            return writer.toString();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}