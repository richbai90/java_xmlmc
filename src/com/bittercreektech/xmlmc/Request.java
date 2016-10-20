package com.bittercreektech.xmlmc;

import com.bittercreektech.xmlmc.types.SwType;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

/**
 * Request.java
 * <p>
 * Represents a request to send to the server. Handles the building of xml in the appropriate structure through
 * simple method calls.
 */
public class Request {
    private Document xml;
    private Element root;
    private Element params;
    private boolean usesParams = false;

    /**
     * Generate a new request
     *
     * @param service api.Service of the xmlmc API to invoke
     * @param method  method of the specified service to invoke
     */
    public Request(String service, String method) {
        //create a new builder
        DocumentBuilderFactory builderFactory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder = null;
        try {
            builder = builderFactory.newDocumentBuilder();
        } catch (ParserConfigurationException e) {
            e.printStackTrace();
        }
        //create the document
        if (builder != null) {
            xml = builder.newDocument();
            //create the root element
            root = xml.createElement("methodCall");
            xml.appendChild(root);
            //create the params element
            params = xml.createElement("params");
            //add the service and method attributes to the root element
            setMethod(method);
            setService(service);
        }

    }

    private void setService(String service) {
        root.setAttribute("service", service);
    }

    private void setMethod(String method) {
        root.setAttribute("method", method);
    }

    /**
     * Create a simple parameter. When converted to a string it looks like
     * <pre >{@code <param>Value</param>}</pre>
     *
     * @param param The name of the param element
     * @param value The value the parameter should hold
     */
    public void setParam(String param, String value) {
        //check if params some params have already been set
        if (!usesParams) {
            root.appendChild(params);
            usesParams = true;
        }
        Element parameter = xml.createElement(param);
        parameter.setTextContent(value);
        params.appendChild(parameter);
    }

    /**
     * Create an empty parameter
     *
     * @param param The name of the param element
     * @see Request#setParam(String, String)
     */
    public void setParam(String param) {
        if (!usesParams) {
            root.appendChild(params);
            usesParams = true;
        }
        Element parameter = xml.createElement(param);
        params.appendChild(parameter);
    }

    public void setParam(SwType param) {
        addComplexParam(param.buildXml());
    }

    /**
     * Create complex params. When converted to a string they take the format
     * <pre >{@code <param>
     * <something>
     * Value
     * </something>
     * </param>}</pre>
     *
     * @param param Name of the parent xml element for the parameter
     * @return new {@link ComplexParam}
     */
    public ComplexParam prepComplexParam(String param) {
        if (!usesParams) {
            root.appendChild(params);
            usesParams = true;
        }
        return new ComplexParam(param, xml);
    }

    private void addComplexParam(ComplexParam param) {
        boolean paramIsParams = param.getRoot().getNodeName().equals("params");
        if (paramIsParams) {
            params = (Element) xml.importNode(param.getRootNode(), true);
        } else {
            Node parameter = xml.importNode(param.getRootNode(), true);
            params.appendChild(parameter);
        }
        if (!usesParams) {
            root.appendChild(params);
            usesParams = true;
        }


    }

    /**
     * Return a string representation of the xml
     *
     * @return Xml String of the format
     * <pre >{@code <methodCall service="service" method="method">
     * <params>
     * <paramName>
     * Value
     * </paramName>
     * </params>
     * </methodCall>}</pre>
     */
    public String toString() {
        return Helpers.getXmlString(xml);
    }

    /**
     * Return the actual xml Document object
     *
     * @return Document object of the request. If you want the xml string, use the {@link Request#toString()} method
     */
    public Document getXmlDocument() {
        //return the xml document
        return xml;
    }
}
