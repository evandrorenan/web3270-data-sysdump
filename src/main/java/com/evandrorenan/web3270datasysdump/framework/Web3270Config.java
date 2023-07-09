package com.evandrorenan.web3270datasysdump.framework;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.util.StreamUtils;

import java.io.IOException;
import java.math.BigInteger;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Stream;

@Configuration
public class Web3270Config {

    private static final int HEX_BASED = 16;

    @Bean
    public Map<BigInteger, Character> loadFileToMap() throws IOException {
        ClassPathResource resource = new ClassPathResource("./static/HexToEbcdicTable.txt");
        String fileContent = StreamUtils.copyToString(resource.getInputStream(), StandardCharsets.UTF_8);

        return Stream.of(fileContent.split("\\r?\\n"))
                     .filter(line -> line.matches("^[0-9A-F]{2},.$"))
                     .collect(HashMap::new,
                             (map, line) -> map.put(new BigInteger(line.substring(0, 2), HEX_BASED), line.charAt(3)),
                             HashMap::putAll);
    }
}
