package com.saga.choreography.bank;

import com.saga.message.AmountDeduct;
import com.saga.message.CreditFailed;
import com.saga.message.TransferFailed;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Slf4j
@Service
public class KakaoBankService {

    private int balance = 100000;

    private final ApplicationEventPublisher eventPublisher;


    public void deductAmount(int amount) {
        if (canDeduct(amount)) {
            log.info("[deductAmount] deduct amount publish event, balance = {} , amount = {}",
                    balance, amount);
            balance -= amount;
            log.info("[deductAmount] deduct amount publish event, final balance = {}", balance);
            eventPublisher.publishEvent(new AmountDeduct(amount));
        } else {
            log.error("[deductAmount] Transfer failed publish event");
            eventPublisher.publishEvent(new TransferFailed(amount));
        }
    }

    @EventListener
    public void revertDeduction(final CreditFailed creditFailed) {
        log.info("[revertDeduction] revert deduction, current balance = {}, credit failed amount = {}"
                ,balance, creditFailed.amount());
        // 보상 로직
        balance += creditFailed.amount();
        log.info("[revertDeduction] revert deduction, current balance = {}", balance);
    }

    @EventListener
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
