package com.evandrorenan.web3270datasysdump.presentation;

import com.evandrorenan.web3270datasysdump.domain.model.BaseLocator;
import com.evandrorenan.web3270datasysdump.domain.usecase.SaveBaseLocatorUseCase;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Slf4j
@Controller
public class SaveController {

    private SaveBaseLocatorUseCase saveUseCase;

    @Autowired
    public SaveController(SaveBaseLocatorUseCase saveUseCase) {
        this.saveUseCase = saveUseCase;
    }

    @PostMapping("/base-locators/raw-input")
    public ResponseEntity<List<BaseLocator>> saveBaseLocatorFromRawInput(@RequestBody String rawInput) throws Exception {
        log.info("Receiving request to saveBaseLocator.");
        return ResponseEntity.ok().body(saveUseCase.run(rawInput));
    }

    @PostMapping("/base-locators/from-blob/{blob-id}")
    public ResponseEntity<List<BaseLocator>> saveBaseLocatorFromBlob(@RequestParam("blob-id") String blobId) throws Exception {
        //FIXME: Implement base-locators extraction from blob file
        log.info("Extract base-locators from blob isn't implemented yet.");
        return ResponseEntity.status(HttpStatus.NOT_IMPLEMENTED).build();
    }
}