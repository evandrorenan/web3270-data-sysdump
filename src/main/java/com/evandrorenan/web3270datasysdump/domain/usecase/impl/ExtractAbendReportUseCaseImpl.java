package com.evandrorenan.web3270datasysdump.domain.usecase.impl;

import com.evandrorenan.web3270datasysdump.domain.model.AbendReport;
import com.evandrorenan.web3270datasysdump.domain.usecase.lineprocessor.ReportLineProcessor;
import com.evandrorenan.web3270datasysdump.repository.BlobRepository;
import com.evandrorenan.web3270datasysdump.repository.nosql.BlobInputStreamHolder;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.InputStream;
import java.util.List;

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

    void run(String blobId, AbendReport abendReport) {
        log.info("Starting to save base locators from blobId: {}", blobId);
        InputStream blobIS = blobRepository.getBlobInputStreamFromBlobId(blobId);
        BlobInputStreamHolder holder = new BlobInputStreamHolder(blobIS, blobRepository);

        while (!holder.endReached()) {
            String currentLine = holder.nextLine();
            //Strategy Pattern - processors must be classified by priority
            for (ReportLineProcessor processor : processors) {
                abendReport = processor.process(currentLine, abendReport);
            }
        }
    }
}
