package com.evandrorenan.web3270datasysdump.domain.usecase.impl;

import com.evandrorenan.web3270datasysdump.domain.model.BaseLocator;
import com.evandrorenan.web3270datasysdump.domain.usecase.IExtractBaseLocatorFromSysdumpUseCase;
import com.evandrorenan.web3270datasysdump.domain.usecase.ISaveBaseLocatorUseCase;
import com.evandrorenan.web3270datasysdump.repository.ISysDumpRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Slf4j
@Component
public class SaveBaseLocatorUseCase implements ISaveBaseLocatorUseCase {

    private final ISysDumpRepository repo;

    private final IExtractBaseLocatorFromSysdumpUseCase extractUseCase;

    @Autowired
    public SaveBaseLocatorUseCase(ISysDumpRepository repo,
                                  IExtractBaseLocatorFromSysdumpUseCase extractUseCase) {
        this.extractUseCase = extractUseCase;
        this.repo = repo;
    }

    @Override
    public List<BaseLocator> run(List<BaseLocator> baseLocators) {
        return repo.saveAll(baseLocators);
    }

    @Override
    public List<BaseLocator> run(String rawInput) throws Exception {
        log.info("Starting to run SaveBaseLocatorUseCase");
        var baseLocators= this.extractUseCase.run(rawInput);
        return repo.saveAll(baseLocators.values());
    }
}
