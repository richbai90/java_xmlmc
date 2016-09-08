package xmlmc;

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
import java.util.HashMap;

/**
 * xmlmc.Response.java
 *
 * Allows to easily parse relevant information from an xml response from the server.
 * Automatically invoked from the xmlmc.Connection class
 * @see Connection#sendRequest(Request)
 */
public class Response {
    private Document response;
    private String xmlResponse;
    private String lastError;
    private String status;
    private ArrayList<HashMap<String, String>> rows;
    private boolean successful;
    private HashMap<String, String> record;

    /**
     * Will attempt to parse an xml response. If the response is not valid xml no error is thrown but the successful flag
     * is set to false and the status is set to network failure, since the server will only ever return valid xml, even in
     * the case of a malformed request.
     * @param response The xml response to parse
     */
    Response(String response) {
        response = response.replaceAll(">\\s+", ">");
        xmlResponse = response;
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        try {
            DocumentBuilder builder = factory.newDocumentBuilder();
            this.response = builder.parse(new InputSource(new StringReader(response)));
            this.status = parseStatus();
            this.successful = status.equals("ok");
            lastError = (status.equalsIgnoreCase("ok")) ? "" : this.response.getElementsByTagName("error").item(0).getTextContent();
            parseRowData();
            parseRecordData();
        } catch (ParserConfigurationException | IOException e) {
            e.printStackTrace();
        } catch (SAXException e) {
            this.successful = false;
            this.lastError = response;
            this.status = "Network Failure";
        }
    }

    private void parseRecordData() {
        Node record = response.getElementsByTagName("record").item(0);
        this.record = Helpers.nodesAsMap(record);
    }

    private String parseStatus() {
        return response.getDocumentElement().getAttribute("status");
    }

    private void parseRowData() {
        NodeList rowNodes = response.getElementsByTagName("row");
        if(rowNodes != null) {
            ArrayList<Node> rowList = Helpers.nodesAsList(rowNodes);
            for (Node row :
                    rowList) {
                rows.add(Helpers.nodesAsMap(row));
            }
        }
    }

    /**
     * Get the value of returned parameter by name
     * @param parameter name of the response param to get
     * @return value of the parameter as a String
     * @throws NullPointerException if the parameter by that name was not found.
     */
    public String getParameter(String parameter) throws NullPointerException {
        Node param = response.getElementsByTagName(parameter).item(0);
        if (param == null) {
//            return "No parameter by that name found. Try getParameters to see all parameters. Please note parameters are case sensitive";
            throw new NullPointerException(
                    String.format("No parameter by the name %s found. " +
                                    "Try using getParameters to see all parameters. Parameters are case sensitive.",
                            parameter));

        }
        return param.getTextContent();
    }

    /**
     * Get a list of all available parameters as a hashmap in the format {@code paramName => paramValue}
     * @return HashMap {@code {paramName => paramValue} }
     */
    public HashMap<String, String> getParameters() {
        Node parameters = response.getElementsByTagName("params").item(0);
        NodeList allParams = parameters.getChildNodes();
        return Helpers.nodesAsMap(allParams);
    }

    /**
     * If the server returned an error get the error string. In the case of malformed xml return the rawResponse
     * @return error message
     */
    public String getLastError() {
        return lastError;
    }

    /**
     * Return the status code for the resonse ok | fail | network failure
     * @return response status
     */
    public String getStatus() {
        return status;
    }

//    public String getXmlResponse() {
//        return xmlResponse;
//    }

    /**
     * If we are expecting rows from this request return them in an ArrayList
     * <strong>This is not fully implemented</strong>
     * @return ArrayList of XmlNodes
     */
    public ArrayList<HashMap<String, String>> getRows() {
        return rows;
    }

    /**
     * Return a specific row
     * <strong>This is not fully implemented</strong>
     * @param index row to get starting at 0 = row 1
     * @return Xml Node
     */
    public HashMap<String, String> getRow(int index) {
        return rows.get(index);
    }

    public HashMap<String, String> getRecord() {
        return record;
    }

    /**
     * Return the raw response of the request as a string
     * @return response as a string
     */
    public String toString() {
        return xmlResponse;
    }

    /**
     * Method to check that the request was successful
     * @return true || false if the method was successful
     */
    public boolean isSuccessful() {
        return successful;
    }
}
