package com.evandrorenan.web3270datasysdump.domain.usecase.lineprocessor.impl;

import com.evandrorenan.web3270datasysdump.domain.model.AbendReport;
import com.evandrorenan.web3270datasysdump.domain.model.BaseLocator;
import com.evandrorenan.web3270datasysdump.domain.usecase.lineprocessor.ReportLineProcessor;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Component
public class BaseLocatorLineProcessor implements ReportLineProcessor {

    public static final String ADDRESS = "address";
    public static final String BASE_LOCATOR = "baselocator";

    public static final Pattern PATTERN =
            Pattern.compile("^(?<" + ADDRESS + ">[0-9A-F]{8}).+?" +
                    "(?<" + BASE_LOCATOR + ">[0-9A-F]{8}\\s[0-9A-F]{8}\\s[0-9A-F]{8}\\s[0-9A-F]{8})");

    @Override
    public BigDecimal priority() {
        return BigDecimal.valueOf(3);
    }

    @Override
    public AbendReport process(String line, AbendReport abendReport) {
        Matcher matcher = PATTERN.matcher(line);
        if (abendReport == null || !matcher.matches()) return abendReport;

        abendReport.getBaseLocators().add(
            BaseLocator.builder()
                .address(matcher.group(ADDRESS))
                .hexContent(matcher.group(BASE_LOCATOR))
                .build()
        );

        return abendReport;
    }
}
