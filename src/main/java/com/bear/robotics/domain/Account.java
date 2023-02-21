package com.bear.robotics.domain;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.Objects;

@Getter
public class Account {

    // todo: temporary open setter before using bank api (must be removed)
    @Setter
    private Long balance;
    private Long accountNo;
    private String userName;

    @Builder
    public Account(Long balance, Long accountNo, String userName) {
        this.balance = balance;
        this.accountNo = accountNo;
        this.userName = userName;
    }

    public Boolean validateWithdraw(Long dollar){
        return balance >= dollar;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Account account = (Account) o;
        return accountNo.equals(account.accountNo);
    }

    @Override
    public int hashCode() {
        return Objects.hash(accountNo);
    }
}
