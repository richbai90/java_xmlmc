import org.w3c.dom.*;

import javax.xml.parsers.ParserConfigurationException;

/**
 * ComplexParam.java
 *
 * Create a complex xml structure for params that require it. When converted to xml the structure looks like this
 * <pre>{@code <param>
 *     <child>
 *         <subchild>
 *             Value
 *         </subchild>
 *     </child>
 * </param>}</pre>
 *
 * The complex param can have as many subchildren as are required.
 */
class ComplexParam {
    private Element root;
    private Element param;
    private Document xml;
    private Element params;

    /**
     * Create the complex param object
     * @param param Name of the parameter to create
     * @param xml The document to append this parameter to
     */
    ComplexParam(String param, Document xml) {
        this.param = xml.createElement(param);
        this.xml = xml;
        root = xml.getDocumentElement();
        params = (Element) root.getFirstChild();
    }

    /**
     * For internal use only. Use this constructor to build on top of an existing complex param
     * @param param The xml element that should be built onto
     * @param xml The xml document to append the element to
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
     * @return document root
     */
    Element getRoot() {
        return root;
    }

    /**
     * Should really be named getParam
     * @return the xmlElement created by this ComplexParam
     */
    Element getAttr() {
        return param;
    }

    /**
     * Append the complex param to the document
     */
    void update() {
        params.appendChild(param);
    }

    /**
     * Create a child element to the complex param
     * @param nodeName Name of the child
     * @return New ComplexParam
     */
    ComplexParam createChild(String nodeName) {
        Element child = xml.createElement(nodeName);
        if (param.getLastChild() != null) {
            param.getLastChild().appendChild(child);
        } else {
            param.appendChild(child);
        }

        return new ComplexParam(param, xml);
    }


    /**
     * Set an attribute on the parameter
     * @param name Name of the attribute to set
     * @param value Value of the attribute
     * @return new ComplexParam
     */
    ComplexParam setNodeAttribute(String name, String value) {
        param.setAttribute(name, value);
        return new ComplexParam(param, xml);
    }


    /**
     * Remove an attribute on the parameter
     * @param name name of attribute to be removed
     * @return new ComplexParam
     */
    ComplexParam removeNodeAttribute(String name) {
        param.removeAttribute(name);
        return new ComplexParam(param, xml);
    }
}