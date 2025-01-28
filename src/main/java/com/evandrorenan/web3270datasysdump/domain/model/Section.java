package com.evandrorenan.web3270datasysdump.domain.model;

import lombok.Builder;
import lombok.Data;

/**
 * Represents a section of data within a system dump.
 * Each section contains specific information about the program state
 * at the time of the abnormal termination (Abend).
 *
 * @author Evandro Renan
 * @version 0.0.1-SNAPSHOT
 */
@Data
@Builder
public class Section {

    /**
     * The type of this section, indicating what kind of information it contains.
     */
    private SectionType sectionType;

    /**
     * The raw content of this section from the system dump.
     */
    private String sectionId;

    /**
     * The starting line number of this section in the original dump file.
     */
    private String initialAddress;

    /**
     * The ending line number of this section in the original dump file.
     */
    private String sectionLength;
}
