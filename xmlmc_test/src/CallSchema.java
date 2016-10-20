/**
 * A class for mapping a call from json
 */



        import java.util.HashMap;
        import java.util.Map;
        import javax.annotation.Generated;

        import com.fasterxml.jackson.annotation.JsonAnyGetter;
        import com.fasterxml.jackson.annotation.JsonAnySetter;
        import com.fasterxml.jackson.annotation.JsonIgnore;
        import com.fasterxml.jackson.annotation.JsonInclude;
        import com.fasterxml.jackson.annotation.JsonProperty;
        import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonInclude(JsonInclude.Include.NON_NULL)
@Generated("org.jsonschema2pojo")
@JsonPropertyOrder({
        "callClass",
        "summary",
        "customer",
        "priority",
        "description",
        "probCode",
        "site",
        "condition",
        "timeSpent",
        "updateCode",
        "updateSource",
        "impact",
        "urgency",
        "sla",
        "organisation",
        "groupId",
        "analystId",
        "logDate",
        "status"
})
public class CallSchema {

    @JsonProperty("callClass")
    private String callClass;
    @JsonProperty("summary")
    private String summary;
    @JsonProperty("customer")
    private String customer;
    @JsonProperty("priority")
    private String priority;
    @JsonProperty("description")
    private String description;
    @JsonProperty("probCode")
    private String probCode;
    @JsonProperty("site")
    private String site;
    @JsonProperty("condition")
    private String condition;
    @JsonProperty("timeSpent")
    private Integer timeSpent;
    @JsonProperty("updateCode")
    private String updateCode;
    @JsonProperty("updateSource")
    private String updateSource;
    @JsonProperty("impact")
    private String impact;
    @JsonProperty("urgency")
    private String urgency;
    @JsonProperty("sla")
    private String sla;
    @JsonProperty("organisation")
    private String organisation;
    @JsonProperty("groupId")
    private String groupId;
    @JsonProperty("analystId")
    private String analystId;
    @JsonProperty("logDate")
    private String logDate;
    @JsonProperty("status")
    private String status;
    @JsonIgnore
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();

    /**
     *
     * @return
     * The callClass
     */
    @JsonProperty("callClass")
    public String getCallClass() {
        return callClass;
    }

    /**
     *
     * @param callClass
     * The callClass
     */
    @JsonProperty("callClass")
    public void setCallClass(String callClass) {
        this.callClass = callClass;
    }

    /**
     *
     * @return
     * The summary
     */
    @JsonProperty("summary")
    public String getSummary() {
        return summary;
    }

    /**
     *
     * @param summary
     * The summary
     */
    @JsonProperty("summary")
    public void setSummary(String summary) {
        this.summary = summary;
    }

    /**
     *
     * @return
     * The customer
     */
    @JsonProperty("customer")
    public String getCustomer() {
        return customer;
    }

    /**
     *
     * @param customer
     * The customer
     */
    @JsonProperty("customer")
    public void setCustomer(String customer) {
        this.customer = customer;
    }

    /**
     *
     * @return
     * The priority
     */
    @JsonProperty("priority")
    public String getPriority() {
        return priority;
    }

    /**
     *
     * @param priority
     * The priority
     */
    @JsonProperty("priority")
    public void setPriority(String priority) {
        this.priority = priority;
    }

    /**
     *
     * @return
     * The description
     */
    @JsonProperty("description")
    public String getDescription() {
        return description;
    }

    /**
     *
     * @param description
     * The description
     */
    @JsonProperty("description")
    public void setDescription(String description) {
        this.description = description;
    }

    /**
     *
     * @return
     * The probCode
     */
    @JsonProperty("probCode")
    public String getProbCode() {
        return probCode;
    }

    /**
     *
     * @param probCode
     * The probCode
     */
    @JsonProperty("probCode")
    public void setProbCode(String probCode) {
        this.probCode = probCode;
    }

    /**
     *
     * @return
     * The site
     */
    @JsonProperty("site")
    public String getSite() {
        return site;
    }

    /**
     *
     * @param site
     * The site
     */
    @JsonProperty("site")
    public void setSite(String site) {
        this.site = site;
    }

