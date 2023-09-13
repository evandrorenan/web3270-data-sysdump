package com.evandrorenan.web3270datasysdump.domain.model;

import lombok.Builder;
import lombok.Data;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;
import java.util.List;


@Data
@Builder
@Document(collection = "abendReports")
public class AbendReport {
    private String name;
    private String jobId;
    private LocalDateTime dateTime;
    private List<Program> programs;
    private List<BaseLocator> baseLocators;
}