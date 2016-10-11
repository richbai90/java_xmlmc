package xmlmc;

import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.Base64;
import java.util.HashMap;

/**
 * xmlmc.Helpers.java
 * <p>
 * A static class of helper functions.
 */
public class Helpers {
    /**
     * Base64 encode a string. Required for passwords.
     *
     * @param string String to encode
     * @return base64 encoded string
     */
    public static String base64Encode(String string) {
        return Base64.getEncoder().encodeToString(string.getBytes());
    }


    /**
     * Convert a nodelist into an ArrayList
     *
     * @param nodes nodelist to convert
     * @return ArrayList
     */
    public static ArrayList<Node> nodesAsList(NodeList nodes) {
        ArrayList<Node> nodesList = new ArrayList<>();
        int nodeIndex = 0;
        while (nodeIndex < nodes.getLength()) {
            nodesList.add(nodes.item(nodeIndex));
            nodeIndex++;
        }

        return nodesList;
    }

    /**
     * Convert a nodelist into a hashmap of key-value pairs
     *
     * @param nodes Nodelist to convert
     * @return HashMap of {@code nodeName => nodeValue}
     */
    public static HashMap<String, String> nodesAsMap(NodeList nodes) {
        HashMap<String, String> nodeMap = new HashMap<String, String>();
        int nodeIndex = 0;
        while (nodeIndex < nodes.getLength()) {
            Node node = nodes.item(nodeIndex);
            if (!node.getNodeName().equals("#text")) {
                nodeMap.put(node.getNodeName(), node.getTextContent());
            }
            nodeIndex++;
        }

        return nodeMap;
    }

    public static HashMap<String, String> nodesAsMap(Node node) {
        NodeList children = node.getChildNodes();
        return nodesAsMap(children);
    }

    public static String getXmlString(Document xml) {
        //parse the document to a string and return the string
        DOMSource domSource = new DOMSource(xml);
        StringWriter writer = new StringWriter();
        StreamResult result = new StreamResult(writer);
        TransformerFactory tf = TransformerFactory.newInstance();
        Transformer transformer = null;
        try {
            transformer = tf.newTransformer();
        } catch (TransformerConfigurationException e) {
            e.printStackTrace();
        }
        try {
            if (transformer != null) {
                transformer.transform(domSource, result);
            }
        } catch (TransformerException e) {
            e.printStackTrace();
        }
        return writer.toString();

    }
}
