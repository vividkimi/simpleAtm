package com.bear.robotics.clients.bank;

import com.bear.robotics.domain.Account;
import com.bear.robotics.domain.Card;

import java.util.List;

public interface BankApiClientSpec {
    Boolean validatePIN(Card card, Long pin);
    List<Account> getAccounts(Card card);
    Boolean deposit(Account account, Long dollar);
    Boolean withdraw(Account account, Long dollar);

}
