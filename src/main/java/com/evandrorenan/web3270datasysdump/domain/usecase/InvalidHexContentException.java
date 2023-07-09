package com.evandrorenan.web3270datasysdump.domain.usecase;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class InvalidHexContentException extends Exception {
    public InvalidHexContentException(String message) {
        log.error(message);
    }
}
