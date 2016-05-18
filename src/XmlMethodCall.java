import org.jetbrains.annotations.Contract;

import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;
import java.net.URL;
import java.net.URLConnection;

/**
 * Xmlmc.java
 * Purpose: Provide an interface to all the Xmlmc APIs
 *
 * @author Rich Gordon
 * @version 1.0.0 03/28/2016
 */
public class XmlMethodCall {

    protected XmlMethodCall() {}


//    public static Request newRequest(String service, String method) throws ParserConfigurationException {
//        return new Request(service, method);
//    }


    public static ApiSession newSession(String server, String port) throws IOException, ParserConfigurationException {
        return new ApiSession(server, port);
    }


    public static ApiSession newSession() throws IOException, ParserConfigurationException {
        return new ApiSession();
    }

    public static HelpDesk helpDesk(ApiSession session) {
        return new HelpDesk(session);
    }
}
