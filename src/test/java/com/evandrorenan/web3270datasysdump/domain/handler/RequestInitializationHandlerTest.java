package com.evandrorenan.web3270datasysdump.domain.handler;

import com.evandrorenan.web3270datasysdump.domain.event.AbendReportEvent;
import com.evandrorenan.web3270datasysdump.domain.event.EventPublisher;
import com.evandrorenan.web3270datasysdump.domain.event.EventType;
import com.evandrorenan.web3270datasysdump.domain.model.AbendReportRequest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class RequestInitializationHandlerTest {

    private RequestInitializationHandler handler;

    @Mock
    private EventPublisher publisher;

    @Mock
    private AbendReportHandler nextHandler;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        handler = new RequestInitializationHandler(publisher);
        handler.setNext(nextHandler);
    }

    @Test
    void shouldHandleRequestInitializedEvent() {
        // Given
        String requestId = "REQ123";
        AbendReportRequest request = new AbendReportRequest("JOB123", "PROG1");
        AbendReportEvent event = new AbendReportEvent(requestId, EventType.REQUEST_INITIALIZED, request);

        // When
        handler.handle(event);

        // Then
        ArgumentCaptor<AbendReportEvent> eventCaptor = ArgumentCaptor.forClass(AbendReportEvent.class);
        verify(publisher).notifyObservers(eventCaptor.capture());
        
        AbendReportEvent capturedEvent = eventCaptor.getValue();
        assertEquals(requestId, capturedEvent.getRequestId());
        assertEquals(EventType.DATA_COLLECTION_STARTED, capturedEvent.getType());
        
        AbendReportRequest capturedRequest = capturedEvent.getPayloadAs(AbendReportRequest.class);
        assertNotNull(capturedRequest.getRequestProtocol());
        verify(nextHandler).handle(event);
    }

    @Test
    void shouldIgnoreNonInitializedEvents() {
        // Given
        AbendReportEvent event = new AbendReportEvent(
            "REQ123",
            EventType.DATA_COLLECTION_STARTED,
            new AbendReportRequest("JOB123", "PROG1")
        );

        // When
        handler.handle(event);

        // Then
        verify(publisher, never()).notifyObservers(any());
        verify(nextHandler).handle(event);
    }
}
