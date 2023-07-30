package com.evandrorenan.web3270datasysdump.domain.model;

import lombok.Builder;

@Builder
public record BaseLocator(
    String _id,
    String address,
    String hexContent
) {}
