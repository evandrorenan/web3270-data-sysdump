package com.evandrorenan.web3270datasysdump.repository;

import java.io.InputStream;

public interface BlobRepository {
    InputStream getBlobAsInputStreamById(String blobId);;;

}
