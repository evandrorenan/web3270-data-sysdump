package com.evandrorenan.web3270datasysdump.domain.handler;

import com.evandrorenan.web3270datasysdump.domain.event.AbendReportEvent;

public interface AbendReportHandler {
    void handle(AbendReportEvent event);
    void setNext(AbendReportHandler next);
}
