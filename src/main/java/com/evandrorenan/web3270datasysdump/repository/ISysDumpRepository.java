package com.evandrorenan.web3270datasysdump.repository;

import com.evandrorenan.web3270datasysdump.domain.model.BaseLocator;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

public interface ISysDumpRepository extends MongoRepository<BaseLocator, String> {}
