package com.twopc.skeleton.transaction;

import lombok.Builder;

@Builder
public record Transaction(int balance, int amount, TransactionType trType) {
}
