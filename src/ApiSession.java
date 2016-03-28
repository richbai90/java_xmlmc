import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerException;
import java.io.*;
import java.net.*;
import java.util.Base64;

/*****************************************
 * Author : rich
 * Date : 3/25/16
 * Assignment: ApiSession
 ******************************************/
public class ApiSession {
    private static final int ANALYST = 373;
    private static final int SELFSERVICE = 397;
    private static final int ANALYSTTRUST = 899;
    private URL endpoint;
    private HttpURLConnection connection;
    private String session = "";
    private String xmlResponse;
    private int sessionType;

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

    public String getSession() {
        return session;
    }

    public ResponseHandler sendRequest(RequestBuilder request) throws TransformerException, IOException {
        return sendRequest(request.getXmlString());
    }

    public ResponseHandler sendRequest(String xmlRequest) throws IOException {
        connection = (HttpURLConnection) endpoint.openConnection();
        connection.setDoOutput(true); // Triggers POST.
        connection.setFixedLengthStreamingMode(xmlRequest.length()); //avoid chunking data
        connection.setRequestProperty("Accept-Charset", "UTF-8");
        connection.setRequestProperty("Content-Type", "text/xmlmc");

        if (!session.isEmpty()) {
            connection.setRequestProperty("Cookie", session);
        }

        try (OutputStream output = connection.getOutputStream()) {
            output.write(xmlRequest.getBytes("UTF-8"));
            output.flush();
            output.close();
            connection.connect();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return handleResponse();

    }

    private ResponseHandler handleResponse() {
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

    public boolean analystLogon(String username, String password) throws ParserConfigurationException, TransformerException, IOException {
        sessionType = ANALYST;
        password = Base64.getEncoder().encodeToString(password.getBytes());
        RequestBuilder request = new RequestBuilder("session", "analystLogon");
        request.setParam("userId", username);
        request.setParam("password", password);
        ResponseHandler response = sendRequest(request);
        return response.status().equalsIgnoreCase("ok");
    }

    public boolean selfServiceLogon(String username, String password) {
        return false;
    }

    ;

    public boolean analystLogonTrust(String username, String secret) {
        return false;
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
        RequestBuilder request = new RequestBuilder("session", "selfServiceLogoff");
        ResponseHandler response = sendRequest(request);
        return response.status().equalsIgnoreCase("ok");
    }

    public boolean analystLogoff() throws ParserConfigurationException, TransformerException, IOException {
        RequestBuilder request = new RequestBuilder("session", "analystLogoff");
        ResponseHandler response = sendRequest(request);
        return response.status().equalsIgnoreCase("ok");
    }


    public String getXmlResponse() {
        return xmlResponse;
    }

}
