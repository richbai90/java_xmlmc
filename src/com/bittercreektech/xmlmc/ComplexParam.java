package com.bittercreektech.xmlmc;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

/**
 * ComplexParam.java
 * <p>
 * Create a complex xml structure for params that require it. When converted to xml the structure looks like this
 * <pre>{@code <param>
 *     <child>
 *         <subchild>
 *             Value
 *         </subchild>
 *     </child>
 * </param>}</pre>
 * <p>
 * The complex param can have as many subchildren as are required.
 */
public class ComplexParam {
    private Element root;
    private Element param;
    private Document xml;
    private Element params;

    /**
     * Create the complex param object
     *
     * @param param Param name to be created
     * @throws ParserConfigurationException
     */
    public ComplexParam(String param) throws ParserConfigurationException {
        DocumentBuilderFactory builderFactory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder = null;
        builder = builderFactory.newDocumentBuilder();

        if (builder != null) {
            xml = builder.newDocument();

            //create the root element
            root = xml.createElement(param);
            xml.appendChild(root);
            params = xml.getDocumentElement();
            this.param = params;

        }
    }

    /**
     * Create the complex param object and append it to an existing xml structure
     *
     * @param param Name of the parameter to create
     * @param xml   The document to append this parameter to
     */
    public ComplexParam(String param, Document xml) {
        this.param = xml.createElement(param);
        this.xml = xml;
        root = xml.getDocumentElement();
        params = (Element) root.getFirstChild();
    }

    /**
     * For internal use only. Use this constructor to build on top of an existing complex param
     *
     * @param param The xml element that should be built onto
     * @param xml   The xml document to append the element to
     */
    private ComplexParam(Element param, Document xml) {
        this.param = param;
        this.xml = xml;
        root = xml.getDocumentElement();
        params = (Element) root.getFirstChild();
        //xml.appendChild(this.attr);
    }

    /**
     * Return the document root
     *
     * @return document root
     */
    public Element getRoot() {
        return root;
    }

    public Node getRootNode() {
        return (Node) root;
    }

    /**
     * Return the parameter
     *
     * @return the xmlElement created by this ComplexParam
     */
    public Element getParam() {
        return param;
    }

    /**
     * Append the complex param to the document
     */
    public void update() {
        if (!(params == param)) {
            params.appendChild(param);
        }
    }

    /**
     * Create a child element to the complex param
     *
     * @param nodeName Name of the child
     * @return New ComplexParam
     */
    public ComplexParam createChild(String nodeName) {
        Element child = xml.createElement(nodeName);
        Node lastChild = param.getLastChild();
        if (param.getLastChild() != null) {
            param.getLastChild().appendChild(child);
        } else {
            param.appendChild(child);
        }

        return new ComplexParam(param, xml);
    }

    /**
     * Create a child element to the complex param with a text value
     *
     * @param nodeName Name of the child
     * @param nodeValue Text Content of the node
     * @return New ComplexParam
     */
    public ComplexParam createChild(String nodeName, String nodeValue) {
        Element child = xml.createElement(nodeName);
        child.setTextContent(nodeValue);
        Node lastChild = param.getLastChild();
        if (param.getLastChild() != null) {
            param.getLastChild().appendChild(child);
        } else {
            param.appendChild(child);
        }

        return new ComplexParam(param, xml);
    }


    /**
     * Set an attribute on the parameter
     *
     * @param name  Name of the attribute to set
     * @param value Value of the attribute
     * @return new ComplexParam
     */
    public ComplexParam setNodeAttribute(String name, String value) {
        param.setAttribute(name, value);
        return new ComplexParam(param, xml);
    }


    /**
     * Remove an attribute on the parameter
     *
     * @param name name of attribute to be removed
     * @return new ComplexParam
     */
    public ComplexParam removeNodeAttribute(String name) {
        param.removeAttribute(name);
        return new ComplexParam(param, xml);
    }

    /**
     * Add an un-nested child to the current root.
     */
    public ComplexParam addParameter(String paramName, String value) {
        Element parameter = xml.createElement(paramName);
        parameter.setTextContent(value);
        param.appendChild(parameter);
        return new ComplexParam(param,xml);
    }

    public void addComplexParameter(String parent, ComplexParam parameter) {
        Node param = xml.importNode(parameter.getRootNode(), true);
        Element el_parent = xml.createElement(parent);
        el_parent.appendChild(param);
        this.param.appendChild(el_parent);
    }

    public void addComplexParameter(ComplexParam parameter) {
        Node param = xml.importNode(parameter.getRootNode(), true);
        this.param.appendChild(param);
    }

    @Override
    public String toString() {
        return Helpers.getXmlString(xml);
    }


}