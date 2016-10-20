package com.bittercreektech.xmlmc.api;

import com.bittercreektech.xmlmc.Connection;
import com.bittercreektech.xmlmc.Request;
import com.bittercreektech.xmlmc.Response;

import java.io.IOException;

/**
 * api.Service class
 *
 * An abstract class which all the other api.Service API wrappers extend. If you are going to create a new API wrapper
 * extend the api.Service class.
 *
 * @author rich
 */
abstract public class Service {

    private final Connection connection;
    private final String service;

    /**
     * Populate the required connection and service parameters
     *
     * @param connection represents a connection to the server. {@link Connection}
     * @param service the name of the service that this API wraps, for example "session"
     */
    protected Service(Connection connection, String service) {
        this.connection = connection;
        this.service = service;
    }

    /**
     * Generates a new Request with the session specified in the constructor and a method provided when called.
     *
     * @param method the method of the service this API wraps see xmlmc documentation for details
     * @return {@code new Request}
     *
     * @see Request
     */
    public Request generateRequest(String method) {
        return new Request(service,method);
    }

    /**
     * Send the request to the server via the connection provided in the constructor.
     *
     * @param request A request to be sent
     * @return {@code new Response}
     *
     * @see Service#generateRequest(String)
     * @see Response
     */
    public Response invoke(Request request) throws IOException {
        return connection.sendRequest(request);
    }
}
