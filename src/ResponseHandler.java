import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.List;

/*****************************************
 * Author : rich
 * Date : 3/28/16
 * Assignment: ResponseHandler
 ******************************************/
public class ResponseHandler {
    private Document response;
    private String xmlResponse;
    private String lastError;
    private String status;
    private ArrayList<Node> rows;

    public void parse(String response) {
        xmlResponse = response;
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        try {
            DocumentBuilder builder = factory.newDocumentBuilder();
            this.response = builder.parse(new InputSource(new StringReader(response)));
            this.status = parseStatus();
            lastError = (status.equalsIgnoreCase("ok")) ? "" : this.response.getElementsByTagName("error").item(0).getTextContent();
            parseRowData();
        } catch (ParserConfigurationException | SAXException | IOException e) {
            e.printStackTrace();
        }
    }

    private String parseStatus() {
        return response.getDocumentElement().getAttribute("status");
    }

    private void parseRowData() {
        NodeList rowNodes = response.getElementsByTagName("row");
        rows = Helpers.nodesAsList(rowNodes);
    }

    public String getParameter(String parameter) {
        Node param = response.getElementsByTagName(parameter).item(0);
        if (param == null) {
            return "No parameter by that name found. Try getParameters to see all parameters. Please note parameters are case sensitive";
        }
        return param.getTextContent();
    }

    public ArrayList<Node> getParameters() {
        Node parameters = response.getElementsByTagName("params").item(0);
        NodeList allParams = parameters.getChildNodes();
        return Helpers.nodesAsList(allParams);
    }

    public String getLastError() {
        return lastError;
    }

    public String getStatus() {
        return status;
    }

    public String getXmlResponse() {
        return xmlResponse;
    }

    public ArrayList<Node> getRows() {
        return rows;
    }

    public Node getRow(int index) {
        return rows.get(index);
    }
}
