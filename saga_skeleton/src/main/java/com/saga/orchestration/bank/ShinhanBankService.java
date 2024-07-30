package com.saga.orchestration.bank;

import com.saga.message.TransferFailed;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class ShinhanBankService {
    private int balance = 100000;

    public boolean deductAmount(int amount) {
        if (canDeduct(amount)) {
            log.info("[deductAmount] deduct amount publish event, balance = {} , amount = {}",
                    balance, amount);
            balance -= amount;
            log.info("[deductAmount] deduct amount, final balance = {}", balance);
            return true;
        } else {
            log.error("[deductAmount] Transfer failed");
            return false;
        }
    }

    public void revertDeduction(int amount) {
        log.info("[revertDeduction] revert deduction, current balance = {}, credit failed amount = {}"
                ,balance, amount);
        // 보상 로직
        balance += amount;
        log.info("[revertDeduction] revert deduction, current balance = {}", balance);
    }

    public void revertTransfer(final TransferFailed transferFailed) {
        log.info("[revertTransfer] revert transfer, current balance = {}, failed amount = {}"
                , balance, transferFailed.amount());
    }

    private boolean canDeduct(int amount) {
        if (balance - amount > 0) {
            log.info("can deduct = {}", amount);
            return true;
        }
        log.error("not enough balance to deduct, current = {}, amount = {}", balance, amount);
        return false;
    }
}
