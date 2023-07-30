package com.evandrorenan.web3270datasysdump.presentation;

import com.evandrorenan.web3270datasysdump.domain.model.BaseLocator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;
import java.util.UUID;

@Controller
public class SaveController {

    @Autowired
    public SaveController() {

    }

    @PostMapping("/base-locators/raw-input")
    public ResponseEntity<List<BaseLocator>> saveBaseLocatorUseCase(@RequestBody String rawInput) throws Exception {
        return ResponseEntity.ok().body(List.of(BaseLocator.builder()
                                                    .address("Address")
                                                    .hexContent("HexContent")
                                                    ._id(UUID.randomUUID().toString())
                                                    .build()));
        //return ResponseEntity.ok().body(saveUseCase.run(rawInput));
    }
}