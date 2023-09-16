package com.evandrorenan.web3270datasysdump.domain.gateway;

import com.evandrorenan.web3270datasysdump.infrastructure.adapters.BlobInputStreamHolder;
import com.evandrorenan.web3270datasysdump.repository.util.BlobChunckHolder;

import java.io.InputStream;
import java.util.Optional;

public interface BlobGateway {

    Optional<BlobChunckHolder> nextChunk(String blobId);

    Optional<BlobChunckHolder> nextChunk(InputStream blobInputStream, String leftOverData);

    BlobInputStreamHolder getBlobAsInputStreamById(String blobId);
}
