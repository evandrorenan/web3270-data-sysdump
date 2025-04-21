package com.evandrorenan.web3270datasysdump.domain.workflow;

import com.evandrorenan.web3270datasysdump.domain.event.AbendReportEvent;
import com.evandrorenan.web3270datasysdump.domain.event.EventType;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class WorkflowStateTest {

    @Test
    void shouldDetermineCorrectNextEventType() {
        assertEquals(EventType.DATA_COLLECTION_STARTED, WorkflowState.INITIALIZED.getNextEventType());
        assertEquals(EventType.REPORT_EXTRACTION_STARTED, WorkflowState.DATA_COLLECTION.getNextEventType());
        assertEquals(EventType.BASE_LOCATORS_SAVED, WorkflowState.REPORT_EXTRACTION.getNextEventType());
        assertNull(WorkflowState.BASE_LOCATORS_SAVED.getNextEventType());
    }

    @Test
    void shouldMapEventToCorrectState() {
        assertEquals(
            WorkflowState.INITIALIZED,
            WorkflowState.fromEvent(new AbendReportEvent("REQ1", EventType.REQUEST_INITIALIZED, null))
        );
        assertEquals(
            WorkflowState.DATA_COLLECTION,
            WorkflowState.fromEvent(new AbendReportEvent("REQ1", EventType.DATA_COLLECTION_COMPLETED, null))
        );
        assertEquals(
            WorkflowState.REPORT_EXTRACTION,
            WorkflowState.fromEvent(new AbendReportEvent("REQ1", EventType.REPORT_EXTRACTION_COMPLETED, null))
        );
        assertEquals(
            WorkflowState.BASE_LOCATORS_SAVED,
            WorkflowState.fromEvent(new AbendReportEvent("REQ1", EventType.BASE_LOCATORS_SAVED, null))
        );
    }

    @Test
    void shouldThrowExceptionForUnexpectedEventType() {
        AbendReportEvent unexpectedEvent = new AbendReportEvent("REQ1", EventType.DATA_COLLECTION_STARTED, null);
        assertThrows(IllegalStateException.class, () -> WorkflowState.fromEvent(unexpectedEvent));
    }
}
