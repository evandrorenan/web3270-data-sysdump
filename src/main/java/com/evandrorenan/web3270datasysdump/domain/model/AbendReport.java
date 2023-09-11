package com.evandrorenan.web3270datasysdump.domain.model;

import lombok.Builder;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;
import java.util.List;

@Builder
@Document(collection = "abendReports")
public record AbendReport (
    String name,
    String jobId,
    LocalDateTime dateTime,
    List<Program> programs
){}
