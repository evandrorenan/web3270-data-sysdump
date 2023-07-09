package com.evandrorenan.web3270datasysdump.presentation;

import com.evandrorenan.web3270datasysdump.domain.model.SysdumpArea;
import com.evandrorenan.web3270datasysdump.domain.usecase.IFindByApproximatedAddressUseCase;
import com.evandrorenan.web3270datasysdump.domain.usecase.InvalidHexContentException;
import com.evandrorenan.web3270datasysdump.domain.usecase.impl.BaseLocatorAddressNotFound;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class FindByApproximatedAddressController {

    private final IFindByApproximatedAddressUseCase useCase;

    @Autowired
    public FindByApproximatedAddressController(IFindByApproximatedAddressUseCase useCase) {
        this.useCase = useCase;
    }

    @GetMapping("/base-locators/by-address/{address}/{offset}")
    public SysdumpArea baseLocatorByAddress(@PathVariable String address, @PathVariable String offset)
            throws InvalidHexContentException, BaseLocatorAddressNotFound {

        return useCase.run(address, offset);
    }
}
