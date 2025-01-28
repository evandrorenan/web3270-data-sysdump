package com.evandrorenan.web3270datasysdump.domain.usecase.lineprocessor.impl;

import com.evandrorenan.web3270datasysdump.domain.model.AbendReport;
import com.evandrorenan.web3270datasysdump.domain.model.Program;
import com.evandrorenan.web3270datasysdump.domain.model.Section;
import com.evandrorenan.web3270datasysdump.domain.model.SectionType;
import com.evandrorenan.web3270datasysdump.domain.usecase.lineprocessor.ReportLineProcessor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Component
@Slf4j
public class ProgramDefinitionLineProcessor implements ReportLineProcessor {

    private static final String SECTION_TYPE = "sectiontype";
    private static final String SECTION_ID = "sectionid";
    private static final String INITIAL_ADDRESS = "initialaddress";
    private static final String SECTION_LENGTH = "sectionlength";

    private static final Pattern PATTERN = Pattern.compile(
            "BL(?<" + SECTION_TYPE + ">[FWL])=" +
                    "(?<" + SECTION_ID + ">[0-9A-F]{4})\\sat\\saddress\\s" +
                    "(?<" + INITIAL_ADDRESS + ">[0-9A-F]{8})\\s*?for length\\s" +
                    "(?<" + SECTION_LENGTH + ">[0-9A-F]{8})");

    @Override
    public BigDecimal priority() {
        return BigDecimal.valueOf(1);
    }

    @Override
    public void process(String line, AbendReport abendReport) {
        Matcher matcher = PATTERN.matcher(line);
        if (abendReport == null || !matcher.find()) return;

        List<Program> programs = abendReport.getPrograms();
        if (programs.isEmpty()) {
            log.warn("Section declaration found before any program definition: {}", line);
            return;
        }

        lastProgramSections(programs).add(
            Section.builder()
                .sectionType(SectionType.getEnumFromLetter(matcher.group(SECTION_TYPE)))
                .sectionId(matcher.group(SECTION_ID))
                .initialAddress(matcher.group(INITIAL_ADDRESS))
                .sectionLength(matcher.group(SECTION_LENGTH))
                .build());
    }

    private static List<Section> lastProgramSections(List<Program> programs) {
        return programs.get(programs.size() - 1).getSections();
    }
}
