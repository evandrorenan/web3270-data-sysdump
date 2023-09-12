package com.evandrorenan.web3270datasysdump.domain.usecase;

import com.evandrorenan.web3270datasysdump.domain.model.AbendReport;

public interface ReportLineProcessor {

    Boolean shouldProcess(String line);

    String process(String line, AbendReport abendReport);
}
