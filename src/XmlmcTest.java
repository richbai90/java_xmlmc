import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerException;
import java.io.IOException;
import java.util.ArrayList;

/*****************************************
 * Author : rich
 * Date : 3/16/16
 * Assignment: XmlmcTest
 ******************************************/
public class XmlmcTest {
    public static void main(String[] args) throws ParserConfigurationException, TransformerException, IOException {
        ApiSession session = XmlMethodCall.newSession("192.168.1.30","5015");
        if(session.analystLogon("admin","")) {
            ResponseHandler sessionInfo = session.getSessionInfo();
            System.out.println(sessionInfo.getParameter("analystName"));
            System.out.println(session.getSessionId());
            session.logoff();
        } else {
            System.out.println(session.getLastError());
        }

    }
}


