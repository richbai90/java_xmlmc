package com.bittercreektech.xmlmc;

import com.bittercreektech.xmlmc.api.Data;
import com.bittercreektech.xmlmc.api.Helpdesk;
import com.bittercreektech.xmlmc.api.Session;

import java.net.MalformedURLException;


/**
 * XmlMethodCall.java
 * Purpose: Provide an interface to all the Xmlmc APIs.
 *
 * The XmlMethodCall class provides easy access to all the Xmlmc API services and their respective methods.
 * It handles opening and closing the connection to the server automatically and parses the xml responses from the server
 * into easy to navigate ArrayLists or Hashes. It also manages the session cookie, both sending and receiving, as well
 * as updating as necessary, automatically. Interfacing with the API is best done through this class since it handles all the details automatically.
 *
 *<div><p>
 * Usage: <pre>{@code
 * xmlmc = new XmlMethodCall("localhost");
 * xmlmc.session().analystLogon("admin", "password");
 * xmlmc.helpdesk().acceptCalls("1557","1998");
 * xmlmc.analystLogoff();
 * }</pre>
 *</div>
 * @author Rich Gordon
 * @version 1.1.0 08/29/2016
 */

public class XmlMethodCall {
    private Connection connection;

    /**
     * Creates a connection to the server that can be used to send requests over the wire
     * @param server The hostname or ip address of the server you wish to send requests to
     * @param debug Whether to print the request xml and response xml to the console
     * @throws MalformedURLException If the Server is not a valid hostname or ip address
     */
    public XmlMethodCall(String server, boolean debug) throws MalformedURLException {
        connection = new Connection(server, debug);
    }

    /**
     * Creates a connection to the server that can be used to send requests over the wire. Sets debug to false.
     * @param server The hostname or ip address of the server you wish to send requests to
     * @throws MalformedURLException If the Server Is not a valid hostname or ip address.
     */
    public XmlMethodCall(String server) throws MalformedURLException {
        this(server, false);
    }

    /**
     * Provide access to the session methods from the XmlMethodCall object
     * @return an instance of the api.Session object.
     * @see Session
     */
    public Session session() {
        return new Session(connection);
    }

    /**
     * Provide access to the helpdesk methods from the XmlMethodCall object
     * @return an instance of the api.Helpdesk object
     * @see Helpdesk
     */
    public Helpdesk helpdesk() {
        return new Helpdesk(connection);
    }

    /**
     * Provide access to the data methods from the XmlMethodCall object
     * @return an instance of the api.Data object
     * @see Data
     */
    public Data data() {
        return new Data(connection);
    }

    public String getSessionId() {
        return connection.getSessionId();
    }

}
