package com.evandrorenan.web3270datasysdump.domain.usecase.impl;

import com.evandrorenan.web3270datasysdump.domain.model.BaseLocator;
import com.evandrorenan.web3270datasysdump.domain.usecase.IExtractBaseLocatorFromSysdumpUseCase;

import java.util.List;
import java.util.TreeMap;
import java.util.function.Function;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import static com.evandrorenan.web3270datasysdump.domain.usecase.impl.BaseLocatorField.ADDRESS;
import static com.evandrorenan.web3270datasysdump.domain.usecase.impl.BaseLocatorField.HEX_CONTENT;

public class ExtractBaseLocatorFromSysdumpUseCase implements IExtractBaseLocatorFromSysdumpUseCase {

    public static final Pattern REGEX_PATTERN_VALID_BASE_LOCATOR =
            Pattern.compile("^[0-9A-F]{8}.+?[0-9A-F]{8}\\s[0-9A-F]{8}\\s[0-9A-F]{8}\\s[0-9A-F]{8}");

    private boolean containsValidBaseLocator(String s) {
        return REGEX_PATTERN_VALID_BASE_LOCATOR.matcher(s).find();
    }

    @Override
    public TreeMap<String, BaseLocator> run(String rawInput) {
        return convertToTreeMap(rawInput);
    }

    private TreeMap<String, BaseLocator> convertToTreeMap(String rawInput) {
        List<String> inputArray = List.of(rawInput.split("\n"));

        return inputArray.stream()
                         .filter(this::containsValidBaseLocator)
                         .map(this::buildBaseLocator)
                         .collect(Collectors
                                 .toMap(BaseLocator::address, Function.identity(), this::mergeFunction, TreeMap::new));
    }

    private <T> T mergeFunction(T a, T b) {
        return a;
    }

    private BaseLocator buildBaseLocator(String rawInput) {
        rawInput = rawInput.trim();
        String address = rawInput.substring(ADDRESS.getBeginIndex(), ADDRESS.getEndIndex());
        String workAreaHex = rawInput.substring(HEX_CONTENT.getBeginIndex(), HEX_CONTENT.getEndIndex());

        return BaseLocator.builder()
                          .address(address)
                          .hexContent(workAreaHex)
                          .build();
    }
}