package com.evandrorenan.web3270datasysdump.repository.util;

import lombok.Builder;

@Builder
public record BlobChunckHolder(
    String chunckData,
    String leftOverData
) {}
