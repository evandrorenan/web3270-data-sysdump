package com.evandrorenan.web3270datasysdump.domain.usecase.lineprocessor.impl;

import com.evandrorenan.web3270datasysdump.domain.model.AbendReport;
import com.evandrorenan.web3270datasysdump.domain.usecase.lineprocessor.ReportLineProcessor;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Component
public class AbendReportHeaderLineProcessor implements ReportLineProcessor {

    private static final String JOB_NAME = "jobname";
    private static final String ABEND_CODE = "abendcode";
    private static final String DATE = "date";
    private static final String TIME = "time";
    private static final Pattern PATTERN = Pattern.compile(
    "^JOBNAME:\\s*?(?<" +
            JOB_NAME + ">[a-zA-Z0-9#$]{1,8})\\s*?.*?ABEND:\\s*?(?<" +
            ABEND_CODE + ">[a-zA-Z0-9#$]{1,8}).*?(?<" +
            DATE +">\\d{4}\\/\\d{2}\\/\\d{2})\\s*?(?<" +
            TIME + ">\\d{2}:\\d{2}:\\d{2})\\s*?");

    @Override
    public BigDecimal priority() {
        return BigDecimal.valueOf(1);
    }

  //  @Override
    public Map<String, String> process(String line) {
        Matcher matcher = PATTERN.matcher(line);

        Map<String, String> map = new HashMap<>();
        while (matcher.find()) {
            for (int i = 1; i <= matcher.groupCount(); i++) {
                //map.put(PATTERN.)
            }
        }
        return null;
    }

    @Override
    public void process(String line, AbendReport abendReport) {
        Matcher matcher = PATTERN.matcher(line);
        if (abendReport == null || !matcher.find()) return;

        abendReport.setName(matcher.group(JOB_NAME));
        abendReport.setJobId(matcher.group(ABEND_CODE));
        abendReport.setDateTime(buildLocalDateTime(matcher));
    }

    private static LocalDateTime buildLocalDateTime(Matcher matcher) {
        String date = matcher.group(DATE);
        String time = matcher.group(TIME);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");
        return LocalDateTime.parse(date + " " + time, formatter);
    }
}
