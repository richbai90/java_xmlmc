package xmlmc;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * xmlmc.Connection.java
 * <p>
 * Handles the maintanance of a network connection to the server. This includes sending requests, parsing responses, updating the session cookie
 * and closing the connection. This class is package local and is not accessible outside of the xmlmc package. To take advantage of
 * the xmlmc.Connection class, use the {@link XmlMethodCall} class instead.
 */
public class Connection {
    private URL endpoint;
    private HttpURLConnection connection;
    private String sessionCookie = "";
    private String lastError;
    private String sessionId;
    private String server;
    private String session = "";

    private Connection(String server, String port) throws MalformedURLException {
        this.server = server;
        connect(server, port);
    }

    /**
     * Create a new connection to the server
     *
     * @param server hostname or ip address of the server
     */
    public Connection(String server) throws MalformedURLException {
        this.server = server;
        connect();
    }

    private void connect(String server, String port) throws MalformedURLException {
        try {
            endpoint = new URL(String.format("http://%s:%s", server, port));
        } catch (MalformedURLException e) {
            throw new MalformedURLException(String.format("Unable to make a connection to the server. The url %s appears to be malformed", endpoint));
        }
    }

    private void connect() throws MalformedURLException {
        connect(server, "5015");
    }

    private void setSession(String session) {
        this.session = session;
    }

    URL getEndpoint() {
        return endpoint;
    }

    String getSessionCookie() {
        return session;
    }

    String getSessionId() {
        return sessionId;
    }

    public Response sendRequest(Request request) throws IOException {
        return sendRequest(request.toString());
    }

    private Response sendRequest(String xmlRequest) throws IOException {
        connection = (HttpURLConnection) endpoint.openConnection();
        connection.setDoOutput(true); // Triggers POST.
        connection.setFixedLengthStreamingMode(xmlRequest.length()); //avoid chunking data
        connection.setRequestProperty("Accept-Charset", "UTF-8");
        connection.setRequestProperty("Content-Type", "text/xmlmc");

        //Set the cookie for the request if we are not connecting for the first time.

        if (!session.isEmpty()) {
            connection.setRequestProperty("Cookie", session);
        }

        //Send the xml request

        try (OutputStream output = connection.getOutputStream()) {
            output.write(xmlRequest.getBytes("UTF-8"));
            output.flush();
            output.close();
            connection.connect();
        } catch (IOException e) {
            e.printStackTrace();
        }

        Response response = handleResponse();
        lastError = (response.getStatus().equalsIgnoreCase("ok")) ? "" : response.getLastError();
        return response;

    }

    private Response handleResponse() {
        String xmlResponse = "";
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream(), "UTF-8"))) {
            for (String line; (line = reader.readLine()) != null; ) {
                xmlResponse += line;
            }

            String cookie = connection.getHeaderField("Set-Cookie");

            if (cookie != null) {
                session = cookie.split(";", 2)[0];
            }

            connection.getInputStream().close();

        } catch (IOException e) {
            e.printStackTrace();
        }

        return new Response(xmlResponse);
    }

    public String getLastError() {
        return lastError;
    }

    public String toString() {
        return this.getEndpoint().toString();
    }

}
