package com.saga.orchestration.bank;

import com.saga.message.AmountCredit;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class HanaBankService {

    private int balance = 50000;

    public boolean creditAmount(final int amount) {
        if (canCredit(amount)) {
            log.info("[creditAmount] amount credit, current balance = {}, credit amount = {}"
                    , balance, amount);
            balance += amount;
            return true;
        } else {
            log.error("[creditAmount] credit failed");
            return false;
        }
    }

    private boolean canCredit(int amount) {
        if (balance + amount < 100000) {
            log.info("can credit = {}", amount);
            return true;
        }
        log.error("over credit limit, current = {}, amount = {}", balance, amount);
        return false;
    }
}
