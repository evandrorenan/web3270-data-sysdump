package com.evandrorenan.web3270datasysdump.domain.model;

import lombok.extern.slf4j.Slf4j;

import java.util.Arrays;
import java.util.Optional;

@Slf4j
public enum SectionType {
    WORKING_STORAGE("W"),
    LINKAGE("L"),
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
