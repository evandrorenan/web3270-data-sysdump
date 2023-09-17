package com.evandrorenan.web3270datasysdump.domain.usecase;

import com.evandrorenan.web3270datasysdump.domain.model.AbendReport;
import com.evandrorenan.web3270datasysdump.domain.model.BaseLocator;

import java.util.List;

public interface SaveBaseLocatorUseCase {

    List<BaseLocator> run(AbendReport abendReport);

    @Deprecated
    List<BaseLocator> run(List<BaseLocator> baseLocator);

    @Deprecated
    List<BaseLocator> run(String rawInput);
}
