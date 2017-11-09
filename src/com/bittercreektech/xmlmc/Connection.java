package com.bittercreektech.xmlmc;

import javax.net.ssl.*;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.X509Certificate;

/**
 * Connection.java
 * <p>
 * Handles the maintanance of a network connection to the server. This includes sending requests, parsing responses, updating the session cookie
 * and closing the connection. This class is package local and is not accessible outside of the xmlmc package. To take advantage of
 * the Connection class, use the {@link XmlMethodCall} class instead.
 */
public class Connection {
    private URL endpoint;
    private HttpURLConnection connection;
    private String sessionCookie = "";
    private String lastError;
    private String sessionId;
    private String server;
    private String session = "";
    private boolean debug = false;
    private boolean trustOnlySecureCerts = true;
    private boolean https = false;

    public Connection(String server, int port) throws MalformedURLException {
        this.server = server;
        connect(server, port, false);
    }

    public Connection(String server, int port, boolean debug) throws MalformedURLException {
        this.server = server;
        connect(server, port, debug);
    }

    protected Connection(String server, int port, boolean debug, int forward, boolean secure) throws MalformedURLException, NoSuchAlgorithmException {
        this.server = server;
        this.trustOnlySecureCerts = secure;

        if(!trustOnlySecureCerts) {
            // Create a trust manager that does not validate certificate chains
            TrustManager[] trustAllCerts = new TrustManager[] {new X509TrustManager() {
                public java.security.cert.X509Certificate[] getAcceptedIssuers() {
                    return null;
                }
                public void checkClientTrusted(X509Certificate[] certs, String authType) {
                }
                public void checkServerTrusted(X509Certificate[] certs, String authType) {
                }
            }
            };

            // Install the all-trusting trust manager
            SSLContext sc = SSLContext.getInstance("SSL");
            try {
                sc.init(null, trustAllCerts, new java.security.SecureRandom());
            } catch (KeyManagementException e) {
                e.printStackTrace();
            }
            HttpsURLConnection.setDefaultSSLSocketFactory(sc.getSocketFactory());

            // Create all-trusting host name verifier
            HostnameVerifier allHostsValid = new HostnameVerifier() {
                public boolean verify(String hostname, SSLSession session) {
                    return true;
                }
            };

            // Install the all-trusting host verifier
            HttpsURLConnection.setDefaultHostnameVerifier(allHostsValid);
        }

        connect(server, port, debug, forward);
    }

    /**
     * Create a new connection to the server
     *
     * @param server hostname or ip address of the server
     */
    public Connection(String server, boolean debug) throws MalformedURLException {
        this.server = server;
        connect(debug);
    }

    private void connect(String server, int port, boolean debug, int forward) throws MalformedURLException {
        this.debug = debug;
        try {
            String url;
            switch (port) {
                case 443:
                    this.https = true;
                    url = String.format("https://%s/sw/xmlmc/", server);
                    break;
                case 80:
                    url = String.format("http://%s/sw/xmlmc/", server);
                    break;
                default:
                    this.https = forward == 443;
                    url = this.https ? String.format("https://%s:%d/sw/xmlmc/", server, port) : String.format("http://%s:%d", server, port);
            }
            endpoint = new URL(url);
        } catch (MalformedURLException e) {
            throw new MalformedURLException(String.format("Unable to make a connection to the server. The url %s appears to be malformed", endpoint));
        }
    }

    private void connect(String server, int port, boolean debug) throws MalformedURLException {
        connect(server, port, debug, 0);
    }

    private void connect(boolean debug) throws MalformedURLException {
        connect(server, 5015, debug);
    }

    private void setSession(String session) {
        this.session = session;
    }

    public URL getEndpoint() {
        return endpoint;
    }

    public String getSessionCookie() {
        return session;
    }

    String getSessionId() {
        return sessionId;
    }

    public Response sendRequest(Request request) throws IOException {
        return sendRequest(request.toString());
    }

    private Response sendRequest(String xmlRequest) throws IOException {
        if (debug) {
            System.out.println(xmlRequest);
        }

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
        if (debug) {
            System.out.println(response);
        }
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

    public boolean isHttps() {
        return https;
    }

    public String toString() {
        return this.getEndpoint().toString();
    }

    public boolean isTrustOnlySecureCerts() {
        return trustOnlySecureCerts;
    }
}
