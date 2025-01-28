package com.evandrorenan.web3270datasysdump.domain.usecase;

import com.evandrorenan.web3270datasysdump.domain.model.AbendReport;

public interface SaveBaseLocatorFromBlobUseCase {
    AbendReport run(String blobId);
}
