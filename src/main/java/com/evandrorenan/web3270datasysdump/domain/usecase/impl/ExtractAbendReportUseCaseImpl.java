package com.evandrorenan.web3270datasysdump.domain.usecase.impl;

import com.evandrorenan.web3270datasysdump.domain.gateway.BlobGateway;
import com.evandrorenan.web3270datasysdump.domain.model.AbendReport;
import com.evandrorenan.web3270datasysdump.domain.model.Program;
import com.evandrorenan.web3270datasysdump.domain.usecase.lineprocessor.ExtractAbendReportUseCase;
import com.evandrorenan.web3270datasysdump.domain.usecase.lineprocessor.ReportLineProcessor;
import com.evandrorenan.web3270datasysdump.infrastructure.adapters.BlobInputStreamHolder;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
@Slf4j
public class ExtractAbendReportUseCaseImpl implements ExtractAbendReportUseCase {

    private final BlobGateway blobGateway;
    private final List<ReportLineProcessor> processors;

    @Autowired
    public ExtractAbendReportUseCaseImpl(
            BlobGateway blobGateway,
            List<ReportLineProcessor> processors) {
        this.blobGateway = blobGateway;
        this.processors = processors;
    }

    @Override
    public AbendReport run(String blobId) {
        log.info("Starting to save base locators from blobId: {}", blobId);

        BlobInputStreamHolder holder = blobGateway.getBlobAsInputStreamById(blobId);

        var abendReport = AbendReport
                .builder()
                .program(Program.builder().build())
                .baseLocators(new ArrayList<>())
                .build();

        Map<String, String> extractedData = new HashMap<>();

        holder.forEachLine(
            line -> processors.forEach(p -> p.process(line, abendReport)),
                extractedData::put);

        extractedData.values().forEach(log::info);
        return abendReport;
    }
}
