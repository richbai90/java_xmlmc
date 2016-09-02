package xmlmc.api;

import xmlmc.Connection;
import xmlmc.Request;
import xmlmc.Response;
import xmlmc.XmlMethodCall;
import xmlmc.types.Call;
import xmlmc.types.EmbeddedFileAttachment;

import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;

/**
 * The Wrapper for the helpdesk service of the xmlmc API. Extends api.Service
 * Access it from the XmlMethodCall class.
 *
 * @author Rich Gordon
 */

public class Helpdesk extends Service {

    /**
     * Status values for locking a call
     * ASSIGN, CLOSE, CANCEL, CHANGECLASS, HOLD, ISSUE, WORKFLOW
     */
    public enum LockReason {
        ASSIGN, CLOSE, CANCEL, CHANGECLASS, HOLD, ISSUE, WORKFLOW
    }

    /**
     * @param connection An instance of the connection class. This is passed automatically when accessed via the
     *                   {@link XmlMethodCall} class
     */
    public Helpdesk(Connection connection) {
        super(connection, "helpdesk");
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
     * <p>
     * {@code acceptCalls(true, "7568","1247","4448")}
     *
     * @param markAsSLAResponse Mark the acceptance as an SLA response
     * @param callrefs          one or more unformatted callrefs to accept.
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
     * Add a file attachment to an existing update entry. The file must be readable as this method will attempt to
     * base64 encode the file data.
     *
     * @param callref  Callref to add the attachment to
     * @param udid     Update Id to associate the attachment with
     * @param fileName Full path of the file to add. The file must be readable
     * @return {@link Request}
     * @throws ParserConfigurationException
     * @throws IOException
     */
    public Response addFilesToCallDiaryItem(String callref, String udid, String fileName) throws ParserConfigurationException, IOException {
        Request request = generateRequest("addFilesToCallDiaryItem");
        request.setParam("callRef", callref);
        request.setParam("diaryUpdateId", udid);
        EmbeddedFileAttachment fileAttachment = new EmbeddedFileAttachment(fileName);
        request.setParam(fileAttachment);
        return invoke(request);
    }

    /**
     * Assign one or more calls to a group
     * <p>
     * {@code assignCallsToGroup("api.Helpdesk", "1234", "5866")}
     * <p>
     * also
     * <p>
     * {@code assignCallsToGroup("api.Helpdesk", ["1234","5866"])}
     *
     * @param groupId  Id of group to assign the calls to
     * @param callrefs One or more unformatted callrefs or an array of unformatted callrefs
     * @return returns {@link Response}
     * @throws IOException
     */
    public Response assignCalls(String groupId, String... callrefs) throws IOException {
        Request request = generateRequest("assignCalls");
        for (String callref :
                callrefs) {
            request.setParam("callref", callref);
        }
        request.setParam("groupId", groupId);
        return invoke(request);
    }

    /**
     * Assign Call to a specific analyst
     *
     * @param groupId   Id of group to assign the calls to
     * @param analystId Analyst ID to assign the calls to
     * @param callrefs  One or more unformatted callrefs or an array of unformatted callrefs to be assigned
     * @return {@link Request}
     * @throws IOException
     * @see Helpdesk#assignCalls(String, String...)
     */
    public Response assignCalls(String groupId, String analystId, String... callrefs) throws IOException {
        Request request = generateRequest("assignCalls");
        for (String callref :
                callrefs) {
            request.setParam("callref", callref);
        }
        request.setParam("groupId", groupId);
        request.setParam("analystId", analystId);
        return invoke(request);
    }

    /**
     * Attach one or more files to an existing call, without being tied to a specific update.
     *
     * @param callRef   Call reference to attach the file to
     * @param fileNames one or more full path names the file(s) you wish to attach
     * @return {@link Request}
     * @throws ParserConfigurationException
     * @throws IOException
     */
    public Response attachFilesToCalls(String callRef, String... fileNames) throws ParserConfigurationException, IOException {
        Request request = generateRequest("attachFilesToCalls");
        request.setParam("callRef", callRef);
        for (String file :
                fileNames) {
            EmbeddedFileAttachment fileAttachment = new EmbeddedFileAttachment(file);
            request.setParam(fileAttachment);
        }
        return invoke(request);
    }

//    public xmlmc.Response callKeywordQuery() {
//        xmlmc.Request request = generateRequest("callKeywordQuery");
//
//        return invoke(request);
//    }

//    public xmlmc.Response callNaturalQuery() {
//        xmlmc.Request request = generateRequest("callNaturalQuery");
//
//        return invoke(request);
//    }

    /**
     * Cancel one or more calls with a description as to why
     *
     * @param description  description why
     * @param publicUpdate whether the change should be visible in selfService
     * @param callrefs     one or more unformatted callrefs or an array of unformatted callrefs
     * @return {@link Request}
     * @throws IOException
     */
    public Response cancelCalls(String description, boolean publicUpdate, String... callrefs) throws IOException {
        Request request = generateRequest("cancelCalls");
        for (String callref :
                callrefs) {
            request.setParam("callref", callref);
        }
        request.setParam("description", description);
        request.setParam("publicUpdate", publicUpdate ? "true" : "false");
        return invoke(request);
    }

    /**
     * change a call from one class to another
     *
     * @param callclass the class to change the call to
     * @param callrefs  one or more callrefs to change
     * @return {@link Request}
     * @throws IOException
     */
    public Response changeCallClass(String callclass, String... callrefs) throws IOException {
        Request request = generateRequest("changeCallClass");
        for (String callref :
                callrefs) {
            request.setParam("callref", callref);
        }
        request.setParam("class", callclass);
        return invoke(request);
    }

    /**
     * Change the visual condition flag on the call
     *
     * @param condition integer flag corresponding to the condition you wish to set
     * @param callrefs  one or more unformatted callrefs to change
     * @return {@link Request}
     * @throws IOException
     */
    public Response changeCallCondition(int condition, String... callrefs) throws IOException {
        Request request = generateRequest("changeCallCondition");
        for (String callref :
                callrefs) {
            request.setParam("callref", callref);
        }
        request.setParam("condition", Integer.toString(condition));
        return invoke(request);
    }

    /**
     * Close one or more calls with a description as to why
     *
     * @param description Reason for closing the call, typically the resolution text.
     * @param timeSpent   time spent working on the call
     * @param callrefs    one or more callrefs to close
     * @return {@link Request}
     * @throws IOException
     */
    public Response closeCalls(String description, int timeSpent, String... callrefs) throws IOException {
        Request request = generateRequest("closeCalls");
        for (String callref :
                callrefs) {
            request.setParam("callref", callref);
        }
        request.setParam("timeSpent", Integer.toString(timeSpent));
        request.setParam("description", description);
        return invoke(request);
    }

    /**
     * Delete an attachment from a call
     *
     * @param callref      callref the attachment is associated with
     * @param attachmentId attachment id as specified in the database
     * @return {@link Request}
     * @throws IOException
     */
    public Response deleteAttachmentFromCall(String callref, String attachmentId) throws IOException {
        Request request = generateRequest("deleteAttachmentFromCall");
        request.setParam("callref", callref);
        request.setParam("attachId", attachmentId);
        return invoke(request);
    }

    /**
     * get the number of calls assigned to an analyst
     *
     * @param analystId ID of the analyst to check
     * @param groupId   The group ID of the analyst to check. <strong>The values will differ from group
     *                  to group as this only checks the calls assigned to the analyst within that group context</strong>
     * @return
     * @throws IOException
     */
    public Response getAnalystAssignedCallCount(String analystId, String groupId) throws IOException {
        Request request = generateRequest("getAnalystAssignedCallCount");
        request.setParam("analystId", analystId);
        request.setParam("groupId", groupId);
        return invoke(request);
    }

    /**
     * given a group ID get the list of analysts available for assignment
     *
     * @param groupId   group ID to check against
     * @param recursive include subgroups ? true : false
     * @return {@link Request}
     * @throws IOException
     */
    public Response getAnalystAssignmentTree(String groupId, boolean recursive) throws IOException {
        Request request = generateRequest("getAnalystAssignmentTree");
        request.setParam("groupId", groupId);
        request.setParam("recursive", recursive ? "true" : "false");
        return invoke(request);
    }

    /**
     * given a group ID get the list of groups available for assignment
     *
     * @see Helpdesk#getAnalystAssignmentTree(String, boolean)
     */
    public Response getAnalystTeamTree(String groupId, boolean recursive) throws IOException {
        Request request = generateRequest("getAnalystTeamTree");
        request.setParam("groupId", groupId);
        request.setParam("recursive", recursive ? "true" : "false");
        return invoke(request);
    }

    /**
     * given a callref get the list of updates for the call
     *
     * @param callref call reference to query
     * @return {@link Request}
     * @throws IOException
     */
    public Response getCallDiaryItemList(String callref) throws IOException {
        Request request = generateRequest("getCallDiaryItemList");
        request.setParam("callref", callref);
        return invoke(request);
    }

    /**
     * Get a base64 encoded file given the callref and fileID
     *
     * @param callref call reference the file is associated with
     * @param fileId  the file ID as stored in the database
     * @return {@link Request}
     * @throws IOException
     */
    public Response getCallFileAttachment(String callref, String fileId) throws IOException {
        Request request = generateRequest("getCallFileAttachment");
        request.setParam("callRef", callref);
        request.setParam("fileId", fileId);
        return invoke(request);
    }

    /**
     * Get the list of file attachments for a particular call and/or update
     *
     * @param callRef reference of the call to check for attachments on
     * @param udid    the update id. To get all the attachments pass 0
     * @return {@link Request}
     * @throws IOException
     */
    public Response getCallFileAttachmentList(String callRef, int udid) throws IOException {
        Request request = generateRequest("getCallFileAttachmentList");
        request.setParam("callRef", callRef);
        request.setParam("updateId", Integer.toString(udid));
        return invoke(request);
    }

    /**
     * Get the sla details of a call
     *
     * @param callref call reference of the call to query against
     * @return {@link Request}
     * @throws IOException
     */
    public Response getCallSLAInfo(String callref) throws IOException {
        Request request = generateRequest("getCallSLAInfo");
        request.setParam("callref", callref);
        return invoke(request);
    }

    /**
     * get the status of a specified call
     *
     * @param callref callref to check the status of
     * @return {@link Request}
     * @throws IOException
     */
    public Response getCallStatusInfo(String callref) throws IOException {
        Request request = generateRequest("getCallStatusInfo");
        request.setParam("callref", callref);
        return invoke(request);
    }

    /**
     * get the summary of a call
     *
     * @param callref callref to get the summary from
     * @return {@link Request}
     * @throws IOException
     */
    public Response getCallSummaryInfo(String callref) throws IOException {
        Request request = generateRequest("getCallSummaryInfo");
        request.setParam("callref", callref);
        return invoke(request);
    }

    /**
     * This is a simple implementation of the place call on hold method. A more complex method can add file attachments
     * update sources and extra database values. This will be implemented soon.
     *
     * @param callref     call reference to place on hold
     * @param timeSpent   time spent on the call
     * @param description reason for placing on hold
     * @param holdUntil   date time string of the format dd/mm/yyyy hh:mm:ss
     * @return {@link Request}
     * @throws IOException
     */
    public Response holdCalls(String callref, int timeSpent, String description, String holdUntil) throws IOException {
        Request request = generateRequest("holdCalls");
        request.setParam("callref", callref);
        request.setParam("timeSpent", Integer.toString(timeSpent));
        request.setParam("description", description);
        request.setParam("holdUntil", holdUntil);
        return invoke(request);
    }

    /**
     * Invoke this method to manually lock a call. Calls are automatically locked when performing actions that require it.
     * note: in order to be able to perform any action on the call again, a manual unlock is also required.
     *
     * @param reason   {@link LockReason}
     * @param callrefs one or more callref strings to lock
     * @return {@link Request}
     * @throws IOException
     */
    public Response lockcalls(LockReason reason, String... callrefs) throws IOException {
        Request request = generateRequest("lockcalls");
        for (String callref :
                callrefs) {
            request.setParam("callref", callref);
        }
        request.setParam("reason", reason.toString());
        request.setParam("ignoreStatus", "true");
        return invoke(request);
    }

    /**
     * There is still a lot to do, this isn't ready yet.
     *
     * @param call
     * @return
     * @deprecated
     */
    public Response logAndAcceptNewCall(Call call) throws IOException {
        Request request = generateRequest("logAndAcceptNewCall");
        request.setParam(call);
        return invoke(request);
    }

    /**
     * @param call
     * @param groupId
     * @see Helpdesk#logAndAcceptNewCall(Call)
     * @deprecated
     */
    public Response logAndAssignNewCall(Call call, String groupId) throws IOException {
        Request request = generateRequest("logAndAssignNewCall");

        return invoke(request);
    }

    /**
     * @param call
     * @param groupId
     * @see Helpdesk#logAndAcceptNewCall(Call)
     * @deprecated
     */
    public Response logAndAssignNewCall(Call call, String groupId, String analystId) throws IOException {
        Request request = generateRequest("logAndAssignNewCall");

        return invoke(request);
    }

    /**
     * There is still a lot to do, this isn't ready yet.
     *
     * @param call
     * @return
     * @deprecated
     */
    public Response logAndTakeNewCall(Call call) throws IOException {
        Request request = generateRequest("logAndTakeNewCall");
        request.setParam(call);
        return invoke(request);
    }

    /**
     * There is still a lot to do, this isn't ready yet.
     *
     * @param call
     * @return
     * @deprecated
     */
    public Response logDeferredCall(Call call, String logDate, String groupId) throws IOException {
        Request request = generateRequest("logAndTakeNewCall");
        request.setParam(call);
        return invoke(request);
    }

    /**
     * Mark a call that has been viewed as unread
     *
     * @param callrefs callref(s) to mark as unread
     * @return {@link Request}
     * @throws IOException
     */
    public Response markWatchedCallsAsUnread(String... callrefs) throws IOException {
        Request request = generateRequest("markWatchedCallsAsUnread");
        for (String callref :
                callrefs) {
            request.setParam("callref", callref);
        }
        return invoke(request);
    }

    /**
     * Reactivate one or more calls
     *
     * @param callrefs one or more callrefs to reactivate
     * @return {@link Request}
     * @throws IOException
     */
    public Response reactivateCalls(String... callrefs) throws IOException {
        Request request = generateRequest("reactivateCalls");
        for (String callref : callrefs
                ) {
            request.setParam("callref", callref);
        }

        return invoke(request);
    }

    /**
     * resolve a call
     *
     * @param callref     Call reference to resolve
     * @param timeSpent   amount of time spent in minutes
     * @param description resolution text
     * @return {@link Response}
     * @throws IOException
     */
    public Response resolveCalls(String callref, int timeSpent, String description) throws IOException {
        Request request = generateRequest("resolveCalls");
        request.setParam("callref", callref);
        request.setParam("timeSpent", Integer.toString(timeSpent));
        request.setParam("description", description);
        return invoke(request);
    }

    /**
     * Take one or more calls off hold
     *
     * @param callrefs callref(s) to take off hold
     * @return {@link Request}
     * @throws IOException
     */
    public Response takeCallsOffHold(String... callrefs) throws IOException {
        Request request = generateRequest("takeCallsOffHold");
        for (String callref :
                callrefs) {
            request.setParam("callref", callref);
        }
        return invoke(request);
    }

    /**
     * unlock one or more calls
     *
     * @param callrefs callref(s) to unlock
     * @return {@link Request}
     * @throws IOException
     */
    public Response unlockCalls(String... callrefs) throws IOException {
        Request request = generateRequest("unlockCalls");
        for (String callref :
                callrefs) {
            request.setParam("callref", callref);

        }
        return invoke(request);
    }

    /**
     * Stop watching one or more calls
     *
     * @param callrefs callref(s) to stop watching
     * @return {@link Request}
     * @throws IOException
     */
    public Response unwatchCalls(String... callrefs) throws IOException {
        Request request = generateRequest("unwatchCalls");
        for (String callref :
                callrefs) {
            request.setParam("callref", callref);
        }
        return invoke(request);
    }

    /**
     * update and accept one or more calls
     *
     * @param updateText update text
     * @param timeSpent  amount of time spent on the call
     * @param callrefs   one or more call references to update
     * @return {@link Request}
     * @throws IOException
     */
    public Response updateAndAcceptCalls(String updateText, int timeSpent, String... callrefs) throws IOException {
        Request request = generateRequest("updateAndAcceptCalls");
        for (String callref :
                callrefs) {
            request.setParam("callref", callref);
        }
        request.setParam("timeSpent", Integer.toString(timeSpent));
        request.setParam("description", updateText);
        return invoke(request);
    }

    /**
     * update and assign calls to a group
     *
     * @param updateText
     * @param timeSpent
     * @param groupId
     * @param callrefs
     * @return {@link Request}
     * @throws IOException
     * @see Helpdesk#updateCalls(String, int, String...) (String, int, String...)
     * @see Helpdesk#assignCalls(String, String...)
     */
    public Response updateAndAssignCalls(String updateText, int timeSpent, String groupId, String... callrefs) throws IOException {
        Request request = generateRequest("updateAndAssignCalls");
        for (String callref :
                callrefs) {
            request.setParam("callref", callref);

        }
        request.setParam("timeSpent", Integer.toString(timeSpent));
        request.setParam("description", updateText);
        request.setParam("groupId", groupId);

        return invoke(request);
    }

    /**
     * update one or more calls
     * @param updateText Update text
     * @param timeSpent time spent on the call
     * @param callrefs one or more callrefs to update
     * @return {@link Request}
     * @throws IOException
     */
    public Response updateCalls(String updateText, int timeSpent, String ...callrefs) throws IOException {
        Request request = generateRequest("updateCalls");
        for (String callref :
                callrefs) {
            request.setParam("callref",callref);
        }
        request.setParam("timeSpent",Integer.toString(timeSpent));
        request.setParam("description",updateText);
        return invoke(request);
    }

}
