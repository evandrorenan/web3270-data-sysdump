package com.evandrorenan.web3270datasysdump.domain.usecase;

import com.evandrorenan.web3270datasysdump.domain.model.SysdumpArea;
import com.evandrorenan.web3270datasysdump.domain.usecase.impl.BaseLocatorAddressNotFound;

public interface IFindByApproximatedAddressUseCase {

    SysdumpArea run(String address, String offset) throws InvalidHexContentException, BaseLocatorAddressNotFound;
}
