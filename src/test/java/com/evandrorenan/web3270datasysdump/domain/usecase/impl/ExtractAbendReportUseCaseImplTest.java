package com.evandrorenan.web3270datasysdump.domain.usecase.impl;

import com.evandrorenan.web3270datasysdump.domain.model.AbendReport;
import com.evandrorenan.web3270datasysdump.domain.usecase.lineprocessor.ReportLineProcessor;
import com.evandrorenan.web3270datasysdump.repository.nosql.BlobRepositoryImpl;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.List;

import static org.mockito.Mockito.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
@SpringBootTest
public class ExtractAbendReportUseCaseImplTest {

    public static final String MOCKED_CONN_STRING = "DefaultEdsafndpointsProtocol=https\\;AccountName=stweb3270\\;AccountKey=xxx;EndpointSuffix=core";
    public static final String MOCKED_CONTAINER_NAME = "container";
    public static final int BUFFER_SIZE = 1024;

    @InjectMocks
    private BlobRepositoryImpl blobRepositoryMock;

    @Mock
    private List<ReportLineProcessor> processors;

    private ExtractAbendReportUseCaseImpl extractAbendReportUseCase;

    @BeforeEach
    void setUp() {
//        extractAbendReportUseCase = new ExtractAbendReportUseCaseImpl(blobRepositoryMock, processors);
    }

    @Test
    void testRun() throws FileNotFoundException {
        String blobId = "testBlobId";
        AbendReport abendReport = null;
        InputStream inputStream = buildInputStream();

        when(blobRepositoryMock.getBlobAsInputStreamById(any())).thenReturn(inputStream);
        when(blobRepositoryMock.nextChunk(any())).thenCallRealMethod();
        when(blobRepositoryMock.nextChunk(any(), any())).thenCallRealMethod();

//        abendReport = extractAbendReportUseCase.run(blobId, abendReport);

        Assertions.assertNotNull(abendReport);
    }

    private static FileInputStream buildInputStream() throws FileNotFoundException {
        String filePath = "src/test/resources/AbendReport.txt";
        File file = new File(filePath);
        return new FileInputStream(file);
    }
}
