package com.evandrorenan.web3270datasysdump.repository;

import com.evandrorenan.web3270datasysdump.domain.model.BaseLocator;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface SysDumpRepository extends MongoRepository<BaseLocator, String> {}
