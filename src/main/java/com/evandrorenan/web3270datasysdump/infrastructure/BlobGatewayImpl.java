package com.evandrorenan.web3270datasysdump.infrastructure;

import com.evandrorenan.web3270datasysdump.domain.gateway.BlobGateway;
import com.evandrorenan.web3270datasysdump.infrastructure.adapters.BlobInputStreamHolder;
import com.evandrorenan.web3270datasysdump.repository.BlobRepository;
import com.evandrorenan.web3270datasysdump.repository.nosql.BlobInputStreamHolderImpl;
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
public class BlobGatewayImpl implements BlobGateway {

    private final BlobRepository blobRepository;
    private final Integer bufferSize;

    @Autowired
    public BlobGatewayImpl(BlobRepository blobRepository,
                           @Value("${blob-repository.buffer-size:1920}") Integer bufferSize) {
        this.blobRepository = blobRepository;
        this.bufferSize = bufferSize;
    }

    @Override
    public Optional<BlobChunckHolder> nextChunk(String blobId) {
        log.info("Will get first chunk.");
        InputStream blobInputStream = blobRepository.getBlobAsInputStreamById(blobId);
        return nextChunk(blobInputStream, "");
    }

    @Override
    public Optional<BlobChunckHolder> nextChunk(InputStream blobInputStream, String leftOverData) {
        log.info("Will get chunk. Left over data: {}", leftOverData);
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

    @Override
    public BlobInputStreamHolder getBlobAsInputStreamById(String blobId) {
        return BlobInputStreamHolderImpl
                .builder()
                .blobInputStream(blobRepository.getBlobAsInputStreamById(blobId))
                .build();
    }

    private static int getLastLineBreak(String chunk) {
        int lastLineBreak = chunk.lastIndexOf("\n");
        if (lastLineBreak == 0) return chunk.length();

        return lastLineBreak;
    }
}