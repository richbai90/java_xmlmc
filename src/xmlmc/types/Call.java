package xmlmc.types;

import xmlmc.ComplexParam;
import xmlmc.Response;
import xmlmc.XmlMethodCall;
import xmlmc.api.Data;

import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * A class representing a supportworks call. Once one considers the possibility for additional call values
 * The possibilities of options to create a call become nearly innumerable. This Class abstracts most
 * of that configuration into setters and ensures that every call has the minimum required parameters
 * for the method it is being used with.
 */

public class Call implements SwType {
    private final XmlMethodCall xmlmc;
    private final ComplexParam param;
    private final String callClass;
    private final Map<String, Map<String, String>> additonalCallValues = new HashMap<>();
    private String summary;
    private String customer;
    private String priority;
    private String description;
    private String costCenter;
    private String probCode;
    private String site;
    private Integer condition;
    private int timeSpent = 5;
    private String updateCode = "API";
    private String updateSource = "XMLMC API";
    private String impact;
    private String urgency;
    private String sla;
    private String fileAttachment;
    private String customerName;
    private String organisation;
    private String groupId;
    private String analystId;
    private String logDate;
    private Status status;


    /**
     * An enumeration of call statuses and their respective numerical values
     * For use in the status setter. Useful for updating a call or creating a new call with a
     * unique status value.
     */
    public enum Status {
        PENDING(1),
        UNASSIGNED(2),
        UNACCEPTED(3),
        ONHOLD(4),
        OFFHOLD(5),
        RESOLVED(6),
        INCOMING(8),
        ESCALATED_OWNER(9),
        ESCALATED_GROUP(10),
        ESCALATED_ANY(11),
        CLOSED(16),
        CANCELLED(17),
        CLOSED_WITH_CHARGE(18);

        public int value;

        Status(int value) {
            this.value = value;
        }
    }

    /**
     * Instantiate the call
     *
     * @param xmlmc     The XmlMethodCall object that you are currently using. Required for database queries that are performed
     *                  automatically.
     * @param callClass The class of the call you wish to log
     * @throws ParserConfigurationException
     */
    public Call(XmlMethodCall xmlmc, String callClass) throws ParserConfigurationException {
        param = new ComplexParam("params");
        this.xmlmc = xmlmc;
        this.callClass = callClass;
    }


    /**
     * The buildXml method responsible for taking all the fields and parsing them into a ComplexParam in the correct order
     *
     * @return An xml object whose string representation may look like this
     * <div>
     * <pre>{@code
     *         <params>
     *             <callClass>Incident</callClass>
     *             <slaName>P1</slaName>
     *             ...other options
     *         </params>
     *     }</pre>
     * </div>
     */
    @Override
    public ComplexParam buildXml() {
        param.addParameter("callClass", callClass);
        parsePriority();
        if (customer != null) {
            param.addParameter("customerId", customer);
            param.addParameter("customerName", customerName);
            param.addParameter("costCenter", costCenter);
        }

        if (probCode != null) {
            param.addParameter("probCode", probCode);
        }

        if (site != null) {
            param.addParameter("site", site);
        }

        if (condition != null) {
            param.addParameter("condition", condition.toString());
        }

        if (logDate != null) {
            param.addParameter("logDate", logDate);
        }

        if (groupId != null) {
            param.addParameter("groupId", groupId);
            if (analystId != null) {
                param.addParameter("analystId", analystId);
            }
        }

        param.addParameter("timeSpent", Integer.toString(timeSpent));

        param.addParameter("updateMessage", (description != null) ? description : (summary != null) ? summary : "");

        param.addParameter("updateCode", updateCode);

        param.addParameter("updateSource", updateSource);

        if (fileAttachment != null) {
            try {
                EmbeddedFileAttachment embeddedFileAttachment = new EmbeddedFileAttachment(fileAttachment);
                param.addComplexParameter(embeddedFileAttachment.buildXml());
            } catch (ParserConfigurationException e) {
                e.printStackTrace();
            }
        }

        if (summary != null) {
            addAdditionalCallValue("opencall", "itsm_title", summary);
        }

        if (organisation != null) {
            addAdditionalCallValue("opencall", "companyname", organisation);
        }

        if (status != null) {
            addAdditionalCallValue("opencall", "status", Integer.toString(status.value));
        }

        try {
            param.addComplexParameter(parseAdditionalCallValues());
        } catch (ParserConfigurationException e) {
            e.printStackTrace();
        }

        return param;


    }

