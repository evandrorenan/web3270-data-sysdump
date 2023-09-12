package com.evandrorenan.web3270datasysdump.domain.usecase.lineprocessor;

import com.evandrorenan.web3270datasysdump.domain.model.AbendReport;

import java.math.BigDecimal;

public interface ReportLineProcessor {

    BigDecimal priority();

    AbendReport process(String line, AbendReport abendReport);
}
