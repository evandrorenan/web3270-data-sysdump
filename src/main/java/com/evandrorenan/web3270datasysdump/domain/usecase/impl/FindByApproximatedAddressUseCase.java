package com.evandrorenan.web3270datasysdump.domain.usecase.impl;

import com.evandrorenan.web3270datasysdump.domain.model.BaseLocator;
import com.evandrorenan.web3270datasysdump.domain.model.SysdumpArea;
import com.evandrorenan.web3270datasysdump.domain.usecase.IBuildHexContentUseCase;
import com.evandrorenan.web3270datasysdump.domain.usecase.IFindByApproximatedAddressUseCase;
import com.evandrorenan.web3270datasysdump.domain.usecase.InvalidHexContentException;
import com.evandrorenan.web3270datasysdump.repository.ISysDumpRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

import java.math.BigInteger;
import java.util.List;

@Component
public class FindByApproximatedAddressUseCase implements IFindByApproximatedAddressUseCase {

    public static final int HEX_RADIX = 16;
    public static final int QT_CHARS_IN_A_ROW = 16;
    public static final int START_PAGE = 0;
    private final ISysDumpRepository repository;
    private final IBuildHexContentUseCase buildHexContentUseCase;

    @Autowired
    public FindByApproximatedAddressUseCase(ISysDumpRepository repository, IBuildHexContentUseCase useCase) {
        this.repository = repository;
        this.buildHexContentUseCase = useCase;
    }

    @Override
    public SysdumpArea run(String address, String offset)
            throws InvalidHexContentException, BaseLocatorAddressNotFound {

        if (offset == null) return null;

        List<BaseLocator> baseLocatorList = repository.findByAddressKeyLessThanEqualWithLimit(
                calculateEndAddress(address, offset),
                setPaginationOptions(offset));

        return buildHexContentUseCase.run(baseLocatorList, address, offset);
    }

    private static Pageable setPaginationOptions(String offset) {
        int requiredPages = (convertToInt(offset) / QT_CHARS_IN_A_ROW) + 1;
        return PageRequest.of(START_PAGE, requiredPages);
    }

    private String calculateEndAddress(String address, String offset) {

        BigInteger biOffset = new BigInteger(offset, HEX_RADIX);
        BigInteger endAddress = new BigInteger(address, HEX_RADIX).add(biOffset);

        return endAddress.toString(HEX_RADIX).toUpperCase();
    }

    private static int convertToInt(String offset) {
        return new BigInteger(offset, HEX_RADIX).intValue();
    }
}
