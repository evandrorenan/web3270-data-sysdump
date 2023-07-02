package com.evandrorenan.web3270datasysdump.domain.usecase;

import com.evandrorenan.web3270datasysdump.repository.ISysDumpRepository;

public interface IFindByApproximatedAddressUseCase {

    void run(ISysDumpRepository repository);
}
