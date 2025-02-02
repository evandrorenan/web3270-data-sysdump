package com.evandrorenan.web3270datasysdump.domain.model;

/**
 * Represents a request to generate an Abend Report.
 * This class maintains the state and metadata of the report generation process.
 */
public class AbendReportRequest {
    private final String jobId;
    private final String programName;
    private String requestProtocol;
    private RequestStatus status;

    /**
     * Creates a new Abend Report request.
     *
     * @param jobId The ID of the job that generated the Abend
     * @param programName The name of the program that generated the Abend
     */
    public AbendReportRequest(String jobId, String programName) {
        this.jobId = jobId;
        this.programName = programName;
        this.status = RequestStatus.PENDING;
    }

    /**
     * @return The job ID associated with this request
     */
    public String getJobId() {
        return jobId;
    }

    /**
     * @return The program name associated with this request
     */
    public String getProgramName() {
        return programName;
    }

    /**
     * @return The unique protocol number assigned to this request
     */
    public String getRequestProtocol() {
        return requestProtocol;
    }

    /**
     * Sets the protocol number for this request.
     *
     * @param requestProtocol The protocol number to assign
     */
    public void setRequestProtocol(String requestProtocol) {
        this.requestProtocol = requestProtocol;
    }

    /**
     * @return The current status of this request
     */
    public RequestStatus getStatus() {
        return status;
    }

    /**
     * Updates the status of this request.
     *
     * @param status The new status to set
     */
    public void setStatus(RequestStatus status) {
        this.status = status;
    }
}