    private void parsePriority() {
        Data data = xmlmc.data();
        if (priority != null) {
            param.addParameter("slaName", priority);
        } else if (sla != null) {
            try {
                Response priority_record = data.sqlQuery(Data.Database.SWDATA, "Select fk_priority from itsmsp_slad_matrix where" +
                        String.format(" fk_slad = %s and fk_urgency = '%s' and fk_impact = '%s' ", sla, urgency, impact), false);
                this.priority = priority_record.getRow(0).get("fk_priority");
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else if (customer != null ) {
            try {
                Response priority_record = data.sqlQuery(Data.Database.SWDATA, "Select priority from userdb where" +
                        String.format(" keysearch = '%s'", customer), false);
                priority = priority_record.getRow(0).get("priority").isEmpty() ? null : priority_record.getRow(0).get("priority");
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        if (priority != null && !priority.isEmpty()) {
            param.addParameter("slaName", priority);
        }

    }

    private ComplexParam parseAdditionalCallValues() throws ParserConfigurationException {
        ComplexParam callValues = new ComplexParam("additionalCallValues");
        for (Map.Entry<String, Map<String, String>> tables :
                additonalCallValues.entrySet()
                ) {
            ComplexParam table = callValues.createChild(tables.getKey());
            for (Map.Entry<String, String> columns : tables.getValue().entrySet()) {
                table.createChild(columns.getKey(), columns.getValue());
            }
        }

        return callValues;
    }

    /**
     * Provides the ability to add additional call values other than those explicitly provided
     *
     * @param table  table name you wish to populate
     * @param column column in the table you wish to populate
     * @param value  value you wish to populate
     */
    public void addAdditionalCallValue(String table, String column, String value) {
        Map<String, String> tbl = (additonalCallValues.containsKey(table)) ? additonalCallValues.get(table) : new HashMap<>();
        tbl.put(column, value);
        if (!additonalCallValues.containsKey(table)) {
            additonalCallValues.put(table, tbl);
        }

    }

    /**
     * If you know the priority ID of the call you can specify it directly. Otherwise we will attempt to use sql queries
     * to infer what the priority should be.
     *
     * @param priority
     */
    public void setPriority(String priority) {
        this.priority = priority;
    }

    public void setCustomer(String customer) {
        Data data = xmlmc.data();
        this.customer = customer;
        try {
            Response customerRecord = data.sqlQuery(String.format("select firstname, surname, site, costcenter, fk_company_id " +
                    "from userdb where keysearch = '%s'", customer));
            String firstName = customerRecord.getRow(0).get("firstname");
            String lastName = customerRecord.getRow(0).get("surname");
            site = customerRecord.getRow(0).get("site");
            costCenter = customerRecord.getRow(0).get("costcenter");
            organisation = customerRecord.getRow(0).get("fk_company_id");
            customerName = firstName + " " + lastName;

            Response orgRecord = data.sqlQuery(String.format("select companyname from company where pk_company_id = '%s'", organisation));
            organisation = orgRecord.getRow(0).get("companyname");
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    /**
     * Provide a short description of the call. If this value is set and description is not set, description will be populated
     * with the value from summary.
     *
     * @param summary Short description of the call
     */
    public void setSummary(String summary) {
        this.summary = summary;
    }

    /**
     * Provide a long description of the problem.
     *
     * @param description problem text
     */
    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * If you know what the cost center should be you can specify it directly. Otherwise we will attempt to infer this
     * information from the customer selection if it is available. In this case, only set the customer, do not use this method.
     *
     * @param costCenter Cost center for the call
     */
    public void setCostCenter(String costCenter) {
        this.costCenter = costCenter;
    }

    /**
     * Add a problem profile to the call
     *
     * @param probCode Problem Profile Short Code
     */
    public void setProbCode(String probCode) {
        this.probCode = probCode;
    }

    /**
     * If you know what the site should be, you can specify it directly. Otherwise we will attempt to infer this information
     * from the customer selection if it is available. In this case, only set the customer do not use this method.
     *
     * @param site Site for the call
     */
    public void setSite(String site) {
        this.site = site;
    }

    /**
     * Set the call condition
     *
     * @param condition Numerical representation of the condition to set
     */
    public void setCondition(int condition) {
        this.condition = condition;
    }

    /**
     * Set the amount of time spent in minutes, on this call. If this is not set, the default is 5 minutes.
     *
     * @param timeSpent Amount of time spent on the call in minutes.
     */
    public void setTimeSpent(int timeSpent) {
        this.timeSpent = timeSpent;
    }

    /**
     * Set a custom update code for the first diary entry. If this is not set the default is API
     *
     * @param updateCode
     */
    public void setUpdateCode(String updateCode) {
        this.updateCode = updateCode;
    }

    /**
     * Provide a custom update source for the first diary entry. If this is not set the default is Xmlmc API
     *
     * @param updateSource
     */
    public void setUpdateSource(String updateSource) {
        this.updateSource = updateSource;
    }

    /**
     * Attach a file when logging the call.
     *
     * @param fileName the complete path to a file you wish to attach. Java must be able to access this path.
     */
    public void setFileAttachment(String fileName) {
        this.fileAttachment = fileName;
    }

    /**
     * Use this method if you would like the priority to be auotmatically calculated based on impact and urgency.
     *
     * @param sla     The SLA to use when making calculations
     * @param impact  The impact of the call
     * @param urgency The urgency of the call
     */
    public void setImpactAndUrgency(String sla, String impact, String urgency) {
        Data data = xmlmc.data();
        try {
            Response sladid_record = data.sqlQuery(Data.Database.SWDATA, String.format("select pk_slad_id from " +
                    "itsmsp_slad where slad_id = '%s'", sla), false);
            this.sla = sladid_record.getRow(0).get("pk_slad_id");
        } catch (IOException e) {
            e.printStackTrace();
        }

        this.impact = impact;
        this.urgency = urgency;

        addAdditionalCallValue("opencall","itsm_sladef", this.sla);
        addAdditionalCallValue("opencall", "itsm_impact_level", impact);
        addAdditionalCallValue("opencall", "itsm_urgency_level", urgency);
    }

    /**
     * This method is used by the {@link xmlmc.api.Helpdesk} API when assigning calls to groups. It does not need to be called
     * manually, and may result in an error if not used appropriately.
     *
     * @param group group to assign the call to.
     * @see xmlmc.api.Helpdesk#logAndAssignNewCall(Call, String)
     * @see Call#assignTo(String, String)
     */

    public void assignTo(String group) {
        groupId = group;
    }

    /**
     * Set the logDate of the call. This is required and set automatically when logging a deferred call, otherwise
     * It defaults to the current date and time.
     *
     * @param logDate The date and time to log the call in the format YYYY-MM-DDThh:mm:ss see:
     *                <a href = "http://www.w3schools.com/xml/schema_dtypes_date.asp">W3Schools</a> for more details
     */
    public void setLogDate(String logDate) {
        this.logDate = logDate;
    }

    /**
     * Set the status of the call. Useful mostly when performing updates, defaults to pending
     *
     * @param status status to set the call to
     * @see Status
     */
    public void setStatus(Status status) {
        this.status = status;
    }

    /**
     * Analyst to assign the call to. Is used by the {@link xmlmc.api.Helpdesk} API when logging and assigning calls
     * And could cause errors when called manually.
     *
     * @param group   Group to assign the call to
     * @param analyst Analyst to assign the call to
     * @see xmlmc.api.Helpdesk#logAndAssignNewCall(Call, String, String)
     * @see Call#assignTo(String)
     */
    public void assignTo(String group, String analyst) {
        groupId = group;
        analystId = analyst;
    }
}
