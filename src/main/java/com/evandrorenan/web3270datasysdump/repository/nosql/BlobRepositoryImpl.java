package com.evandrorenan.web3270datasysdump.repository.nosql;

import com.azure.storage.blob.BlobClient;
import com.azure.storage.blob.BlobContainerClient;
import com.azure.storage.blob.BlobServiceClient;
import com.azure.storage.blob.BlobServiceClientBuilder;
import com.evandrorenan.web3270datasysdump.repository.BlobRepository;
import com.evandrorenan.web3270datasysdump.repository.util.BlobChunckHolder;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.Optional;

@Component
@Slf4j
public class BlobRepositoryImpl implements BlobRepository {

    private final String connectionString;
    private final String blobContainerName;
    private final Integer bufferSize;

    @Autowired
    public BlobRepositoryImpl(@Value("${blob-repository.blob-storage.connection-string}") String connectionString,
                              @Value("${blob-repository.blob-storage.container-name}") String blobContainerName,
                              @Value("${blob-repository.buffer-size:1920}") Integer bufferSize) {
        this.connectionString = connectionString;
        this.blobContainerName = blobContainerName;
        this.bufferSize = bufferSize;
    }

    @Override
    public InputStream getBlobAsInputStreamById(String blobId) {
        BlobServiceClient blobServiceClient = new BlobServiceClientBuilder()
                .connectionString(this.connectionString)
                .buildClient();
        BlobContainerClient blobContainerClient = blobServiceClient.getBlobContainerClient(this.blobContainerName);
        BlobClient blobClient = blobContainerClient.getBlobClient(blobId);
        return blobClient.openInputStream();
    }
}