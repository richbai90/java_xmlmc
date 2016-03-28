import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;
import java.io.StringReader;

/*****************************************
 * Author : rich
 * Date : 3/28/16
 * Assignment: ResponseHandler
 ******************************************/
public class ResponseHandler {
    Document response;
    public void parse(String response) {
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        try {
            DocumentBuilder builder = factory.newDocumentBuilder();
            this.response = builder.parse(new InputSource(new StringReader(response)));
        } catch (ParserConfigurationException | SAXException | IOException e) {
            e.printStackTrace();
        }
    }

    public String status() {
        return response.getDocumentElement().getAttribute("status");
    }

    public String get(String parameter) {
        Node param =  response.getElementsByTagName(parameter).item(0);
        return param.getTextContent();
    }
}
