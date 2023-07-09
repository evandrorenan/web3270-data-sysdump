package com.evandrorenan.web3270datasysdump.domain.usecase.impl;

import com.evandrorenan.web3270datasysdump.domain.model.BaseLocator;
import com.evandrorenan.web3270datasysdump.domain.model.SysdumpArea;
import com.evandrorenan.web3270datasysdump.domain.usecase.IBuildHexContentUseCase;
import com.evandrorenan.web3270datasysdump.domain.usecase.InvalidHexContentException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.math.BigInteger;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Component
public class BuildHexContentUseCase implements IBuildHexContentUseCase {

    private static final String EMPTY_STRING = "";
    private static final int HEX_BASED = 16;
    private final Map<BigInteger, Character> stringCharacterMap;

    @Autowired
    public BuildHexContentUseCase(Map<BigInteger, Character> stringCharacterMap) {
        this.stringCharacterMap = stringCharacterMap;
    }

    @Override
    public SysdumpArea run(List<BaseLocator> baseLocators, String address, String offset)
            throws InvalidHexContentException, BaseLocatorAddressNotFound {

        String workAreaHexContent = extractWorkAreaContent(
                baseLocators,
                new BigInteger(address, HEX_BASED),
                new BigInteger(offset, HEX_BASED));

        return SysdumpArea.builder()
                .hexContent(workAreaHexContent)
                .ebcdicContent(convertHexToEBCDIC(workAreaHexContent, stringCharacterMap))
                .build();
    }

    private static TreeMap<BigInteger, BaseLocator> convertIntoTreeMap(List<BaseLocator> baseLocators) {
        return baseLocators
                .stream().collect(Collectors.toMap(
                        bl -> new BigInteger(bl.address(), HEX_BASED),
                        BuildHexContentUseCase::apply,
                        BuildHexContentUseCase::apply,
                        TreeMap::new));
    }

    private static BaseLocator apply(BaseLocator baseLocator) {
        return baseLocator;
    }

    private static BaseLocator apply(BaseLocator o, BaseLocator o2) {
        return o;
    }

    public String convertHexToEBCDIC(String hexString, Map<BigInteger, Character> hexMap)
            throws InvalidHexContentException {

        if ((hexString.length() % 2) != 0 || hexString.length() < 2) {
            throw new InvalidHexContentException(String.format("Invalid hex content: %s", hexString));
        }

        return IntStream.range(0, hexString.length() / 2)
                        .mapToObj(i -> hexString.substring(i * 2, i * 2 + 2))
                        .map(hex -> hexMap.getOrDefault(new BigInteger(hex, HEX_BASED), '.'))
                        .map(Objects::toString)
                        .collect(Collectors.joining());
    }
    
    private String extractWorkAreaContent(
            List<BaseLocator> baseLocators,
            BigInteger address,
            BigInteger offset) throws BaseLocatorAddressNotFound {

        return extractWorkAreaContent(
                convertIntoTreeMap(baseLocators),
                address,
                offset,
                EMPTY_STRING);
    }

    private String extractWorkAreaContent(
            TreeMap<BigInteger, BaseLocator> baseLocators,
            BigInteger address, BigInteger offset, String content) throws BaseLocatorAddressNotFound {

        String hexWord = getHexWordByAddress(baseLocators, address);

        BigInteger wordInitAddress = calcWordInit(address);
        BigInteger wordEndAddress = calcWordEnd(address);
        
        int wordOffset = calcWordOffset(address, offset, wordEndAddress);
        int initString = wordIndexCorrespToAddressInit(address, wordInitAddress) * 2;
        content += hexWord.substring(initString, initString + wordOffset);

        offset = new BigInteger(String.valueOf(offset.intValue() - (wordOffset / 2)));
        if (offset.intValue() > 0) {
            return extractWorkAreaContent(baseLocators, wordEndAddress, offset, content);
        }

        return content;
    }

    private static int wordIndexCorrespToAddressInit(BigInteger address, BigInteger wordInitAddress) {
        return address.intValue() - wordInitAddress.intValue();
    }

    private static int calcWordOffset(BigInteger address, BigInteger offset, BigInteger wordEndAddress) {
        int newOffset = wordIndexCorrespToAddressInit(wordEndAddress, address);
        if (offset.intValue() < newOffset) return offset.intValue() * 2;
        return newOffset * 2;
    }

    private static String getHexWordByAddress(TreeMap<BigInteger, BaseLocator> baseLocators, BigInteger address)
            throws BaseLocatorAddressNotFound {

        //if the exact key is not found, it means there content is equal to previow hexadecimal word (workAreaHex)
        BigInteger floorApproximateKey = baseLocators.floorKey(address);
        if (Objects.isNull(floorApproximateKey)) {
            throw new BaseLocatorAddressNotFound(String.format("Address not found %s: ", address.toString(HEX_BASED)));
        }

        return baseLocators.get(floorApproximateKey).hexContent();
    }

    private BigInteger calcWordEnd(BigInteger address) {
        return calcWordInit(address).add(new BigInteger("10", 16));
    }

    private BigInteger calcWordInit(BigInteger address) {
        String strAddress = address.toString(16);
        return new BigInteger(strAddress.substring(0, strAddress.length() - 1) + "0", 16);
    }
}
