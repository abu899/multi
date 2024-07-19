package com.twopc.skeleton.bank;

import com.twopc.skeleton.transaction.Transaction;
import com.twopc.skeleton.transaction.TransactionType;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class KBBankService implements BankingService {
	private int balance;
	private Transaction transaction;

	public KBBankService(int balance) {
		this.balance = balance;
	}

	@Override
	public int getBalance() {
		return this.balance;
	}

	@Override
	public void saveTransaction(int amount, TransactionType transactionType) {
		this.transaction = new Transaction(this.balance, amount, transactionType);
	}

	@Override
	public void commit() {
		log.info("KB bank commit");
		if (this.transaction.trType() == TransactionType.WITHDRAW) {
			this.balance = this.balance - this.transaction.amount();
		} else {
			this.balance = this.balance + this.transaction.amount();
		}

		log.info("After commit: tractionType = {}, amount = {}, balance = {}", this.transaction.trType(),
			this.transaction.amount(), this.balance);
	}

	@Override
	public void rollback() {
		log.info("KB transaction is rollback = {}", this.transaction);
		this.transaction = null;
	}
}
