package com.evandrorenan.web3270datasysdump.repository;

import com.evandrorenan.web3270datasysdump.repository.util.BlobChunckHolder;

import java.io.InputStream;
import java.util.Optional;

public interface BlobRepository {
    InputStream getBlobInputStreamFromBlobId(String blobId);

    Optional<BlobChunckHolder> nextChunk(InputStream blobInputStream);

    Optional<BlobChunckHolder> nextChunk(InputStream blobInputStream, String leftOverData);
}
