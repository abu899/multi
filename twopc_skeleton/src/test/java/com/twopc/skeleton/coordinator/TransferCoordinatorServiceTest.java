package com.twopc.skeleton.coordinator;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import com.twopc.skeleton.bank.BankingService;
import com.twopc.skeleton.bank.KBBankService;
import com.twopc.skeleton.bank.KakaoBankService;

class TransferCoordinatorServiceTest {

	private TransferCoordinatorService transferCoordinatorService = new TransferCoordinatorService();

	@Test
	@DisplayName("모든 계좌가 정상적으로 송금 준비가 된다면 송금이 성공하고 각자의 balance가 업데이트 된다")
	void success() {
		// given
		BankingService origin = new KakaoBankService(100);
		BankingService destination = new KBBankService(100);

		// when
		transferCoordinatorService.transfer(origin, destination, 50);

		// then
		assertThat(origin.getBalance()).isEqualTo(50);
		assertThat(destination.getBalance()).isEqualTo(150);
	}

	@Test
	@DisplayName("인출 계좌의 잔액이 송금액보다 적으면 송금이 실패하고 각자의 balance가 업데이트 되지 않는다")
	void fail() {
		// given
		BankingService origin = new KakaoBankService(100);
		BankingService destination = new KBBankService(100);

		// when
		transferCoordinatorService.transfer(origin, destination, 150);

		// then
		assertThat(origin.getBalance()).isEqualTo(100);
		assertThat(destination.getBalance()).isEqualTo(100);
	}

	@Test
	@DisplayName("송금 계좌의 잔액이 계좌의 한도보다 높으면 송금이 실패하고 각자의 balance가 업데이트 되지 않는다")
	void fail2() {
		// given
		BankingService origin = new KakaoBankService(9000);
		BankingService destination = new KBBankService(5000);

		// when
		transferCoordinatorService.transfer(origin, destination, 6000);

		// then
		assertThat(origin.getBalance()).isEqualTo(9000);
		assertThat(destination.getBalance()).isEqualTo(5000);
	}
}