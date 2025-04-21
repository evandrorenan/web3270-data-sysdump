package com.evandrorenan.web3270datasysdump.domain.event;

import java.util.ArrayList;
import java.util.List;

/**
 * Implements the Observer pattern for event distribution in the Abend Report workflow.
 * This class manages observers and notifies them of workflow events.
 */
public class EventPublisher {
    private final List<EventObserver> observers = new ArrayList<>();

    /**
     * Registers a new observer to receive event notifications.
     *
     * @param observer The observer to register
     */
    public void registerObserver(EventObserver observer) {
        observers.add(observer);
    }

    /**
     * Removes an observer from the notification list.
     *
     * @param observer The observer to remove
     */
    public void removeObserver(EventObserver observer) {
        observers.remove(observer);
    }

    /**
     * Notifies all registered observers about an event.
     *
     * @param event The event to distribute to observers
     */
    public void notifyObservers(AbendReportEvent event) {
        observers.forEach(observer -> observer.onEvent(event));
    }
}
