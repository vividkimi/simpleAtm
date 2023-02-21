package com.bear.robotics.clients.bank;

import com.bear.robotics.domain.Account;
import com.bear.robotics.domain.Card;
import lombok.extern.slf4j.Slf4j;

import java.util.*;
import java.util.concurrent.atomic.AtomicLong;

@Slf4j
public class MemoryBankApiClient implements BankApiClientSpec {

    // just for test
    private static Map<Card, Long> cardPinMap = new HashMap<>();
    private static Map<Card, List<Account>> cardAccountsMap = new HashMap<>();

    private static AtomicLong accountSequence = new AtomicLong();
    private static AtomicLong cardSequence = new AtomicLong();


    @Override
    public Boolean validatePIN(Card card, Long pin) {
        Long rightPin = cardPinMap.get(card);
        if (Objects.isNull(rightPin)) {
            log.error("NOT REGISTERED CARD");
            return false;
        }
        return rightPin.equals(pin);
    }

    @Override
    public List<Account> getAccounts(Card card) {
        return cardAccountsMap.getOrDefault(card, Collections.emptyList());
    }


    @Override
    public Boolean deposit(Account account, Long dollar) {
        Long balance = account.getBalance();
        account.setBalance(balance + dollar);
        return true;
    }

    @Override
    public Boolean withdraw(Account account, Long dollar) {
        if (account.validateWithdraw(dollar)) {
            Long balance = account.getBalance();
            account.setBalance(balance - dollar);
            return true;
        } else {
            log.error("NOT ENOUGH BALANCE");
            return false;

        }
    }


    // just for test

    public static void saveCardPIN(Card card, Long pin) {
        try {
            cardPinMap.put(card, pin);
        } catch (Exception e) {
            log.error(e.getMessage());
        }

    }

    public static void saveCardAccounts(Card card, Account account) {
        try {
            cardAccountsMap.computeIfAbsent(card, k -> new ArrayList<>()).add(account);
        } catch (Exception e) {
            log.error(e.getMessage());
        }

    }


    public static Card registerNewCard(String userName) {
        try {
            Card card = Card.builder()
                    .cardNo(cardSequence.getAndIncrement())
                    .userName(userName)
                    .build();
            cardAccountsMap.put(card, new ArrayList<>());
            return card;
        } catch (Exception e) {
            log.error(e.getMessage());

        }

        return null;

    }

    public static Account registerNewAccount(String userName) {
        try {
            return Account.builder()
                    .accountNo(accountSequence.getAndIncrement())
                    .balance(0L)
                    .userName(userName)
                    .build();

        } catch (Exception e) {
            log.error(e.getMessage());
            return null;
        }
    }
}
