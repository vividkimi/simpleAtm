package com.bear.robotics.service;


import com.bear.robotics.clients.bank.MemoryBankApiClient;
import com.bear.robotics.clients.bank.BankApiClientSpec;
import com.bear.robotics.domain.Account;
import com.bear.robotics.domain.Card;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class BankApiService {

    private final BankApiClientSpec bankApiClient = new MemoryBankApiClient();


    public Boolean validatePIN(Card card, Long pin){
        return bankApiClient.validatePIN(card, pin);
    }
    public List<Account> getAccounts(Card card){
        return bankApiClient.getAccounts(card);
    }
    public Boolean deposit(Account account, Long dollar){
        return bankApiClient.deposit(account, dollar);
    }
    public Boolean withdraw(Account account, Long dollar){
        return bankApiClient.withdraw(account, dollar);
    }
}
