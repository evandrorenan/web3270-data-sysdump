package com.evandrorenan.web3270datasysdump.presentation;

import com.evandrorenan.web3270datasysdump.domain.model.BaseLocator;
import com.evandrorenan.web3270datasysdump.domain.usecase.ISaveBaseLocatorUseCase;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

@Controller
public class SaveController {

    private ISaveBaseLocatorUseCase saveUseCase;

    @Autowired
    public SaveController(ISaveBaseLocatorUseCase saveUseCase) {
        this.saveUseCase = saveUseCase;
    }

    @PostMapping("/base-locators/raw-input")
    public ResponseEntity<List<BaseLocator>> saveBaseLocatorUseCase(@RequestBody String rawInput) throws Exception {
        return ResponseEntity.ok().body(saveUseCase.run(rawInput));
    }
}