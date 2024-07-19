package com.twopc.skeleton.bank;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.twopc.skeleton.transaction.TransactionType;

public interface BankingService {
	Logger log = LoggerFactory.getLogger(BankingService.class);

	default boolean prepareTransfer(TransactionType transferType, int amount) {
		saveTransaction(amount, transferType);
		if (transferType == TransactionType.DEPOSIT) {
			if (getBalance() + amount >= 10000) {
				log.error("Deposit limit exceeded");
				return false;
			}
		} else {
			if (getBalance() < amount) {
				log.error("Not enough balance to withdraw");
				return false;
			}
		}
		log.info("Transaction prepared complete");
		return true;
	}

	void saveTransaction(int amount, TransactionType transactionType);

	void commit();

	void rollback();

	int getBalance();
}
