import org.w3c.dom.*;

import javax.xml.parsers.ParserConfigurationException;

/*****************************************
 * Author : rich
 * Date : 3/15/16
 * Assignment: ComplexParam
 ******************************************/
public class ComplexParam {
    private Element root;
    private Element param;
    private Document xml;
    private Element params;

    public void update() {
        params.appendChild(param);
    }

    public Element getAttr() {
        return param;
    }

    public Element getRoot() {
        return root;
    }

    public ComplexParam(String param, Document xml) throws ParserConfigurationException {
        this.param = xml.createElement(param);
        this.xml = xml;
        root = xml.getDocumentElement();
        params = (Element) root.getFirstChild();
    }

    public ComplexParam(Element param, Document xml) throws ParserConfigurationException {
        this.param = param;
        this.xml = xml;
        root = xml.getDocumentElement();
        params = (Element) root.getFirstChild();
        //xml.appendChild(this.attr);
    }

    public ComplexParam createChild(String nodeName) throws ParserConfigurationException {
        Element child = xml.createElement(nodeName);
        if (param.getLastChild() != null) {
            param.getLastChild().appendChild(child);
        } else {
            param.appendChild(child);
        }

        return new ComplexParam(param, xml);
    }


    public ComplexParam setNodeAttribute(String name, String value) throws DOMException, ParserConfigurationException {
        param.setAttribute(name, value);
        return new ComplexParam(param, xml);
    }


    public ComplexParam removeNodeAttribute(String name) throws DOMException, ParserConfigurationException {
        param.removeAttribute(name);
        return new ComplexParam(param, xml);
    }
}