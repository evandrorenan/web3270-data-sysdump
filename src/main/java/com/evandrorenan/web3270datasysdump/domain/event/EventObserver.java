package com.evandrorenan.web3270datasysdump.domain.event;

public interface EventObserver {
    void onEvent(AbendReportEvent event);
}
