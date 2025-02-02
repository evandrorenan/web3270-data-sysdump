package com.evandrorenan.web3270datasysdump.domain.model;

import lombok.extern.slf4j.Slf4j;

import java.util.Arrays;
import java.util.Optional;

/**
 * Enumeration of different section types found in a 3270 terminal system dump.
 * Each type represents a specific category of information that can be found
 * in the system dump.
 *
 * @author Evandro Renan
 * @version 0.0.1-SNAPSHOT
 */
@Slf4j
public enum SectionType {
    /**
     * Working storage section.
     */
    WORKING_STORAGE("W"),
    /**
     * Linkage section.
     */
    LINKAGE("L"),
    /**
     * File section.
     */
    FILE("F");

    private final String sectionLetter;

    SectionType(String sectionLetter) {
        this.sectionLetter = sectionLetter;
    }

    public static SectionType getEnumFromLetter(String letter) {
        Optional<SectionType> optLetter = Arrays.stream(SectionType.values()).filter(letter::equals).findFirst();
        if (optLetter.isPresent()) return optLetter.get();

        log.warn("Letter {} doesn't matches any enum. Default 'W' was setted.", letter);
        return WORKING_STORAGE;
    }
}
