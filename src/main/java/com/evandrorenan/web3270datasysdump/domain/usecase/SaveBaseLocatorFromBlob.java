package com.evandrorenan.web3270datasysdump.domain.usecase;

import com.evandrorenan.web3270datasysdump.domain.model.BaseLocator;
import java.util.List;

public interface SaveBaseLocatorFromBlob {
    List<BaseLocator> run(String blobId) throws Exception;
}
