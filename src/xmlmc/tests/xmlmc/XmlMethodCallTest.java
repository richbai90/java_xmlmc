package xmlmc;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import xmlmc.api.Data;
import xmlmc.api.Helpdesk;
import xmlmc.api.Session;

import static org.hamcrest.CoreMatchers.instanceOf;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

/*****************************************
 * Author : rich
 * Date : 9/8/16
 * Assignment: XmlMethodCallTest
 ******************************************/
public class XmlMethodCallTest {
    XmlMethodCall xmlmc;

    @Before
    public void setUp() throws Exception {
        this.xmlmc = new XmlMethodCall("192.168.1.102");
    }

    @After
    public void tearDown() throws Exception {

    }

    @Test
    public void session() throws Exception {
        assertThat(xmlmc.session(), is(instanceOf(Session.class)));
    }

    @Test
    public void helpdesk() throws Exception {
        assertThat(xmlmc.helpdesk(), is(instanceOf(Helpdesk.class)));
    }

    @Test
    public void data() throws Exception {
        assertThat(xmlmc.data(), is(instanceOf(Data.class)));
    }

}