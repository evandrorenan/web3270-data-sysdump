package com.evandrorenan.web3270datasysdump.domain.model;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class AbendReportRequestTest {

    @Test
    void shouldCreateRequestWithInitialStatus() {
        // Given
        String jobId = "JOB123";
        String programName = "PROG1";

        // When
        AbendReportRequest request = new AbendReportRequest(jobId, programName);

        // Then
        assertEquals(jobId, request.getJobId());
        assertEquals(programName, request.getProgramName());
        assertEquals(RequestStatus.PENDING, request.getStatus());
        assertNull(request.getRequestProtocol());
    }

    @Test
    void shouldUpdateRequestProtocol() {
        // Given
        AbendReportRequest request = new AbendReportRequest("JOB123", "PROG1");
        String protocol = "PROT123";

        // When
        request.setRequestProtocol(protocol);

        // Then
        assertEquals(protocol, request.getRequestProtocol());
    }

    @Test
    void shouldUpdateStatus() {
        // Given
        AbendReportRequest request = new AbendReportRequest("JOB123", "PROG1");

        // When
        request.setStatus(RequestStatus.COLLECTING_DATA);

        // Then
        assertEquals(RequestStatus.COLLECTING_DATA, request.getStatus());
    }
}
