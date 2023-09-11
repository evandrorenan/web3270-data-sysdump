package com.evandrorenan.web3270datasysdump.domain.model;

import lombok.Builder;

import java.util.List;

@Builder
public record Section (
    SectionType sectionType,
    List<BaseLocator> baseLocators
) {}
