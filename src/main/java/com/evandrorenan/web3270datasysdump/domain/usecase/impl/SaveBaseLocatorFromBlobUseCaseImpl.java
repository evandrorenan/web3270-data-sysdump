package com.evandrorenan.web3270datasysdump.domain.usecase.impl;

import com.evandrorenan.web3270datasysdump.domain.usecase.SaveBaseLocatorFromBlobUseCase;
import com.evandrorenan.web3270datasysdump.domain.usecase.SaveBaseLocatorUseCase;
import com.evandrorenan.web3270datasysdump.repository.BlobRepository;
import com.evandrorenan.web3270datasysdump.repository.util.BlobChunckHolder;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.InputStream;
import java.util.Optional;

@Component
@Slf4j
public class SaveBaseLocatorFromBlobUseCaseImpl implements SaveBaseLocatorFromBlobUseCase {

    private final BlobRepository blobRepository;
    private final SaveBaseLocatorUseCase saveBaseLocatorUseCase;

    @Autowired
    public SaveBaseLocatorFromBlobUseCaseImpl(BlobRepository blobRepository, SaveBaseLocatorUseCase saveBaseLocatorUseCase) {
        this.blobRepository = blobRepository;
        this.saveBaseLocatorUseCase = saveBaseLocatorUseCase;
    }

    @Override
    public void run(String blobId) throws Exception {
        log.info("Starting to save base locators from blobId: {}", blobId);
        InputStream blobIS = blobRepository.getBlobInputStreamFromBlobId(blobId);
        Optional<BlobChunckHolder> optBlobChunckHolder = blobRepository.nextChunk(blobIS);

        while (optBlobChunckHolder.isPresent()) {
            BlobChunckHolder holder = optBlobChunckHolder.get();
            log.info("Chunck size: {}", holder.getChunckData().length());
            log.info("Leftover data: {}", holder.getLeftOverData());
            saveBaseLocatorUseCase.run(holder.getChunckData());
            blobRepository.nextChunk(blobIS, holder.getLeftOverData());
        }
    }
}