package xmlmc;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import xmlmc.types.EmbeddedFileAttachment;

import static org.hamcrest.CoreMatchers.*;
import static org.junit.Assert.assertThat;

/*****************************************
 * Author : rich
 * Date : 9/9/16
 * Assignment: RequestTest
 ******************************************/
public class RequestTest {
    Request request;

    @Before
    public void setUp() throws Exception {
        request = new Request("session", "analystLogon");
    }

    @After
    public void tearDown() throws Exception {

    }

    /**
     * Place this test at the top as it is needed for other tests to succeed
     *
     * @throws Exception
     */
    @Test
    public void getXmlDocument() throws Exception {
        assertThat(request.getXmlDocument(), is(instanceOf(Document.class)));
    }

    @Test
    public void xmlStructure() {
        Node root = request.getXmlDocument().getDocumentElement();
        assertThat(root.getNodeName(), is("methodCall"));
        assertThat(root.getAttributes().getLength(), is(equalTo(2)));
        assertThat(root.getAttributes().item(0).getNodeName(), is("method"));
        assertThat(root.getAttributes().item(0).getTextContent(), is("analystLogon"));
        assertThat(root.getAttributes().item(1).getNodeName(), is("service"));
        assertThat(root.getAttributes().item(1).getTextContent(), is("session"));
    }

    @Test
    public void setNormalParam() throws Exception {
        request.setParam("analystId", "admin");
        assertThat(request.getXmlDocument().getElementsByTagName("params").getLength(), is(equalTo(1)));
        Node params = request.getXmlDocument().getElementsByTagName("params").item(0);
        assertThat(params.getChildNodes().getLength(), is(equalTo(1)));
        assertThat(params.getLastChild().getNodeName(), is("analystId"));
        assertThat(params.getLastChild().getTextContent(), is("admin"));
    }

    @Test
    public void setEmptyParam() throws Exception {
        request.setParam("analystId", "admin");
        request.setParam("password");
        Node params = request.getXmlDocument().getElementsByTagName("params").item(0);
        assertThat(params.getChildNodes().getLength(), is(equalTo(2)));
        assertThat(params.getLastChild().getNodeName(), is("password"));
        assertThat(params.getLastChild().getTextContent(), is(""));
    }

    @Test
    public void setSwTypeParam() throws Exception {
        EmbeddedFileAttachment fileAttachment = new EmbeddedFileAttachment("/Users/Rich/Documents/test.m");
        request.setParam(fileAttachment);
        Node params = request.getXmlDocument().getElementsByTagName("params").item(0);
        assertThat(params.getLastChild().getNodeName(), is("fileAttachment"));
        assertThat(params.getLastChild().getChildNodes().getLength() > 0, is(true));
    }

    @Test
    public void prepComplexParam() throws Exception {
        request.prepComplexParam("complex").createChild("subchild").update();
        assertThat(request.getXmlDocument().getElementsByTagName("params").item(0).getLastChild().getNodeName(), is("complex"));
        assertThat(request.getXmlDocument().getElementsByTagName("params").item(0).getLastChild().getLastChild().getNodeName(), is("subchild"));
    }

    @Test
    public void testToString() throws Exception {
        request.setParam("analystId", "admin");
        request.setParam("password");
        String xmlString = "<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"no\"?><methodCall method=\"analystLogon\" service=\"session\"><params><analystId>admin</analystId><password/></params></methodCall>";
        assertThat(request.toString(), is(xmlString));
    }

}