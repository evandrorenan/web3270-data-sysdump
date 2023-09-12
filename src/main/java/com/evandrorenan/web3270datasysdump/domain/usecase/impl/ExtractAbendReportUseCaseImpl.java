package com.evandrorenan.web3270datasysdump.domain.usecase.impl;

import com.evandrorenan.web3270datasysdump.domain.model.AbendReport;
import com.evandrorenan.web3270datasysdump.domain.usecase.ReportLineProcessor;
import com.evandrorenan.web3270datasysdump.domain.usecase.SaveBaseLocatorUseCase;
import com.evandrorenan.web3270datasysdump.repository.BlobRepository;
import com.evandrorenan.web3270datasysdump.repository.nosql.BlobInputStreamHolder;
import com.evandrorenan.web3270datasysdump.repository.util.BlobChunckHolder;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
@Slf4j
public class ExtractAbendReportUseCaseImpl {

    private final BlobRepository blobRepository;
    private final List<ReportLineProcessor> processors;

    @Autowired
    public ExtractAbendReportUseCaseImpl(
            BlobRepository blobRepository,
            List<ReportLineProcessor> processors) {
        this.blobRepository = blobRepository;
        this.processors = processors;
    }

    void run(String blobId) {
        this.run(blobId, AbendReport.builder().build());
    }

    void run(String blobId, AbendReport abendReport) {
        log.info("Starting to save base locators from blobId: {}", blobId);
        BlobInputStreamHolder holder = BlobInputStreamHolder.builder()
                .blobInputStream(blobRepository.getBlobInputStreamFromBlobId(blobId))
                .build();

        while (!holder.endReached()) {
            String currentLine = holder.nextLine();
            processors.forEach(processor -> {
                if (!processor.shouldProcess(currentLine)) return;
                processor.process(currentLine, abendReport);
            });
        }
    }
}
