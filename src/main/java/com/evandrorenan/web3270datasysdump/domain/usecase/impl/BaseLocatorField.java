package com.evandrorenan.web3270datasysdump.domain.usecase.impl;

import lombok.Getter;

@Getter
public enum BaseLocatorField {
    ADDRESS(0, 8),
    HEX_CONTENT(20, 55);

    private final int beginIndex;
    private final int endIndex;

    BaseLocatorField(int beginIndex, int endIndex) {
        this.beginIndex = beginIndex;
        this.endIndex = endIndex;
    }
}
