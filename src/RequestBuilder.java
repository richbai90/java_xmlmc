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
 * Assignment: RequestBuilder
 ******************************************/
public class RequestBuilder {
    private Document xml;
    private Element root;
    private Element params;
    private boolean usesParams = false;

    RequestBuilder(String service, String method) throws ParserConfigurationException {
        DocumentBuilderFactory builderFactory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder = builderFactory.newDocumentBuilder();
        xml = builder.newDocument();
        root = xml.createElement("methodCall");
        xml.appendChild(root);
        params = xml.createElement("params");
        setService(service);
        setMethod(method);
    }

    public void setService(String service) {
        root.setAttribute("service", service);
    }

    public void setMethod(String method) {
        root.setAttribute("method", method);
    }

    public void setParam(String attribute, String value) {
        if (!usesParams) {
            root.appendChild(params);
            usesParams = true;
        }
        Element param = xml.createElement(attribute);
        param.setTextContent(value);
        params.appendChild(param);
    }

    public void setParam(String attribute) {
        if (!usesParams) {
            root.appendChild(params);
            usesParams = true;
        }
        Element param = xml.createElement(attribute);
        params.appendChild(param);
    }

    public ComplexParam prepComplexParam(String attribute) throws ParserConfigurationException {
        if (!usesParams) {
            root.appendChild(params);
            usesParams = true;
        }
        return new ComplexParam(attribute, xml);
    }

    public String getXmlString() throws TransformerException {
        DOMSource domSource = new DOMSource(xml);
        StringWriter writer = new StringWriter();
        StreamResult result = new StreamResult(writer);
        TransformerFactory tf = TransformerFactory.newInstance();
        Transformer transformer = tf.newTransformer();
        transformer.transform(domSource, result);
        return writer.toString();
    }

    public Document getXml() {
        return xml;
    }
}
