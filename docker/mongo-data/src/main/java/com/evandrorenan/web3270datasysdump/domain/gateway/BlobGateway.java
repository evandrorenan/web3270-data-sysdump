package com.evandrorenan.web3270datasysdump.domain.gateway;

import com.evandrorenan.web3270datasysdump.infrastructure.adapters.BlobInputStreamHolder;

public interface BlobGateway {

    BlobInputStreamHolder getBlobAsInputStreamById(String blobId);
}
