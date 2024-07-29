package com.saga.choreography.controller;

import com.saga.choreography.bank.KBBankService;
import com.saga.choreography.bank.KakaoBankService;
import com.saga.choreography.dto.Deduct;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class ChoreographyController {
    private final KakaoBankService kakaoBankService;

    @PostMapping("/deduct")
    public void deduct(@RequestBody final Deduct deduct) {
        kakaoBankService.deductAmount(deduct.amount());
    }
}
