package com.evandrorenan.web3270datasysdump.domain.usecase;

public enum ReportLineProcessorType {

    ABEND_REPORT_HEADER_PROCESSOR("X"),
    BASE_LOCATOR_PROCESSOR("z");

    private final String regex;

    ReportLineProcessorType(String regex) {
        this.regex = regex;
    }
}
