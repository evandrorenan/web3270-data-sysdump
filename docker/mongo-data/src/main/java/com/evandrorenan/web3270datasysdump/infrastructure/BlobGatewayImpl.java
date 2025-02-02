package com.evandrorenan.web3270datasysdump.infrastructure;

import com.evandrorenan.web3270datasysdump.domain.gateway.BlobGateway;
import com.evandrorenan.web3270datasysdump.infrastructure.adapters.BlobInputStreamHolder;
import com.evandrorenan.web3270datasysdump.repository.BlobRepository;
import com.evandrorenan.web3270datasysdump.repository.azure.BlobInputStreamHolderImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class BlobGatewayImpl implements BlobGateway {

    private final BlobRepository blobRepository;

    @Autowired
    public BlobGatewayImpl(BlobRepository blobRepository,
                           @Value("${blob-repository.buffer-size:1920}") Integer bufferSize) {
        this.blobRepository = blobRepository;
    }

    @Override
    public BlobInputStreamHolder getBlobAsInputStreamById(String blobId) {
        return new BlobInputStreamHolderImpl(blobRepository.getBlobAsInputStreamById(blobId));
    }
}