package com.evandrorenan.web3270datasysdump.domain.model;

import lombok.Builder;
import lombok.Data;

/**
 * Represents a program in the 3270 terminal system.
 * This class contains information about a specific program that was running
 * when an abnormal termination (Abend) occurred.
 *
 * @author Evandro Renan
 * @version 0.0.1-SNAPSHOT
 */
@Data
@Builder
public class Program {

    /**
     * The name of the program.
     */
    private String name;

    /**
     * The type or language of the program (e.g., COBOL, Assembler).
     */
    private String type;

    /**
     * The program's compilation date.
     */
    private String compilationDate;

    /**
     * The version or release of the compiler used.
     */
    private String compilerVersion;
}
