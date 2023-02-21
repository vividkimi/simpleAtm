package com.bear.robotics.service;


import com.bear.robotics.clients.bank.BankApiClientSpec;
import com.bear.robotics.clients.bank.MemoryBankApiClient;
import com.bear.robotics.clients.cachebin.CacheBinClientSpec;
import com.bear.robotics.clients.cachebin.CacheBinTestClient;
import com.bear.robotics.clients.cardreader.CardReaderClientSpec;
import com.bear.robotics.clients.cardreader.MemoryCardReaderClient;
import com.bear.robotics.controller.exception.*;
import com.bear.robotics.domain.Account;
import com.bear.robotics.domain.Card;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class AtmService {
    private final CardReaderClientSpec cardReaderClient = new MemoryCardReaderClient();
    private final BankApiClientSpec bankApiClient = new MemoryBankApiClient();
    private final CacheBinClientSpec cacheBinClient = new CacheBinTestClient();

    public Card readCard() throws CardReadException {
        return cardReaderClient.readCard()
                .orElseThrow(() -> new CardReadException("fail to read card"));

    }

    public List<Account> getAccounts(Card card) {
        return bankApiClient.getAccounts(card);
    }


    public Boolean validatePIN(Card card, Long pin) {
        return bankApiClient.validatePIN(card, pin);
    }

    // ui engineer can read from Account obj, but just in case.
    public Long getBalance(Account account) {
        return account.getBalance();
    }

    public Boolean deposit(Account account, Long dollar) throws CacheBinException, BankLossDepositException, BankApiException {

        if (bankApiClient.deposit(account, dollar)) {
            if (cacheBinClient.deposit(dollar)) {
                return Boolean.TRUE;
            } else {
                if (bankApiClient.withdraw(account, dollar)) {
                    throw new CacheBinException("fail to deposit to cache bin");

                } else {
                    throw new BankLossDepositException("fail to deposit into cache bin and cancel deposit with bank api");

                }
            }
        } else {
            throw new BankApiException("fail to deposit with bank api");
        }

    }

    public Boolean withdraw(Account account, Long dollar) throws CacheBinException, UserLossWithdrawException {

        if (bankApiClient.withdraw(account, dollar)) {
            if (cacheBinClient.withdraw(dollar)) {
                return Boolean.TRUE;
            } else {
                if (bankApiClient.deposit(account, dollar)) {
                    throw new CacheBinException("fail to withdraw from cache bin");
                } else {
                    throw new UserLossWithdrawException("fail to withdraw from cache bin and cancel withdraw with bank api");
                }
            }
        } else {
            return Boolean.FALSE;
        }
    }

}
