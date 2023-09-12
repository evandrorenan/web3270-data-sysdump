package com.evandrorenan.web3270datasysdump.domain.usecase.impl;

import com.evandrorenan.web3270datasysdump.domain.usecase.annotation.LineProcessor;
import org.springframework.stereotype.Component;

@Component
@LineProcessor(regex = "")
public class AbendReportHeaderLineProcessor {
}
