package com.evandrorenan.web3270datasysdump.domain.handler;

import com.evandrorenan.web3270datasysdump.domain.event.AbendReportEvent;
import com.evandrorenan.web3270datasysdump.domain.event.EventPublisher;
import com.evandrorenan.web3270datasysdump.domain.event.EventObserver;

/**
 * Base class for event handlers in the Abend Report workflow.
 * Provides common functionality for event handling and publishing.
 */
public abstract class BaseHandler implements EventObserver {
    protected final EventPublisher publisher;

    protected BaseHandler(EventPublisher publisher) {
        this.publisher = publisher;
        this.publisher.registerObserver(this);
    }
}
