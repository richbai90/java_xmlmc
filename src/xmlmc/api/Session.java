package xmlmc.api;

import xmlmc.Connection;
import xmlmc.Helpers;
import xmlmc.Request;
import xmlmc.Response;

import java.io.IOException;

/**
 * api.Service.java
 * <p>
 * Interface to all the xmlmc session methods. Before anything else can be done a call to one of the logon methods must
 * be made. Each method returns an instance of the {@link Response object}
 */
public class Session extends Service {

    /**
     * Setup the session API by calling the super constructor defined in {@link Service#Service(Connection, String)}
     *
     * @param connection An instance of the {@link Connection class}
     */
    public Session(Connection connection) {
        super(connection, "session");
    }

    /**
     * Create a supportworks session as the specified analyst
     *
     * @param userId Analyst ID
     * @param password Analyst Password
     * @return {@link Request}
     * @throws IOException
     */
    public Response analystLogon(String userId, String password) throws IOException {
        password = Helpers.base64Encode(password);
        Request request = generateRequest("analystLogon");
        request.setParam("userId", userId);
        request.setParam("password", password);
        return invoke(request);
    }

    /**
     * Log off the current session of the session was created using {@link Session#analystLogon(String, String)}
     *
     * @return {@link Request}
     * @throws IOException
     */
    public Response analystLogoff() throws IOException {
        Request request = generateRequest("analystLogoff");
        return invoke(request);
    }

    /**
     * Use this method to authenticate using some other trusted identification method.
     *
     * @param userId    analyst ID
     * @param secretKey Secret key specified in your sw configuration
     * @return {@link Request}
     * @throws IOException
     */
    public Response analystLogonTrust(String userId, String secretKey) throws IOException {
        secretKey = Helpers.base64Encode(secretKey);
        Request request = generateRequest("analystLogonTrust");
        request.setParam("userId", userId);
        request.setParam("secretKey", secretKey);
        return invoke(request);
    }

    /**
     * Bind a new session to an existing session. Useful to not consume a new license.
     *
     * @param sessionId api.Session ID to bind to
     * @return {@link Request}
     * @throws IOException
     */
    public Response bindSession(String sessionId) throws IOException {
        Request request = generateRequest("bindSession");
        request.setParam("sessionId", sessionId);
        return invoke(request);
    }

    /**
     * Change the user's password. Works the same for Customer passwords as well as Analyst Passwords
     *
     * @param oldPassword Original Password
     * @param newPassword New Password
     * @return {@link Request}
     * @throws IOException
     */
    public Response changePassword(String oldPassword, String newPassword) throws IOException {
        Request request = generateRequest("changePassword");
        oldPassword = Helpers.base64Encode(oldPassword);
        newPassword = Helpers.base64Encode(newPassword);
        request.setParam(oldPassword);
        request.setParam(newPassword);
        return invoke(request);
    }

    /**
     * Close a local System api.Session
     *
     * @return {@link Request}
     * @throws IOException
     */
    public Response closeLocalSession() throws IOException {
        Request request = generateRequest("closeLocalSession");
        return invoke(request);
    }

    /**
     * Given a well formatted date-time string, converts the string to the analyst's timezone
     *
     * @param inputText date time string in the format month/day/year/hh:mm:ss
     * @return {@link Request}
     * @throws IOException
     */
    public Response convertDateTimeInText(String inputText) throws IOException {
        Request request = generateRequest("convertDateTimeInText");
        request.setParam(inputText);
        return invoke(request);
    }

    /**
     * Create a local system session
     *
     * @return {@link Request}
     * @throws IOException
     */
    public Response createLocalSession() throws IOException {
        Request request = generateRequest("createLocalSession");
        return invoke(request);
    }

    /**
     * Gets the information about the session. Technically this method has been deprecated, however, it still functions
     * and some information exists only from this method.
     *
     * @return {@link Request}
     * @throws IOException
     * @see Session#getSessionInfo2()
     * @deprecated
     */
    public Response getSessionInfo() throws IOException {
        Request request = generateRequest("getSessionInfo");
        return invoke(request);
    }

    /**
     * Gets the information about the session.
     *
     * @return {@link Request}
     * @throws IOException
     */
    public Response getSessionInfo2() throws IOException {
        Request request = generateRequest("getSessionInfo2");
        return invoke(request);
    }

