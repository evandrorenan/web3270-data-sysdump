package com.evandrorenan.web3270datasysdump.domain.handler;

import com.evandrorenan.web3270datasysdump.domain.event.AbendReportEvent;
import com.evandrorenan.web3270datasysdump.domain.event.EventPublisher;
import com.evandrorenan.web3270datasysdump.domain.event.EventType;
import com.evandrorenan.web3270datasysdump.domain.model.AbendReportRequest;

import java.util.UUID;

/**
 * Handles the initialization of Abend Report requests.
 * Listens for REQUEST_INITIALIZED events and generates a unique protocol number.
 */
public class RequestInitializationHandler extends BaseHandler {
    
    /**
     * Creates a new request initialization handler.
     *
     * @param publisher The event publisher to use for event distribution
     */
    public RequestInitializationHandler(EventPublisher publisher) {
        super(publisher);
    }

    /**
     * Handles the REQUEST_INITIALIZED event by generating a protocol number
     * and publishing a completion event.
     *
     * @param event The event to handle
     */
    @Override
    public void onEvent(AbendReportEvent event) {
        if (event.getType() == EventType.REQUEST_INITIALIZED) {
            AbendReportRequest request = event.getPayloadAs(AbendReportRequest.class);
            request.setRequestProtocol(generateRequestProtocol());
            publisher.notifyObservers(new AbendReportEvent(
                event.getRequestId(),
                EventType.REQUEST_INITIALIZED,
                request
            ));
        }
    }

    /**
     * Generates a unique request protocol number.
     *
     * @return A unique protocol number
     */
    private String generateRequestProtocol() {
        return UUID.randomUUID().toString();
    }
}
