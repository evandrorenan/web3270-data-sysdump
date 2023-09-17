package com.evandrorenan.web3270datasysdump.domain.usecase;

import com.evandrorenan.web3270datasysdump.domain.model.BaseLocator;

import java.util.List;

public interface SaveBaseLocatorUseCase {

    List<BaseLocator> run(List<BaseLocator> baseLocator);

    List<BaseLocator> run(String rawInput);
}
