package com.twopc.skeleton.coordinator;

import com.twopc.skeleton.bank.BankingService;
import com.twopc.skeleton.transaction.TransactionType;

public class TransferCoordinatorService {

	public void transfer(BankingService origin, BankingService destination, int amount) {
		// 두 로직이 순차적이 아닌 독립적으로 실행된다고 가정
		boolean withdrawPrepare = origin.prepareTransfer(TransactionType.WITHDRAW, amount);
		boolean depositPrepare = destination.prepareTransfer(TransactionType.DEPOSIT, amount);

		if (withdrawPrepare && depositPrepare) {
			origin.commit();
			destination.commit();
		} else {
			origin.rollback();
			destination.rollback();
		}
	}
}
