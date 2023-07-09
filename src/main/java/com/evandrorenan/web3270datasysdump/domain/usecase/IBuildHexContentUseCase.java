package com.evandrorenan.web3270datasysdump.domain.usecase;

import com.evandrorenan.web3270datasysdump.domain.model.BaseLocator;
import com.evandrorenan.web3270datasysdump.domain.model.SysdumpArea;
import com.evandrorenan.web3270datasysdump.domain.usecase.impl.BaseLocatorAddressNotFound;

import java.util.List;
import java.util.TreeMap;

public interface IBuildHexContentUseCase {

    SysdumpArea run(List<BaseLocator> baseLocators, String address, String offset)
            throws InvalidHexContentException, BaseLocatorAddressNotFound;
}
