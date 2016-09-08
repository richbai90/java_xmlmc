package xmlmc;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;


import java.net.URL;

import static org.hamcrest.CoreMatchers.*;
import static org.junit.Assert.*;

/*****************************************
 * Author : rich
 * Date : 9/8/16
 * Assignment: ConnectionTest
 ******************************************/
public class ConnectionTest {
    Connection connection;

    @Before
    public void setUp() throws Exception {
        connection = new Connection("192.168.1.102");
    }

    @After
    public void tearDown() throws Exception {
        Request request = new Request("session", "analystLogoff");
        connection.sendRequest(request);
    }

    @Test
    public void getEndpoint() throws Exception {
        assertThat(connection.getEndpoint(), is(instanceOf(URL.class)));
    }

    @Test
    public void getSessionCookie() throws Exception {
        // TODO: 9/8/16 Come up with a better test.
        Request request = new Request("session", "analystLogon");
        request.setParam("userId", "admin");
        request.setParam("password","");
        connection.sendRequest(request);
        assertFalse(connection.getSessionCookie().isEmpty());
    }

    @Test
    public void getSessionId() throws Exception {
        // TODO: 9/8/16 Implement getSessionId and test
    }

    @Test
    public void sendRequest() throws Exception {
        Request getSessionInfo = new Request("session", "getSessionInfo");
        assertThat(connection.sendRequest(getSessionInfo), is(instanceOf(Response.class)));
    }

    @Test
    public void getLastError() throws Exception {
        Request badRequest = new Request("session", "unknowMethod");
        connection.sendRequest(badRequest);
        assertThat(connection.getLastError(),is(not("")));
    }

    @Test
    public void testToString() throws Exception {
        assertThat(connection.toString(), is("http://192.168.1.102:5015"));
    }

}