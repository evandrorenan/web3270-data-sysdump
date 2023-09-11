package com.evandrorenan.web3270datasysdump.repository.util;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class  BlobChunckHolder {
    private final String chunckData;
    private final String leftOverData;
}
