package com.evandrorenan.web3270datasysdump.domain.usecase;

import com.evandrorenan.web3270datasysdump.domain.model.AbendReport;
import com.evandrorenan.web3270datasysdump.domain.model.BaseLocator;

import java.util.List;

public interface SaveBaseLocatorUseCase {

    AbendReport run(AbendReport abendReport);

    @Deprecated(forRemoval = true, since = "17/09/23")
    List<BaseLocator> run(List<BaseLocator> baseLocator);

    @Deprecated(forRemoval = true, since = "17/09/23")
    List<BaseLocator> run(String rawInput);
}
