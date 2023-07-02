package com.evandrorenan.web3270datasysdump.domain.model;

import lombok.Builder;
import org.springframework.stereotype.Component;

@Component
@Builder
public record BaseLocator(
    String address,
    String hexContent
) {}
