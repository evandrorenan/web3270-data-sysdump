package com.evandrorenan.web3270datasysdump.domain.event;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.mockito.Mockito.*;

class EventPublisherTest {

    private EventPublisher publisher;

    @Mock
    private EventObserver observer1;

    @Mock
    private EventObserver observer2;

    @Mock
    private AbendReportEvent event;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        publisher = new EventPublisher();
    }

    @Test
    void shouldNotifyRegisteredObservers() {
        // Given
        publisher.registerObserver(observer1);
        publisher.registerObserver(observer2);

        // When
        publisher.notifyObservers(event);

        // Then
        verify(observer1).onEvent(event);
        verify(observer2).onEvent(event);
    }

    @Test
    void shouldNotNotifyRemovedObserver() {
        // Given
        publisher.registerObserver(observer1);
        publisher.registerObserver(observer2);
        publisher.removeObserver(observer1);

        // When
        publisher.notifyObservers(event);

        // Then
        verify(observer1, never()).onEvent(event);
        verify(observer2).onEvent(event);
    }

    @Test
    void shouldHandleNotifyWithNoObservers() {
        // When
        publisher.notifyObservers(event);

        // Then
        verify(observer1, never()).onEvent(event);
        verify(observer2, never()).onEvent(event);
    }
}
