package com.bittercreektech.xmlmc;

import java.net.MalformedURLException;

/*****************************************
 * Author : Rich Baird
 * Date : 11/8/17
 * Assignment: XmlMethodCallOverNat
 * Class xmlmc
 ******************************************/
public class XmlMethodCallOverNat extends XmlMethodCall {
    /**
     * Creates a connection to the server that can be used to send requests over the wire
     *
     * @param server The hostname or ip address of the server you wish to send requests to
     * @param port
     * @param debug  Whether to print the request xml and response xml to the console  @throws MalformedURLException If the Server is not a valid hostname or ip address
     */
    private XmlMethodCallOverNat(String server, int port, boolean debug, int forward, boolean secure) throws MalformedURLException {
        super(server, port, debug, forward, secure);
    }

    public XmlMethodCallOverNat(String server, int port, int forward) throws MalformedURLException {
        this(server, port, true, forward, false);
    }
}
