import java.io.IOException;

/**
 * The Wrapper for the helpdesk service of the xmlmc API. Extends Service
 * Access it from the XmlMethodCall class.
 *
 * @author Rich Gordon
 */

public class Helpdesk extends Service {
    /**
     *
     * @param connection An instance of the connection class. This is passed automatically when accessed via the
     *                   {@link XmlMethodCall} class
     */
    protected Helpdesk(Connection connection) {
        super(connection,"helpdesk");
    }

    /**
     * Accept one or more calls
     *
     * @see Helpdesk#acceptCalls(boolean, String...)
     */
    public Response acceptCalls(String... callrefs) throws IOException {
        Request request = generateRequest("acceptCalls");
        for (String callref :
                callrefs) {
            request.setParam("callref", callref);
        }
        return invoke(request);
    }

    /**
     * Accept one or more calls
     *
     * {@code acceptCalls(true, "7568","1247","4448")}
     *
     * @param markAsSLAResponse Mark the acceptance as an SLA response
     * @param callrefs one or more unformatted callrefs to accept.
     * @return returns instance of response object {@link Response}
     */
    public Response acceptCalls(boolean markAsSLAResponse, String... callrefs) throws IOException {
        Request request = generateRequest("acceptCalls");
        for (String callref :
                callrefs) {
            request.setParam("callref", callref);
        }
        request.setParam("markAsSLAResponse", markAsSLAResponse ? "true" : "false");
        return invoke(request);
    }

    /**
     * Assign one or more calls to a group
     *
     * {@code assignCallsToGroup("Helpdesk", "1234", "5866")}
     *
     * also
     *
     * {@code assignCallsToGroup("Helpdesk", ["1234","5866"])}
     *
     * @param groupId Id of group to assign the calls to
     * @param callrefs One or more unformatted callrefs or an array of unformatted callrefs
     * @return returns {@link Response}
     *@throws IOException
     */
    public Response assignCallsToGroup(String groupId, String... callrefs) throws IOException {
        Request request = generateRequest("assignCalls");
        for (String callref :
                callrefs) {
            request.setParam("callref", callref);
        }
        request.setParam("groupId", groupId);
        return invoke(request);
    }

    public Response assignCallsToAnalyst(String groupId, String analystId, String... callrefs) throws IOException {
        Request request = generateRequest("assignCalls");
        for (String callref :
                callrefs) {
            request.setParam("callref", callref);
        }
        request.setParam("groupId", groupId);
        request.setParam("analystId", analystId);
        return invoke(request);
    }

//    public Response callKeywordQuery() {
//        Request request = generateRequest("callKeywordQuery");
//
//        return invoke(request);
//    }

//    public Response callNaturalQuery() {
//        Request request = generateRequest("callNaturalQuery");
//
//        return invoke(request);
//    }

    public Response cancelCalls(String description, boolean publicUpdate, String ...callrefs) throws IOException {
        Request request = generateRequest("cancelCalls");
        for (String callref :
                callrefs) {
            request.setParam("callref", callref);
        }
        request.setParam("description",description);
        request.setParam("publicUpdate",publicUpdate?"true":"false");
        return invoke(request);
    }

    public Response changeCallClass(String callclass, String ...callrefs) throws IOException {
        Request request = generateRequest("changeCallClass");
        for (String callref :
                callrefs) {
            request.setParam("callref",callref);
        }
        request.setParam("class",callclass);
        return invoke(request);
    }

    public Response changeCallCondition(int condition, String ...callrefs) throws IOException {
        Request request = generateRequest("changeCallCondition");
        for (String callref :
                callrefs) {
            request.setParam("callref", callref);
        }
        request.setParam("condition", Integer.toString(condition));
        return invoke(request);
    }

    public Response closeCalls(String description, int timeSpent, String ...callrefs) throws IOException {
        Request request = generateRequest("closeCalls");
        for (String callref :
                callrefs) {
            request.setParam("callref", callref);
        }
        request.setParam("timeSpent", Integer.toString(timeSpent));
        request.setParam("description", description);
        return invoke(request);
    }
    
    public Response deleteAttachmentFromCall(String callref, String attachmentId) throws IOException {
        Request request = generateRequest("deleteAttachmentFromCall");
        request.setParam("callref", callref);
        request.setParam("attachId", attachmentId);
        return invoke(request);
    }




}