    /**
     *
     * @return
     * The condition
     */
    @JsonProperty("condition")
    public String getCondition() {
        return condition;
    }

    /**
     *
     * @param condition
     * The condition
     */
    @JsonProperty("condition")
    public void setCondition(String condition) {
        this.condition = condition;
    }

    /**
     *
     * @return
     * The timeSpent
     */
    @JsonProperty("timeSpent")
    public Integer getTimeSpent() {
        return timeSpent;
    }

    /**
     *
     * @param timeSpent
     * The timeSpent
     */
    @JsonProperty("timeSpent")
    public void setTimeSpent(Integer timeSpent) {
        this.timeSpent = timeSpent;
    }

    /**
     *
     * @return
     * The updateCode
     */
    @JsonProperty("updateCode")
    public String getUpdateCode() {
        return updateCode;
    }

    /**
     *
     * @param updateCode
     * The updateCode
     */
    @JsonProperty("updateCode")
    public void setUpdateCode(String updateCode) {
        this.updateCode = updateCode;
    }

    /**
     *
     * @return
     * The updateSource
     */
    @JsonProperty("updateSource")
    public String getUpdateSource() {
        return updateSource;
    }

    /**
     *
     * @param updateSource
     * The updateSource
     */
    @JsonProperty("updateSource")
    public void setUpdateSource(String updateSource) {
        this.updateSource = updateSource;
    }

    /**
     *
     * @return
     * The impact
     */
    @JsonProperty("impact")
    public String getImpact() {
        return impact;
    }

    /**
     *
     * @param impact
     * The impact
     */
    @JsonProperty("impact")
    public void setImpact(String impact) {
        this.impact = impact;
    }

    /**
     *
     * @return
     * The urgency
     */
    @JsonProperty("urgency")
    public String getUrgency() {
        return urgency;
    }

    /**
     *
     * @param urgency
     * The urgency
     */
    @JsonProperty("urgency")
    public void setUrgency(String urgency) {
        this.urgency = urgency;
    }

    /**
     *
     * @return
     * The sla
     */
    @JsonProperty("sla")
    public String getSla() {
        return sla;
    }

    /**
     *
     * @param sla
     * The sla
     */
    @JsonProperty("sla")
    public void setSla(String sla) {
        this.sla = sla;
    }

    /**
     *
     * @return
     * The organisation
     */
    @JsonProperty("organisation")
    public String getOrganisation() {
        return organisation;
    }

    /**
     *
     * @param organisation
     * The organisation
     */
    @JsonProperty("organisation")
    public void setOrganisation(String organisation) {
        this.organisation = organisation;
    }

    /**
     *
     * @return
     * The groupId
     */
    @JsonProperty("groupId")
    public String getGroupId() {
        return groupId;
    }

    /**
     *
     * @param groupId
     * The groupId
     */
    @JsonProperty("groupId")
    public void setGroupId(String groupId) {
        this.groupId = groupId;
    }

    /**
     *
     * @return
     * The analystId
     */
    @JsonProperty("analystId")
    public String getAnalystId() {
        return analystId;
    }

    /**
     *
     * @param analystId
     * The analystId
     */
    @JsonProperty("analystId")
    public void setAnalystId(String analystId) {
        this.analystId = analystId;
    }

    /**
     *
     * @return
     * The logDate
     */
    @JsonProperty("logDate")
    public String getLogDate() {
        return logDate;
    }

    /**
     *
     * @param logDate
     * The logDate
     */
    @JsonProperty("logDate")
    public void setLogDate(String logDate) {
        this.logDate = logDate;
    }

    /**
     *
     * @return
     * The status
     */
    @JsonProperty("status")
    public String getStatus() {
        return status;
    }

    /**
     *
     * @param status
     * The status
     */
    @JsonProperty("status")
    public void setStatus(String status) {
        this.status = status;
    }

    @JsonAnyGetter
    public Map<String, Object> getAdditionalProperties() {
        return this.additionalProperties;
    }

    @JsonAnySetter
    public void setAdditionalProperty(String name, Object value) {
        this.additionalProperties.put(name, value);
    }

}
