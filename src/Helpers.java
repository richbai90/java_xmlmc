import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import java.util.ArrayList;
import java.util.Base64;

/*****************************************
 * Author : rich
 * Date : 4/1/16
 * Assignment: Helpers
 ******************************************/
public class Helpers {
    public static String base64Encode(String string) {
        return Base64.getEncoder().encodeToString(string.getBytes());
    }


    public static ArrayList<Node> nodesAsList(NodeList nodes) {
        ArrayList<Node> nodesList = new ArrayList<>();
        int nodeIndex = 0;
        while (nodeIndex < nodes.getLength()) {
            nodesList.add(nodes.item(nodeIndex));
            nodeIndex++;
        }

        return nodesList;
    }
}
