package com.evandrorenan.web3270datasysdump.repository;

import com.evandrorenan.web3270datasysdump.domain.model.AbendReport;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface AbendReportRepository extends MongoRepository<AbendReport, String> {}
