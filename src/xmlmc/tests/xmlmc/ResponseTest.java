package xmlmc;

import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.HashMap;

import static org.junit.Assert.assertThat;
import static org.hamcrest.CoreMatchers.*;

/*****************************************
 * Author : rich
 * Date : 9/9/16
 * Assignment: ResponseTest
 ******************************************/
public class ResponseTest {
    Response response;

    @Before
    public void setup() {
        String response = "<?xml version='1.0' encoding='UTF-8'?>\n" +
                "<methodCallResult status=\"ok\">\n" +
                "    <params>\n" +
                "        <SessionID>57d2d993-38e9aad6-0eb0</SessionID>\n" +
                "    </params>\n" +
                "</methodCallResult>";

        this.response = new Response(response);
    }

    @Test
    public void getParameter() throws Exception {
        assertThat(response.getParameter("SessionID"), is("57d2d993-38e9aad6-0eb0"));
        assertThat(response.getParameter("unknown"), is(""));
    }

    @Test
    public void getParameters() throws Exception {
        assertThat(response.getParameters(),is(instanceOf(HashMap.class)));
        assertThat(response.getParameters().get("SessionID"), is("57d2d993-38e9aad6-0eb0"));
    }

    @Test
    public void getLastError() throws Exception {
        Response failure = failedResponse();
        assertThat(failure.getLastError(),is("Invalid session. Please establish a session first"));
        assertThat(response.getLastError(),is(""));
    }

    @Test
    public void getStatus() throws Exception {
        assertThat(response.getStatus(),is("ok"));
        assertThat(failedResponse().getStatus(),is("fail"));
    }

    @Test
    public void getRows() throws Exception {
        assertThat(response.getRows().size(),is(equalTo(0)));
        assertThat(rowsResponse().getRows(),is(instanceOf(ArrayList.class)));
        assertThat(rowsResponse().getRows().size(),is(equalTo(3)));
        assertThat(rowsResponse().getRows().get(0).get("callref"),is("12345"));

    }

    @Test
    public void getRow() throws Exception {
        assertThat(rowsResponse().getRow(0),is(instanceOf(HashMap.class)));
        assertThat(rowsResponse().getRow(1).get("status"),is("16"));
    }

    @Test
    public void getRecord() throws Exception {

    }

    @Test
    public void testToString() throws Exception {

    }

    @Test
    public void isSuccessful() throws Exception {

    }

    private Response failedResponse() {
        String response = "<?xml version='1.0' encoding='UTF-8'?>\n" +
                "<methodCallResult status=\"fail\">\n" +
                "    <state>\n" +
                "        <code>0005</code>\n" +
                "        <error>Invalid session. Please establish a session first</error>\n" +
                "    </state>\n" +
                "</methodCallResult>";
        return new Response(response);
    }

    private Response rowsResponse() {
        String response = "<?xml version='1.0' encoding='UTF-8'?>\n" +
                "<methodCallResult status=\"ok\">\n" +
                "    <params>\n" +
                "        <rowsEffected>3</rowsEffected>\n" +
                "    </params>\n" +
                "    <rowData>\n" +
                "        <row>\n" +
                "            <callref>12345</callref>\n" +
                "            <status>2</status>\n" +
                "        </row>" +
                "        <row>\n" +
                "            <callref>50897</callref>\n" +
                "            <status>16</status>\n" +
                "        </row>\n" +
                "        <row>\n" +
                "            <callref>8675309</callref>\n" +
                "            <status>9</status>\n" +
                "        </row>\n" +
                "    </rowData>\n" +
                "</methodCallResult>";
        return new Response(response);
    }

}