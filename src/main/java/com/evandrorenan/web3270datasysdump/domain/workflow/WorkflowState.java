package com.evandrorenan.web3270datasysdump.domain.workflow;

import com.evandrorenan.web3270datasysdump.domain.event.AbendReportEvent;
import com.evandrorenan.web3270datasysdump.domain.event.EventType;

/**
 * Represents a state in the Abend Report workflow.
 * Each state knows what the next state should be and what event to publish.
 */
public enum WorkflowState {
    INITIALIZED {
        @Override
        public EventType getNextEventType() {
            return EventType.DATA_COLLECTION_STARTED;
        }
    },
    DATA_COLLECTION {
        @Override
        public EventType getNextEventType() {
            return EventType.REPORT_EXTRACTION_STARTED;
        }
    },
    REPORT_EXTRACTION {
        @Override
        public EventType getNextEventType() {
            return EventType.BASE_LOCATORS_SAVED;
        }
    },
    BASE_LOCATORS_SAVED {
        @Override
        public EventType getNextEventType() {
            return null; // End state
        }
    };

    public abstract EventType getNextEventType();

    public static WorkflowState fromEvent(AbendReportEvent event) {
        return switch (event.getType()) {
            case REQUEST_INITIALIZED -> INITIALIZED;
            case DATA_COLLECTION_COMPLETED -> DATA_COLLECTION;
            case REPORT_EXTRACTION_COMPLETED -> REPORT_EXTRACTION;
            case BASE_LOCATORS_SAVED -> BASE_LOCATORS_SAVED;
            default -> throw new IllegalStateException("Unexpected event type: " + event.getType());
        };
    }
}
