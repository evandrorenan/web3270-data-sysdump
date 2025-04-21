package com.evandrorenan.web3270datasysdump.domain.workflow;

import com.evandrorenan.web3270datasysdump.domain.event.AbendReportEvent;
import com.evandrorenan.web3270datasysdump.domain.event.EventObserver;
import com.evandrorenan.web3270datasysdump.domain.event.EventPublisher;
import com.evandrorenan.web3270datasysdump.domain.event.EventType;

/**
 * Coordinates the workflow by managing state transitions and publishing appropriate events.
 * Uses a state machine to determine the next step in the workflow.
 */
public class WorkflowCoordinator implements EventObserver {
    private final EventPublisher publisher;

    public WorkflowCoordinator(EventPublisher publisher) {
        this.publisher = publisher;
        this.publisher.registerObserver(this);
    }

    @Override
    public void onEvent(AbendReportEvent event) {
        WorkflowState currentState = WorkflowState.fromEvent(event);
        EventType nextEventType = currentState.getNextEventType();
        
        if (nextEventType != null) {
            publisher.notifyObservers(new AbendReportEvent(
                event.getRequestId(),
                nextEventType,
                event.getPayload()
            ));
        }
    }
}
