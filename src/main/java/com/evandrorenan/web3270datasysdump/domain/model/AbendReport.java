package com.evandrorenan.web3270datasysdump.domain.model;

import lombok.Builder;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;
import java.util.List;

/**
 * Represents an Abend (Abnormal End) Report from a 3270 terminal system dump.
 * This class serves as both a domain model and a MongoDB document, storing information
 * about system abnormal terminations including program details and base locators.
 *
 * @author Evandro Renan
 * @version 0.0.1-SNAPSHOT
 */
@Data
@Builder
@Document(collection = "abendReports")
public class AbendReport {
    
    /**
     * Unique identifier for the Abend report.
     */
    @Id
    private String id;

    /**
     * The name of the Abend report.
     */
    private String name;

    /**
     * The job ID associated with the Abend report.
     */
    private String jobId;

    /**
     * The date and time the Abend report was generated.
     */
    private LocalDateTime dateTime;

    /**
     * The program that experienced the abnormal termination.
     */
    private Program program;

    /**
     * List of base locators associated with the program at the time of the abend.
     * Base locators provide information about program storage areas.
     */
    private List<BaseLocator> baseLocators;

    /**
     * List of sections containing different parts of the system dump.
     * Each section represents a specific type of information from the dump.
     */
    private List<Section> sections;
}