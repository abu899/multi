package com.saga.choreography.bank;

import com.saga.message.AmountCredit;
import com.saga.message.AmountDeduct;
import com.saga.message.CreditFailed;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Slf4j
@Service
public class KBBankService {

    private int balance = 50000;

    private final ApplicationEventPublisher eventPublisher;


    @EventListener
    public void creditAmount(final AmountDeduct amountDeduct) {
        int amount = amountDeduct.amount();
        if (canCredit(amount)) {
            log.info("[creditAmount] amount credit publish event, current balance = {}, credit amount = {}"
                    , balance, amount);
            eventPublisher.publishEvent(new AmountCredit(amount));
        } else {
            log.error("[creditAmount] credit failed publish event");
            eventPublisher.publishEvent(new CreditFailed(amount));
        }
    }

    @EventListener
    public void amountCredit(final AmountCredit amountCredit) {
        int amount = amountCredit.amount();
        balance += amount;
        log.info("[amountCredit] amount credit, current balance = {}", balance);
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
