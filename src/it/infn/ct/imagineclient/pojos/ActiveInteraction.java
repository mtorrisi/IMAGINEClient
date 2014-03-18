package it.infn.ct.imagineclient.pojos;

import java.util.List;

public class ActiveInteraction {
	
	private int id;
    private String portal;
    private String application;
    private String userDescription;
    private String submissionTimestamp;
    private String Status;
    private List<ActiveInteraction> subjobsInfos;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getPortal() {
        return portal;
    }

    public void setPortal(String portal) {
        this.portal = portal;
    }

    public String getApplication() {
        return application;
    }

    public void setApplication(String application) {
        this.application = application;
    }

    public String getUserDescription() {
        return userDescription;
    }

    public void setUserDescription(String userDescription) {
        this.userDescription = userDescription;
    }

    public String getSubmissionTimestamp() {
        return submissionTimestamp;
    }

    public void setSubmissionTimestamp(String submissionTimestamp) {
        this.submissionTimestamp = submissionTimestamp;
    }

    public String getStatus() {
        return Status;
    }

    public void setStatus(String Status) {
        this.Status = Status;
    }

    public List<ActiveInteraction> getSubjobsInfos() {
        return subjobsInfos;
    }

    public void setSubjobsInfos(List<ActiveInteraction> values) {
        this.subjobsInfos = values;
    }
	
}
