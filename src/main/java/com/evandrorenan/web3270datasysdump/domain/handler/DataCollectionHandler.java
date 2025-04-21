package com.evandrorenan.web3270datasysdump.domain.handler;

import com.evandrorenan.web3270datasysdump.domain.event.AbendReportEvent;
import com.evandrorenan.web3270datasysdump.domain.event.EventPublisher;
import com.evandrorenan.web3270datasysdump.domain.event.EventType;
import com.evandrorenan.web3270datasysdump.domain.model.AbendReportRequest;
import com.evandrorenan.web3270datasysdump.domain.model.Blob;
import com.evandrorenan.web3270datasysdump.domain.storage.InMemoryStorage;

import java.util.UUID;

/**
 * Handles data collection for Abend Report requests.
 * Listens for DATA_COLLECTION_STARTED events and collects raw data.
 */
public class DataCollectionHandler extends BaseHandler {
    private final InMemoryStorage storage;

    /**
     * Creates a new data collection handler.
     *
     * @param publisher The event publisher to use for event distribution
     * @param storage The storage to use for saving collected data
     */
    public DataCollectionHandler(EventPublisher publisher, InMemoryStorage storage) {
        super(publisher);
        this.storage = storage;
    }

    /**
     * Handles the DATA_COLLECTION_STARTED event by collecting raw data
     * and publishing a completion event.
     *
     * @param event The event to handle
     */
    @Override
    public void onEvent(AbendReportEvent event) {
        if (event.getType() == EventType.DATA_COLLECTION_STARTED) {
            AbendReportRequest request = event.getPayloadAs(AbendReportRequest.class);
            Blob blob = collectRawData(request);
            storage.save(event.getRequestId() + "_blob", blob);
            
            publisher.notifyObservers(new AbendReportEvent(
                event.getRequestId(),
                EventType.DATA_COLLECTION_COMPLETED,
                blob
            ));
        }
    }

    /**
     * Collects raw data for the given request.
     *
     * @param request The request to collect data for
     * @return A blob containing the collected data
     */
    private Blob collectRawData(AbendReportRequest request) {
        // Simulated data collection
        String rawData = String.format("Raw data for job %s and program %s", 
            request.getJobId(), request.getProgramName());
        return new Blob(
            rawData.getBytes(),
            UUID.randomUUID().toString(),
            "text/plain"
        );
    }
}
