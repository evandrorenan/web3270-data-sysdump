package com.evandrorenan.web3270datasysdump.domain.usecase.lineprocessor.impl;

import com.evandrorenan.web3270datasysdump.domain.model.AbendReport;
import com.evandrorenan.web3270datasysdump.domain.model.Program;
import com.evandrorenan.web3270datasysdump.domain.usecase.lineprocessor.ReportLineProcessor;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Component
public class StorageSectionLineProcessor implements ReportLineProcessor {

    private static final String PROGRAM_NAME = "programname";
    private static final Pattern PATTERN = Pattern.compile(
            "Program\\sand\\sEntry\\sPoint\\sName:\\s*?(?<" + PROGRAM_NAME + ">\\S{1,8})");

    @Override
    public BigDecimal priority() {
        return BigDecimal.valueOf(1);
    }

    @Override
    public AbendReport process(String line, AbendReport abendReport) {
        Matcher matcher = PATTERN.matcher(line);
        if (abendReport == null || !matcher.matches()) return abendReport;

        abendReport.getPrograms().add(
                Program.builder()
                        .name(matcher.group(PROGRAM_NAME))
                        .sections(new ArrayList<>())
                        .build());

        return abendReport;
    }
}
