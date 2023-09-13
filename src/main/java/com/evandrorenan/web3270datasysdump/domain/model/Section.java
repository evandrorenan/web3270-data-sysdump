package com.evandrorenan.web3270datasysdump.domain.model;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class Section {
    private SectionType sectionType;
    private String sectionId;
    private String initialAddress;
    private String sectionLength;
}
