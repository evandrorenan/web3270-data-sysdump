package com.evandrorenan.web3270datasysdump.domain.usecase.impl;

import com.evandrorenan.web3270datasysdump.domain.gateway.BlobGateway;
import com.evandrorenan.web3270datasysdump.domain.model.AbendReport;
import com.evandrorenan.web3270datasysdump.domain.usecase.lineprocessor.ReportLineProcessor;
import com.evandrorenan.web3270datasysdump.domain.usecase.lineprocessor.impl.AbendReportHeaderLineProcessor;
import com.evandrorenan.web3270datasysdump.domain.usecase.lineprocessor.impl.BaseLocatorLineProcessor;
import com.evandrorenan.web3270datasysdump.domain.usecase.lineprocessor.impl.ProgramDefinitionLineProcessor;
import com.evandrorenan.web3270datasysdump.domain.usecase.lineprocessor.impl.StorageSectionLineProcessor;
import com.evandrorenan.web3270datasysdump.infrastructure.adapters.BlobInputStreamHolder;
import com.evandrorenan.web3270datasysdump.repository.nosql.BlobInputStreamHolderImpl;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.List;

import static org.mockito.Mockito.any;
import static org.mockito.Mockito.when;

class ExtractAbendReportUseCaseImplTest {

    private ExtractAbendReportUseCaseImpl extractAbendReportUseCase;

    @BeforeEach
    void setUp() {

    }

    @Test
    void testRun() throws FileNotFoundException {
        BlobGateway blobGatewayMock = Mockito.mock(BlobGateway.class);
        when(blobGatewayMock.getBlobAsInputStreamById(any())).thenReturn(buildInputStream());
        List<ReportLineProcessor> processors = List.of(
                new AbendReportHeaderLineProcessor(),
                new BaseLocatorLineProcessor(),
                new ProgramDefinitionLineProcessor(),
                new StorageSectionLineProcessor());
        extractAbendReportUseCase = new ExtractAbendReportUseCaseImpl(blobGatewayMock, processors);

        String blobId = "testBlobId";

        AbendReport abendReport = extractAbendReportUseCase.run(blobId);

        Assertions.assertNotNull(abendReport);
    }

    private static BlobInputStreamHolder buildInputStream() throws FileNotFoundException {
        String filePath = "src/test/resources/AbendReport.txt";
        File file = new File(filePath);

        return new BlobInputStreamHolderImpl(new FileInputStream(file));
    }
}
