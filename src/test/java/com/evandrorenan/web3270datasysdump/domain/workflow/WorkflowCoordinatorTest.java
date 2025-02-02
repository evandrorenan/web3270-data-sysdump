package com.evandrorenan.web3270datasysdump.domain.workflow;

import com.evandrorenan.web3270datasysdump.domain.event.AbendReportEvent;
import com.evandrorenan.web3270datasysdump.domain.event.EventPublisher;
import com.evandrorenan.web3270datasysdump.domain.event.EventType;
import com.evandrorenan.web3270datasysdump.domain.model.AbendReportRequest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.verify;

class WorkflowCoordinatorTest {

    private WorkflowCoordinator coordinator;

    @Mock
    private EventPublisher publisher;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        coordinator = new WorkflowCoordinator(publisher);
    }

    @Test
    void shouldTriggerDataCollectionAfterInitialization() {
        // Given
        AbendReportRequest request = new AbendReportRequest("JOB123", "PROG1");
        AbendReportEvent event = new AbendReportEvent("REQ1", EventType.REQUEST_INITIALIZED, request);

        // When
        coordinator.onEvent(event);

        // Then
        ArgumentCaptor<AbendReportEvent> eventCaptor = ArgumentCaptor.forClass(AbendReportEvent.class);
        verify(publisher).notifyObservers(eventCaptor.capture());
        
        AbendReportEvent nextEvent = eventCaptor.getValue();
        assertEquals(EventType.DATA_COLLECTION_STARTED, nextEvent.getType());
        assertEquals("REQ1", nextEvent.getRequestId());
    }

    @Test
    void shouldTriggerReportExtractionAfterDataCollection() {
        // Given
        AbendReportEvent event = new AbendReportEvent("REQ1", EventType.DATA_COLLECTION_COMPLETED, "test-data");

        // When
        coordinator.onEvent(event);

        // Then
        ArgumentCaptor<AbendReportEvent> eventCaptor = ArgumentCaptor.forClass(AbendReportEvent.class);
        verify(publisher).notifyObservers(eventCaptor.capture());
        
        AbendReportEvent nextEvent = eventCaptor.getValue();
        assertEquals(EventType.REPORT_EXTRACTION_STARTED, nextEvent.getType());
        assertEquals("REQ1", nextEvent.getRequestId());
    }

    @Test
    void shouldTriggerBaseLocatorsSaveAfterExtraction() {
        // Given
        AbendReportEvent event = new AbendReportEvent("REQ1", EventType.REPORT_EXTRACTION_COMPLETED, "test-report");

        // When
        coordinator.onEvent(event);

        // Then
        ArgumentCaptor<AbendReportEvent> eventCaptor = ArgumentCaptor.forClass(AbendReportEvent.class);
        verify(publisher).notifyObservers(eventCaptor.capture());
        
        AbendReportEvent nextEvent = eventCaptor.getValue();
        assertEquals(EventType.BASE_LOCATORS_SAVED, nextEvent.getType());
        assertEquals("REQ1", nextEvent.getRequestId());
    }
}
