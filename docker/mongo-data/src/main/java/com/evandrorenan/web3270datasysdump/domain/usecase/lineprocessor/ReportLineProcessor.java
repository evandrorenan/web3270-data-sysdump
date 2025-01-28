package com.evandrorenan.web3270datasysdump.domain.usecase.lineprocessor;

import com.evandrorenan.web3270datasysdump.domain.model.AbendReport;

import java.math.BigDecimal;
import java.util.Map;
import java.util.function.Consumer;

public interface ReportLineProcessor {

    BigDecimal priority();

    void process(String line, AbendReport abendReport);

    //Map<String, String> process(String line);
}
