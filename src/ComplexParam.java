import org.w3c.dom.*;

import javax.xml.parsers.ParserConfigurationException;

/*****************************************
 * Author : rich
 * Date : 3/15/16
 * Assignment: ComplexParam
 ******************************************/
public class ComplexParam {
    private Element root;
    private Element attr;
    private Document xml;
    private Element params;

    public void update() {
        params.appendChild(attr);
    }

    public Element getAttr() {
        return attr;
    }

    public Element getRoot() {
        return root;
    }

    public ComplexParam(String attr, Document xml) throws ParserConfigurationException {
        this.attr = xml.createElement(attr);
        this.xml = xml;
        root = xml.getDocumentElement();
        params = (Element) root.getFirstChild();
    }

    public ComplexParam(Element attr, Document xml) throws ParserConfigurationException {
        this.attr = attr;
        this.xml = xml;
        root = xml.getDocumentElement();
        params = (Element) root.getFirstChild();
        //xml.appendChild(this.attr);
    }

    public ComplexParam createChild(String nodeName) throws ParserConfigurationException {
        Element child = xml.createElement(nodeName);
        if (attr.getLastChild() != null) {
            attr.getLastChild().appendChild(child);
        } else {
            attr.appendChild(child);
        }

        return new ComplexParam(attr, xml);
    }


    public ComplexParam setNodeAttribute(String name, String value) throws DOMException, ParserConfigurationException {
        attr.setAttribute(name, value);
        return new ComplexParam(attr, xml);
    }


    public ComplexParam removeNodeAttribute(String name) throws DOMException, ParserConfigurationException {
        attr.removeAttribute(name);
        return new ComplexParam(attr, xml);
    }
}