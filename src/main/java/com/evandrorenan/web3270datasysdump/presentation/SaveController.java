package com.evandrorenan.web3270datasysdump.presentation;

import com.evandrorenan.web3270datasysdump.domain.model.BaseLocator;
import com.evandrorenan.web3270datasysdump.domain.usecase.SaveBaseLocatorFromBlobUseCase;
import com.evandrorenan.web3270datasysdump.domain.usecase.SaveBaseLocatorUseCase;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Slf4j
@Controller
public class SaveController {

    private final SaveBaseLocatorUseCase saveUseCase;

    private final SaveBaseLocatorFromBlobUseCase saveFromBlobUseCase;

    @Autowired
    public SaveController(SaveBaseLocatorUseCase saveUseCase, SaveBaseLocatorFromBlobUseCase saveFromBlobUseCase) {
        this.saveUseCase = saveUseCase;
        this.saveFromBlobUseCase = saveFromBlobUseCase;
    }

    @PostMapping("/base-locators/raw-input")
    public ResponseEntity<List<BaseLocator>> saveBaseLocatorFromRawInput(@RequestBody String rawInput) throws Exception {
        log.info("Receiving request to saveBaseLocator.");
        return ResponseEntity.ok().body(saveUseCase.run(rawInput));
    }

    @PostMapping("/base-locators/from-blob")
    public ResponseEntity<List<BaseLocator>> saveBaseLocatorFromBlob(@RequestParam("blob-id") String blobId) throws Exception {
        log.info("Extract base-locators from blob isn't implemented yet.");
        saveFromBlobUseCase.run(blobId);
        return ResponseEntity.ok().build();
    }
}