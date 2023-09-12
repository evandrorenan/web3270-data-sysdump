package com.evandrorenan.web3270datasysdump.framework;

import com.evandrorenan.web3270datasysdump.domain.usecase.lineprocessor.ReportLineProcessor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.GenericApplicationContext;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Configuration
public class Web3270Config {

    private final GenericApplicationContext context;

    @Autowired
    public Web3270Config(GenericApplicationContext context) {
        this.context = context;
    }

    @Bean
    public List<ReportLineProcessor> buildLineProcessorsBeans() {
        return context.getBeanFactory().getBeansOfType(ReportLineProcessor.class)
                .values()
                .stream().sorted(Comparator.comparing(ReportLineProcessor::priority))
                .collect(Collectors.toList());
    }
}
