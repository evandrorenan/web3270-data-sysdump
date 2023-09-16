package com.evandrorenan.web3270datasysdump.domain.usecase.lineprocessor;

import com.evandrorenan.web3270datasysdump.domain.model.AbendReport;

public interface ExtractAbendReportUseCase {

    AbendReport run(String blobId, AbendReport abendReport);
}
