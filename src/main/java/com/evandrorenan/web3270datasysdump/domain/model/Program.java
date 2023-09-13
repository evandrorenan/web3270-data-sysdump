package com.evandrorenan.web3270datasysdump.domain.model;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Builder
@Data
public class Program {
    private String name;
    private List<Section> sections;
}
