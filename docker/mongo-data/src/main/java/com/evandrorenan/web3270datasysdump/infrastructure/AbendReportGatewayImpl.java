package com.evandrorenan.web3270datasysdump.infrastructure;

import com.evandrorenan.web3270datasysdump.domain.gateway.AbendReportGateway;
import com.evandrorenan.web3270datasysdump.domain.model.AbendReport;
import com.evandrorenan.web3270datasysdump.repository.AbendReportRepository;
import com.evandrorenan.web3270datasysdump.repository.BlobRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class AbendReportGatewayImpl implements AbendReportGateway {

    private final AbendReportRepository abendReportRepository;

    @Autowired
    public AbendReportGatewayImpl(AbendReportRepository abendReportRepository) {
        this.abendReportRepository = abendReportRepository;
    }

    @Override
    public AbendReport save(AbendReport abendReport) {
        return this.abendReportRepository.save(abendReport);
    }
}