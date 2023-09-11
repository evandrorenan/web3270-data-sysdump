package com.evandrorenan.web3270datasysdump.repository;

import com.azure.storage.blob.BlobClient;
import com.azure.storage.blob.BlobContainerClient;
import com.azure.storage.blob.BlobServiceClient;
import com.azure.storage.blob.BlobServiceClientBuilder;
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
    public InputStream getBlobInputStreamFromBlobId(String blobId) {
        BlobServiceClient blobServiceClient = new BlobServiceClientBuilder()
                .connectionString(this.connectionString)
                .buildClient();
        BlobContainerClient blobContainerClient = blobServiceClient.getBlobContainerClient(this.blobContainerName);
        BlobClient blobClient = blobContainerClient.getBlobClient(blobId);
        return blobClient.openInputStream();
    }

    @Override
    public Optional<BlobChunckHolder> nextChunk(InputStream blobInputStream) {
        return nextChunk(blobInputStream, "");
    }

    @Override
    public Optional<BlobChunckHolder> nextChunk(InputStream blobInputStream, String leftOverData) {
        try {
            byte[] blobBytes = blobInputStream.readNBytes(bufferSize);
            if (blobBytes.length == 0 && leftOverData.isEmpty()) return Optional.empty();

            String chunk = leftOverData + new String(blobBytes, StandardCharsets.UTF_8);
            int lastLineBreak = getLastLineBreak(chunk);

            return Optional.of(BlobChunckHolder.builder()
                                   .chunckData(chunk.substring(0, lastLineBreak))
                                   .leftOverData(chunk.substring(lastLineBreak))
                                   .build());

        } catch (IOException e) {
            log.error("Error trying to read blobInputStream: {}", e.getMessage());
            throw new RuntimeException(e);
        }
    }

    private static int getLastLineBreak(String chunk) {
        int lastLineBreak = chunk.lastIndexOf("\n");
        if (lastLineBreak == 0) return chunk.length();

        return lastLineBreak;
    }
}