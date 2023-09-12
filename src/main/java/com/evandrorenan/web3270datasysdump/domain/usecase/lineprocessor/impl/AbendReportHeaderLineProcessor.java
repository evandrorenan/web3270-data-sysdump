package com.evandrorenan.web3270datasysdump.domain.usecase.lineprocessor.impl;

import com.evandrorenan.web3270datasysdump.domain.model.AbendReport;
import com.evandrorenan.web3270datasysdump.domain.usecase.lineprocessor.ReportLineProcessor;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Component
public class AbendReportHeaderLineProcessor implements ReportLineProcessor {

    private static final String JOB_NAME = "jobname";
    private static final String ABEND_CODE = "abendcode";
    private static final String DATE = "date";
    private static final String TIME = "time";
    private static final Pattern PATTERN = Pattern.compile(
            "JOBNAME:\\s*?(?<" + JOB_NAME + ">[A-Za-z0-9$@#]{1,8}).*?" +
                  "ABEND:\\s*?(?<" + ABEND_CODE + ">[A-Za-z0-9$@#]{1,8}).*?" +
                  "(?<" + DATE + ">\\d{4}\\/\\d{2}\\/\\d{2})\\s*?" +
                  "(?<" + TIME + ">\\d{2}:\\d{2}:\\d{2})");

    @Override
    public BigDecimal priority() {
        return BigDecimal.valueOf(1);
    }

    @Override
    public AbendReport process(String line, AbendReport abendReport) {
        Matcher matcher = PATTERN.matcher(line);
        if (!matcher.matches()) return null;

        return AbendReport.builder()
                .name(matcher.group(JOB_NAME))
                .dateTime(buildLocalDateTime(matcher))
                .build();

    }

    private static LocalDateTime buildLocalDateTime(Matcher matcher) {
        String date = matcher.group(DATE);
        String time = matcher.group(TIME);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");
        return LocalDateTime.parse(date + " " + time, formatter);
    }
}
