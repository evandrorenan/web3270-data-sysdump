package com.evandrorenan.web3270datasysdump.domain.usecase.impl;

import com.evandrorenan.web3270datasysdump.domain.model.BaseLocator;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.core.io.ClassPathResource;
import org.springframework.util.StreamUtils;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.TreeMap;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

class ExtractBaseLocatorFromSysdumpUseCaseImplTest {

    public static final String VALID_RAW_INPUT_ARRAY = "./ExtractBaseLocatorFromSysdumpUseCase_ValidSysdumpInputArray.txt";

    ExtractBaseLocatorFromSysdumpUseCaseImpl useCase = new ExtractBaseLocatorFromSysdumpUseCaseImpl();

    @Test
    void whenInputIsValid_extractBaseLocators() throws Exception {
        TreeMap<String, BaseLocator> baseLocators = useCase.run(loadFileToString());

        System.out.println(new ObjectMapper()
                .writerWithDefaultPrettyPrinter()
                .writeValueAsString(baseLocators));

        assertNotNull(baseLocators);
        assertTrue(baseLocators.size() > 0);
        assertTrue(baseLocators.firstEntry().getValue() instanceof BaseLocator);
    }

    private static String loadFileToString() throws IOException {
        ClassPathResource resource = new ClassPathResource(VALID_RAW_INPUT_ARRAY);
        return StreamUtils.copyToString(resource.getInputStream(), StandardCharsets.UTF_8);
    }
}