    /**
     * Check if the user has a specific right
     *
     * @param userRight The right you wish to check for
     * @return {@link Request}
     * @throws IOException
     */
    public Response hasRight(String userRight) throws IOException {
        Request request = generateRequest("hasRight");
        request.setParam("userRight");
        return invoke(request);
    }

    /**
     * Check if the session is still valid. Useful check before attempting to bind a session.
     *
     * @param sessionId sessionId to check against
     * @return {@link Request}
     * @throws IOException
     */
    public Response isSessionValid(String sessionId) throws IOException {
        Request request = generateRequest("isSessionValid");
        request.setParam("sessionId", sessionId);
        return invoke(request);
    }

    /**
     * Create a customer session.
     *
     * @param selfServiceInstance Name of the selfservice instance you wish to connect to
     * @param customerId          Customer ID you wish to connect to
     * @param password            password for the customer
     * @return {@link Request}
     * @throws IOException
     */
    public Response selfServiceLogon(String selfServiceInstance, String customerId, String password) throws IOException {
        password = Helpers.base64Encode(password);
        Request request = generateRequest("selfServiceLogon");
        request.setParam("selfServiceInstance", selfServiceInstance);
        request.setParam("customerId", customerId);
        request.setParam("password", password);
        return invoke(request);
    }

    /**
     * Create a customer session in the ITSM instance
     *
     * @see Session#selfServiceLogon(String, String, String)
     */
    public Response selfServiceLogon(String customerId, String password) throws IOException {
        Request request = generateRequest("selfServiceLogon");
        return selfServiceLogon("itsm", customerId, password);
    }

    /**
     * End the current customer session
     *
     * @return {@link Request}
     * @throws IOException
     */
    public Response selfServiceLogoff() throws IOException {
        Request request = generateRequest("selfServiceLogoff");
        return invoke(request);
    }

    /**
     * Set a database right for an analyst
     *
     * @param tableName    name of the table
     * @param rightFlag    integer flag of which right to set
     * @param rightAllowed right allowed or disallowed
     * @return {@link Request}
     * @throws IOException
     */
    public Response setDatabaseRight(String tableName, int rightFlag, boolean rightAllowed) throws IOException {
        Request request = generateRequest("setDatabaseRight");
        String newRightFlag = Integer.toString(rightFlag);
        request.setParam("tableName", tableName);
        request.setParam("rightFlag", newRightFlag);
        request.setParam("rightAllowed", rightAllowed ? "true" : "false");
        return invoke(request);
    }

    /**
     * Enable or disable output validation messages.
     *
     * @param validateResultMesage enable (true) or disable (false)
     * @return {@link Request}
     * @throws IOException
     */
    public Response setOutputValidation(boolean validateResultMesage) throws IOException {
        Request request = generateRequest("setOutputValidation");
        request.setParam("validateResultMessage", validateResultMesage ? "true" : "false");
        return invoke(request);
    }

    /**
     * Set a right for the current user
     *
     * @param rightClass   Category of right to set
     * @param rightFlag    Int flag which right in the category to set
     * @param rightAllowed allowed or disallowed
     * @return {@link Request}
     * @throws IOException
     */
    public Response setUserRight(String rightClass, int rightFlag, boolean rightAllowed) throws IOException {
        Request request = generateRequest("setUserRight");
        request.setParam("rightClass", rightClass);
        request.setParam("rightFlag", Integer.toString(rightFlag));
        request.setParam("rightAllowed", rightAllowed ? "true" : "false");
        return invoke(request);
    }

//    public xmlmc.Response setVariable(String sessionVariable) {
//        xmlmc.Request request = generateRequest("setVariable");
//
//        return invoke(request);
//    }

    /**
     * Switch the analyst context to a group
     *
     * @param groupId group ID to switch to
     * @return {@link Request}
     * @throws IOException
     */
    public Response switchAnalystContext(String groupId) throws IOException {
        Request request = generateRequest("switchAnalystContext");
        request.setParam("groupId", groupId);
        return invoke(request);
    }

    /**
     * Switch analyst context to that of another analyst
     *
     * @param groupId   group ID of the analyst
     * @param analystId analyst ID to switch to
     * @return {@link Request}
     * @throws IOException
     */
    public Response switchAnalystContext(String groupId, String analystId) throws IOException {
        Request request = generateRequest("switchAnalystContext");
        request.setParam("groupId", groupId);
        request.setParam("analystid", analystId);
        return invoke(request);
    }


}
