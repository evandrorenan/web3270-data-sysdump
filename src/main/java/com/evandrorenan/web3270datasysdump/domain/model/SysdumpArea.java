package com.evandrorenan.web3270datasysdump.domain.model;

import lombok.Builder;

@Builder
public record SysdumpArea(
        String hexContent,
        String ebcdicContent
) {
}
