import org.w3c.dom.Document;
import org.w3c.dom.Element;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.*;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.StringWriter;

/*****************************************
 * Author : rich
 * Date : 3/16/16
 * Assignment: Request
 ******************************************/
public class Request {
    private Document xml;
    private Element root;
    private Element params;
    private boolean usesParams = false;

    Request(String service, String method) throws ParserConfigurationException {
        //create a new builder
        DocumentBuilderFactory builderFactory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder = builderFactory.newDocumentBuilder();
        //create the document
        xml = builder.newDocument();
        //create the root element
        root = xml.createElement("methodCall");
        xml.appendChild(root);
        //create the params element
        params = xml.createElement("params");
        //add the service and method attributes to the root element
        setService(service);
        setMethod(method);
    }

    public void setService(String service) {
        root.setAttribute("service", service);
    }

    public void setMethod(String method) {
        root.setAttribute("method", method);
    }

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

    public void setParam(String param) {
        if (!usesParams) {
            root.appendChild(params);
            usesParams = true;
        }
        Element parameter = xml.createElement(param);
        params.appendChild(parameter);
    }

    public ComplexParam prepComplexParam(String param) throws ParserConfigurationException {
        if (!usesParams) {
            root.appendChild(params);
            usesParams = true;
        }
        return new ComplexParam(param, xml);
    }

    public String getXmlString() throws TransformerException {
        //parse the document to a string and return the string
        DOMSource domSource = new DOMSource(xml);
        StringWriter writer = new StringWriter();
        StreamResult result = new StreamResult(writer);
        TransformerFactory tf = TransformerFactory.newInstance();
        Transformer transformer = tf.newTransformer();
        transformer.transform(domSource, result);
        return writer.toString();
    }

    public Document getXml() {
        //return the xml document
        return xml;
    }
}
