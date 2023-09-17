package com.evandrorenan.web3270datasysdump.domain.usecase.impl;

import com.evandrorenan.web3270datasysdump.domain.model.BaseLocator;
import com.evandrorenan.web3270datasysdump.domain.usecase.ExtractBaseLocatorFromSysdumpUseCase;
import com.evandrorenan.web3270datasysdump.domain.usecase.SaveBaseLocatorUseCase;
import com.evandrorenan.web3270datasysdump.repository.SysDumpRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Slf4j
@Component
public class SaveBaseLocatorUseCaseImpl implements SaveBaseLocatorUseCase {

    private final SysDumpRepository repo;

    private final ExtractBaseLocatorFromSysdumpUseCase extractUseCase;

    @Autowired
    public SaveBaseLocatorUseCaseImpl(SysDumpRepository repo,
                                      ExtractBaseLocatorFromSysdumpUseCase extractUseCase) {
        this.extractUseCase = extractUseCase;
        this.repo = repo;
    }

    @Override
    public List<BaseLocator> run(List<BaseLocator> baseLocators) {
        return repo.saveAll(baseLocators);
    }

    @Override
    public List<BaseLocator> run(String rawInput) {
        log.info("Starting to run SaveBaseLocatorUseCase");
        var baseLocators= this.extractUseCase.run(rawInput);
        return repo.saveAll(baseLocators.values());
    }
}
