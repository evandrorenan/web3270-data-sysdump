package com.evandrorenan.web3270datasysdump.domain.usecase;

import com.evandrorenan.web3270datasysdump.domain.model.BaseLocator;

import java.util.TreeMap;

public interface IExtractBaseLocatorFromSysdumpUseCase {

    TreeMap<String, BaseLocator> run(String rawInputArray) ;
}
