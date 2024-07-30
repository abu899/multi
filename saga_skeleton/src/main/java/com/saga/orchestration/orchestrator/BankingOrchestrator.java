package com.saga.orchestration.orchestrator;

import com.saga.orchestration.bank.HanaBankService;
import com.saga.orchestration.bank.ShinhanBankService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class BankingOrchestrator {

    private final ShinhanBankService shinhanBankService;
    private final HanaBankService hanaBankService;

    public void transfer(int amount) {
        if(shinhanBankService.deductAmount(amount)) {
            if(!hanaBankService.creditAmount(amount)) {
                shinhanBankService.revertDeduction(amount);
            }
        }
    }
}
