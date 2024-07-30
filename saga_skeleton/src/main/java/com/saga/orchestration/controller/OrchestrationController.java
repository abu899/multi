package com.saga.orchestration.controller;

import com.saga.choreography.bank.KakaoBankService;
import com.saga.choreography.dto.Deduct;
import com.saga.orchestration.orchestrator.BankingOrchestrator;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/orchestration")
public class OrchestrationController {
    private final BankingOrchestrator orchestrator;

    @PostMapping("/deduct")
    public void deduct(@RequestBody final Deduct deduct) {
        orchestrator.transfer(deduct.amount());
    }
}
