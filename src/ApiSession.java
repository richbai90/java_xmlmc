import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerException;
import java.io.*;
import java.net.*;

/*****************************************
 * Author : rich
 * Date : 3/25/16
 * Assignment: ApiSession
 ******************************************/
public class ApiSession {
    public enum SessionType {ANALYST, SELFSERVICE, ANALYSTTRUST}

    private URL endpoint;
    private HttpURLConnection connection;
    private String session = "";
    private SessionType sessionType;
    private String lastError;
    private String sessionId;

    public ApiSession(String server, String port) throws IOException, ParserConfigurationException {
        endpoint = new URL(String.format("http://%s:%s", server, port));

    }

    public ApiSession() throws IOException, ParserConfigurationException {
        this("localhost", "5015");
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

    public String getSessionId() {
        return sessionId;
    }

    public ResponseHandler sendRequest(Request request) throws TransformerException, IOException {
        return sendRequest(request.getXmlString());
    }

    public ResponseHandler sendRequest(String xmlRequest) throws IOException {
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

        ResponseHandler response = handleResponse();
        lastError = (response.getStatus().equalsIgnoreCase("ok")) ? "" : response.getLastError();
        return response;

    }

    private ResponseHandler handleResponse() {
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

        ResponseHandler response = new ResponseHandler();
        response.parse(xmlResponse);
        return response;
    }

    public String getLastError() {
        return lastError;
    }

    public boolean analystLogon(String username, String password) throws ParserConfigurationException, TransformerException, IOException {
        //Set the sessionType
        sessionType = SessionType.ANALYST;
        //encode the password
        password = Helpers.base64Encode(password);
        //Create a new XML request
        Request request = new Request("session", "analystLogon");
        //Add xml children for the parameters
        request.setParam("userId", username);
        request.setParam("password", password);
        //Send the request
        ResponseHandler response = sendRequest(request);
        //Check that the status returns ok
        return response.getStatus().equalsIgnoreCase("ok");
    }

    public ResponseHandler selfServiceLogon(String username, String password, String instance) throws ParserConfigurationException, TransformerException, IOException {
        sessionType = SessionType.SELFSERVICE;
        password = Helpers.base64Encode(password);
        Request request = new Request("session", "selfServiceLogon");
        request.setParam("selfServiceInstance", instance);
        request.setParam("customerId", username);
        request.setParam("password", password);
        return sendRequest(request);

    }

    public ResponseHandler selfServiceLogon(String username, String password) throws IOException, TransformerException, ParserConfigurationException {
        return selfServiceLogon(username, password, "ITSM");
    }

    public boolean analystLogonTrust(String username, String secret) throws TransformerException, IOException, ParserConfigurationException {
        sessionType = SessionType.ANALYSTTRUST;
        secret = Helpers.base64Encode(secret);
        Request request = new Request("session", "analystLogonTrust");
        request.setParam("userId", username);
        request.setParam("secretKey", secret);
        ResponseHandler response = sendRequest(request);
        return response.getStatus().equalsIgnoreCase("ok");
    }


    public boolean logoff() throws ParserConfigurationException, TransformerException, IOException {
        switch (sessionType) {
            case SELFSERVICE:
                return selfServiceLogoff();
            case ANALYST:
            default:
                return analystLogoff();
        }
    }

    private boolean selfServiceLogoff() throws ParserConfigurationException, TransformerException, IOException {
        Request request = new Request("session", "selfServiceLogoff");
        ResponseHandler response = sendRequest(request);
        return response.getStatus().equalsIgnoreCase("ok");
    }

    private boolean analystLogoff() throws ParserConfigurationException, TransformerException, IOException {
        Request request = new Request("session", "analystLogoff");
        ResponseHandler response = sendRequest(request);
        return response.getStatus().equalsIgnoreCase("ok");
    }

    public boolean bindSession(String session) throws ParserConfigurationException, TransformerException, IOException {
        Request request = new Request("session", "bindSession");
        request.setParam("sessionId", session);
        return sendRequest(request).getStatus().equalsIgnoreCase("ok");
    }

    public boolean changePassword(String oldPassword, String newPassword) throws TransformerException, IOException, ParserConfigurationException {
        Request request = new Request("session", "changePassword");
        request.setParam("oldPassword", Helpers.base64Encode(oldPassword));
        request.setParam("newPassword", Helpers.base64Encode(newPassword));
        return sendRequest(request).getStatus().equalsIgnoreCase("ok");
    }

    public ResponseHandler convertDateTimeInText(String time) throws TransformerException, IOException, ParserConfigurationException {
        Request request = new Request("session", "convertDateTimeInText");
        request.setParam("inputText", time);
        return sendRequest(request);
    }

    public ResponseHandler getSessionInfo() throws ParserConfigurationException, TransformerException, IOException {
        Request request = new Request("Session", "getSessionInfo2");
//        request.setParam("sessionId");
        return sendRequest(request);
    }


}
