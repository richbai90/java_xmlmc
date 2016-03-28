import org.w3c.dom.Document;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.IOException;
import java.io.StringWriter;

/*****************************************
 * Author : rich
 * Date : 3/16/16
 * Assignment: XmlmcTest
 ******************************************/
public class XmlmcTest {
    public static void main(String[] args) throws ParserConfigurationException, TransformerException, IOException {
        ApiSession session = Xmlmc.newSession("10.3.200.46","5015");
        session.analystLogon("admin","");
        session.logoff();
    }
}
