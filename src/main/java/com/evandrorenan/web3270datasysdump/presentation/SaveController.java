package com.evandrorenan.web3270datasysdump.presentation;

import com.evandrorenan.web3270datasysdump.domain.model.BaseLocator;
import com.evandrorenan.web3270datasysdump.domain.usecase.ISaveBaseLocatorUseCase;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

@Slf4j
@Controller
public class SaveController {

    private ISaveBaseLocatorUseCase saveUseCase;

    @Autowired
    public SaveController(ISaveBaseLocatorUseCase saveUseCase) {
        this.saveUseCase = saveUseCase;
    }

    @PostMapping("/base-locators/raw-input")
    public ResponseEntity<List<BaseLocator>> saveBaseLocatorUseCase(@RequestBody String rawInput) throws Exception {
        log.info("Receiving request to saveBaseLocator.");
        return ResponseEntity.ok().body(saveUseCase.run(rawInput));
    }
}