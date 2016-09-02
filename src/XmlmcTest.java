import xmlmc.Response;
import xmlmc.XmlMethodCall;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerException;
import java.io.IOException;

/*****************************************
 * Author : rich
 * Date : 3/16/16
 * Assignment: XmlmcTest
 ******************************************/
public class XmlmcTest {
    public static void main(String[] args) throws ParserConfigurationException, TransformerException, IOException {
        XmlMethodCall xmlmc = new XmlMethodCall("192.168.1.u");
        Response logon = xmlmc.session().analystLogon("admin", "");
        if (logon.isSuccessful()) {
            System.out.println(logon);
            System.out.println(logon.getParameters());
            Response logoff = xmlmc.session().analystLogoff();
            System.out.println(logoff);
        }

    }
}


