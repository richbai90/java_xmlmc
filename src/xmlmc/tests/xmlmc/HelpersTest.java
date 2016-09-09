package xmlmc;

import org.junit.Before;
import org.junit.Test;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import java.util.ArrayList;
import java.util.HashMap;

import static org.hamcrest.CoreMatchers.instanceOf;
import static org.hamcrest.CoreMatchers.*;
import static org.hamcrest.core.IsNot.not;
import static org.junit.Assert.assertThat;

/*****************************************
 * Author : rich
 * Date : 9/8/16
 * Assignment: HelpersTest
 ******************************************/
public class HelpersTest {
    Document xml;
    Element root;

    @Before
    public void setUp() {
        DocumentBuilderFactory builderFactory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder = null;
        try {
            builder = builderFactory.newDocumentBuilder();
        } catch (ParserConfigurationException e) {
            e.printStackTrace();
        }

        if (builder != null) {
            xml = builder.newDocument();

            //create the root element
            root = xml.createElement("params");
            xml.appendChild(root);
            Element results = xml.createElement("results");
            int[] indices = {1, 2, 3, 4, 5, 6};
            for (int index :
                    indices) {
                Element child = xml.createElement("child" + Integer.toString(index));
                results.appendChild(child);
            }

            root.appendChild(results);
        }
    }

    @Test
    public void base64Encode() throws Exception {
        assertThat(Helpers.base64Encode("password"), is("cGFzc3dvcmQ="));
    }

    @Test
    public void nodesAsList() throws Exception {
        NodeList children = root.getElementsByTagName("results").item(0).getChildNodes();
        assertThat(Helpers.nodesAsList(children),is(instanceOf(ArrayList.class)));
        assertThat(Helpers.nodesAsList(children).size(),is(6));
        for (Node child :
                Helpers.nodesAsList(children)) {
            assertThat(child.getNodeName(), is(not("#text")));
        }
    }

    @Test
    public void nodesAsMap() throws Exception {
        NodeList children = root.getElementsByTagName("results").item(0).getChildNodes();
        assertThat(Helpers.nodesAsMap(children).size(),is(6));
        assertThat(Helpers.nodesAsMap(children).containsKey("child1"),is(true));
    }

    @Test
    public void nodesAsMap1() throws Exception {
        Node chilren = root.getElementsByTagName("results").item(0);
        assertThat(Helpers.nodesAsMap(chilren),is(instanceOf(HashMap.class)));
        assertThat(Helpers.nodesAsMap(chilren).size(),is(6));
        assertThat(Helpers.nodesAsMap(chilren).containsKey("child1"),is(true));
    }

}