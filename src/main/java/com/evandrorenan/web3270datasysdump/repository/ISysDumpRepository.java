package com.evandrorenan.web3270datasysdump.repository;

import com.evandrorenan.web3270datasysdump.domain.model.BaseLocator;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import java.util.List;

public interface ISysDumpRepository extends MongoRepository<BaseLocator, String> {
    @Query(value = "{ address: { $lte: ?0 } }", sort = "{ address: -1 }")
    List<BaseLocator> findByAddressKeyLessThanEqualWithLimit(String address, Pageable pageable);
}
