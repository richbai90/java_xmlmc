import org.jetbrains.annotations.Contract;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;
import java.io.StreamCorruptedException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;

/**
 * Xmlmc.java
 * Purpose: Provide an interface to all the Xmlmc APIs
 *
 * @author Rich Gordon
 * @version 1.0.0 03/28/2016
 */
public abstract class Xmlmc {
    private URL endpoint;
    private URLConnection connection;
    private String session;



    protected Xmlmc() {}

    @Contract("_, _ -> !null")
    public static RequestBuilder newRequest(String service, String method) throws ParserConfigurationException {
        return new RequestBuilder(service, method);
    }

    @Contract("_, _ -> !null")
    public static ApiSession newSession(String server, String port) throws IOException, ParserConfigurationException {
        return new ApiSession(server, port);
    }

    @Contract(" -> !null")
    public static ApiSession newSession() throws IOException, ParserConfigurationException {
        return new ApiSession();
    }
}
