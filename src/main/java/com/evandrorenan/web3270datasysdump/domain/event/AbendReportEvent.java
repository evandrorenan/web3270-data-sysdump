package com.evandrorenan.web3270datasysdump.domain.event;

public class AbendReportEvent {
    private final String requestId;
    private final EventType type;
    private final Object payload;

    public AbendReportEvent(String requestId, EventType type, Object payload) {
        this.requestId = requestId;
        this.type = type;
        this.payload = payload;
    }

    public String getRequestId() {
        return requestId;
    }

    public EventType getType() {
        return type;
    }

    public Object getPayload() {
        return payload;
    }

    @SuppressWarnings("unchecked")
    public <T> T getPayloadAs(Class<T> clazz) {
        return (T) payload;
    }
}
