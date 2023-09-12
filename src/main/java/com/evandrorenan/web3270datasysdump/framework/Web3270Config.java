package com.evandrorenan.web3270datasysdump.framework;

import com.evandrorenan.web3270datasysdump.domain.usecase.ReportLineProcessor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.GenericApplicationContext;

import java.util.List;

@Configuration
public class Web3270Config {

    private final GenericApplicationContext context;

    @Autowired
    public Web3270Config(GenericApplicationContext context) {
        this.context = context;
    }

    @Bean
    public List<ReportLineProcessor> buildLineProcessorsBeans() {
        return List.copyOf(
                context.getBeanFactory().getBeansOfType(ReportLineProcessor.class).values());
    }
}
