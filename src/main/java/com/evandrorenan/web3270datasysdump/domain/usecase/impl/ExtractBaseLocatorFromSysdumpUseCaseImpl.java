package com.evandrorenan.web3270datasysdump.domain.usecase.impl;

import com.evandrorenan.web3270datasysdump.domain.model.BaseLocator;
import com.evandrorenan.web3270datasysdump.domain.usecase.ExtractBaseLocatorFromSysdumpUseCase;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.TreeMap;
import java.util.function.Function;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import static com.evandrorenan.web3270datasysdump.domain.model.BaseLocatorField.ADDRESS;
import static com.evandrorenan.web3270datasysdump.domain.model.BaseLocatorField.HEX_CONTENT;

/**
 * Implementation of {@link ExtractBaseLocatorFromSysdumpUseCase}.
 * This class is responsible for extracting {@link BaseLocator} objects from a raw input string.
 * The raw input string is expected to be a sysdump file content, and it is expected to contain lines with the following format:
 *
 * <pre>
 * 00000000  00000000  00000000  00000000
 * 00000001  00000001  00000001  00000001
 * 00000002  00000002  00000002  00000002
 * ...
 * </pre>
 *
 * The lines are filtered using the {@link #REGEX_PATTERN_VALID_BASE_LOCATOR} regular expression.
 * The filtered lines are then mapped to {@link BaseLocator} objects using the {@link #buildBaseLocator(String)} method.
 * The resulting list of {@link BaseLocator} objects is then collected into a {@link TreeMap} using the address as the key.
 *
 * @author evandro
 */
@Slf4j
@Component
public class ExtractBaseLocatorFromSysdumpUseCaseImpl implements ExtractBaseLocatorFromSysdumpUseCase {

    /**
     * Regular expression pattern to validate if a line is a valid base locator.
     */
    public static final Pattern REGEX_PATTERN_VALID_BASE_LOCATOR =
            Pattern.compile("^[0-9A-F]{8}.+?[0-9A-F]{8}\\s[0-9A-F]{8}\\s[0-9A-F]{8}\\s[0-9A-F]{8}");

    @Override
    public TreeMap<String, BaseLocator> run(String rawInput) {
        log.info("Starting to run ExtractBaseLocatorFromSysdumpUseCase");
        return convertToTreeMap(rawInput);
    }

    /**
     * Converts the raw input string to a {@link TreeMap} of {@link BaseLocator} objects.
     *
     * @param rawInput the raw input string
     * @return the {@link TreeMap} of {@link BaseLocator} objects
     */
    private TreeMap<String, BaseLocator> convertToTreeMap(String rawInput) {
        List<String> inputArray = List.of(rawInput.split("\n"));

        var inputArrayStream = inputArray.stream()
                         .filter(this::containsValidBaseLocator)
                         .map(this::buildBaseLocator)
                         .collect(Collectors
                                 .toMap(BaseLocator::address, Function.identity(), this::mergeFunction, TreeMap::new));

        log.info("Lines discarded: {}", inputArray.size() - inputArrayStream.size());

        return inputArrayStream;
    }

    /**
     * Checks if a line contains a valid base locator.
     *
     * @param s the line to check
     * @return true if the line contains a valid base locator, false otherwise
     */
    private boolean containsValidBaseLocator(String s) {
        return REGEX_PATTERN_VALID_BASE_LOCATOR.matcher(s).find();
    }

    /**
     * Merges two values of the same type.
     *
     * @param a the first value
     * @param b the second value
     * @return the merged value
     */
    private <T> T mergeFunction(T a, T b) {
        return a;
    }

    /**
     * Builds a {@link BaseLocator} object from a raw input string.
     *
     * @param rawInput the raw input string
     * @return the {@link BaseLocator} object
     */
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