import java.io.IOException;

/*****************************************
 * Author : rich
 * Date : 8/29/16
 * Assignment: Session
 ******************************************/
public class Session extends Service {

    Session(Connection connection) {
        super(connection, "session");
    }

    Response analystLogon(String userId, String password) throws IOException {
        password = Helpers.base64Encode(password);
        Request request = generateRequest("analystLogon");
        request.setParam("userId", userId);
        request.setParam("password", password);
        return invoke(request);
    }

    Response analystLogoff() throws IOException {
        Request request = generateRequest("analystLogoff");
        return invoke(request);
    }

    Response analystLogonTrust(String userId, String secretKey) throws IOException {
        secretKey = Helpers.base64Encode(secretKey);
        Request request = generateRequest("analystLogonTrust");
        request.setParam("userId", userId);
        request.setParam("secretKey", secretKey);
        return invoke(request);
    }

    Response bindSession(String sessionId) throws IOException {
        Request request = generateRequest("bindSession");
        request.setParam("sessionId", sessionId);
        return invoke(request);
    }

    Response changePassword(String oldPassword, String newPassword) throws IOException {
        Request request = generateRequest("changePassword");
        oldPassword = Helpers.base64Encode(oldPassword);
        newPassword = Helpers.base64Encode(newPassword);
        request.setParam(oldPassword);
        request.setParam(newPassword);
        return invoke(request);
    }

    Response closeLocalSession() throws IOException {
        Request request = generateRequest("closeLocalSession");
        return invoke(request);
    }

    Response convertDateTimeInText(String inputText) throws IOException {
        Request request = generateRequest("convertDateTimeInText");
        request.setParam(inputText);
        return invoke(request);
    }

    Response createLocalSession() throws IOException {
        Request request = generateRequest("createLocalSession");
        return invoke(request);
    }

    Response getSessionInfo() throws IOException {
        Request request = generateRequest("getSessionInfo");
        return invoke(request);
    }

    Response getSessionInfo2() throws IOException {
        Request request = generateRequest("getSessionInfo2");
        return invoke(request);
    }

    Response hasRight(String userRight) throws IOException {
        Request request = generateRequest("hasRight");
        request.setParam("userRight");
        return invoke(request);
    }

    Response isSessionValid() throws IOException {
        Request request = generateRequest("isSessionValid");
        return invoke(request);
    }

    Response selfServiceLogon(String selfServiceInstance, String customerId, String password) throws IOException {
        password = Helpers.base64Encode(password);
        Request request = generateRequest("selfServiceLogon");
        request.setParam("selfServiceInstance", selfServiceInstance);
        request.setParam("customerId", customerId);
        request.setParam("password", password);
        return invoke(request);
    }

    Response selfServiceLogon(String customerId, String password) throws IOException {
        Request request = generateRequest("selfServiceLogon");
        return selfServiceLogon("itsm", customerId, password);
    }

    Response selfServiceLogoff() throws IOException {
        Request request = generateRequest("selfServiceLogoff");
        return invoke(request);
    }

    Response setDatabaseRight(String tableName, int rightFlag, boolean rightAllowed) throws IOException {
        Request request = generateRequest("setDatabaseRight");
        String newRightFlag = Integer.toString(rightFlag);
        request.setParam("tableName", tableName);
        request.setParam("rightFlag", newRightFlag);
        request.setParam("rightAllowed", rightAllowed ? "true" : "false");
        return invoke(request);
    }

    Response setOutputValidation(boolean validateResultMesage) throws IOException {
        Request request = generateRequest("setOutputValidation");
        request.setParam("validateResultMessage", validateResultMesage ? "true" : "false");
        return invoke(request);
    }

    Response setUserRight(String rightClass, int rightFlag, boolean rightAllowed) throws IOException {
        Request request = generateRequest("setUserRight");
        request.setParam("rightClass", rightClass);
        request.setParam("rightFlag", Integer.toString(rightFlag));
        request.setParam("rightAllowed", rightAllowed ? "true" : "false");
        return invoke(request);
    }

//    Response setVariable(String sessionVariable) {
//        Request request = generateRequest("setVariable");
//
//        return invoke(request);
//    }

    Response switchAnalystContext(String groupId) throws IOException {
        Request request = generateRequest("switchAnalystContext");
        request.setParam("groupId", groupId);
        return invoke(request);
    }

    Response switchAnalystContext(String groupId, String analystId) throws IOException {
        Request request = generateRequest("switchAnalystContext");
        request.setParam("groupId", groupId);
        request.setParam("analystid", analystId);
        return invoke(request);
    }


}
