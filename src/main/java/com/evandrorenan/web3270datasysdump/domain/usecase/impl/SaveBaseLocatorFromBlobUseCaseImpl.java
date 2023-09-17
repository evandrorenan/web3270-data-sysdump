package com.evandrorenan.web3270datasysdump.domain.usecase.impl;

import com.evandrorenan.web3270datasysdump.domain.model.AbendReport;
import com.evandrorenan.web3270datasysdump.domain.usecase.SaveBaseLocatorFromBlobUseCase;
import com.evandrorenan.web3270datasysdump.domain.usecase.SaveBaseLocatorUseCase;
import com.evandrorenan.web3270datasysdump.domain.usecase.lineprocessor.ExtractAbendReportUseCase;
import com.evandrorenan.web3270datasysdump.repository.BlobRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class SaveBaseLocatorFromBlobUseCaseImpl implements SaveBaseLocatorFromBlobUseCase {

    private final SaveBaseLocatorUseCase saveBaseLocatorUseCase;

    private final ExtractAbendReportUseCase extractAbendReportUseCase;


    @Autowired
    public SaveBaseLocatorFromBlobUseCaseImpl(BlobRepository blobRepository,
                                              SaveBaseLocatorUseCase saveBaseLocatorUseCase,
                                              ExtractAbendReportUseCase extractAbendReportUseCase) {
        this.saveBaseLocatorUseCase = saveBaseLocatorUseCase;
        this.extractAbendReportUseCase = extractAbendReportUseCase;
    }

    @Override
    public AbendReport run(String blobId) {
        log.info("Starting to save base locators from blobId: {}", blobId);
        AbendReport abendReport = extractAbendReportUseCase.run(blobId);
        saveBaseLocatorUseCase.run(AbendReport);
    }
}