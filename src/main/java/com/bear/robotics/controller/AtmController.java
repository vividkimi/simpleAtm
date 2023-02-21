package com.bear.robotics.controller;

import com.bear.robotics.controller.exception.*;
import com.bear.robotics.controller.response.ApiResponse;
import com.bear.robotics.domain.Account;
import com.bear.robotics.domain.Card;
import com.bear.robotics.service.AtmService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Slf4j
@RestController
@RequiredArgsConstructor
public class AtmController {


    private final AtmService atmService;

    public ApiResponse<Card> insertCard() {
        try {
            return ApiResponse.success(atmService.readCard());
        } catch (CardReadException e) {
            return ApiResponse.fail(e.getMessage());
        }
    }

    public ApiResponse<List<Account>> getAccountsOfCard(Card card, Long pin) {
        if (atmService.validatePIN(card, pin)) {
            return ApiResponse.success(atmService.getAccounts(card));
        } else {
            return ApiResponse.fail("WRONG PIN CODE");
        }
    }


    public ApiResponse<Boolean> validatePIN(Card card, Long pin) {
        return ApiResponse.of(atmService.validatePIN(card, pin), "validate PIN");
    }

    // ui engineer can read from Account obj, but just in case.
    public ApiResponse<Long> getBalance(Account account) {
        return ApiResponse.success(atmService.getBalance(account));
    }

    public ApiResponse<Boolean> deposit(Account account, Long dollar) {

        try {
            return ApiResponse.of(atmService.deposit(account, dollar), "deposit");
        } catch (CacheBinException | BankApiException e) {
            return ApiResponse.fail(e.getMessage());
        } catch (BankLossDepositException blde) {
            return ApiResponse.majorFail(blde.getMessage());
        }

    }

    public ApiResponse<Boolean> withdraw(Account account, Long dollar) {

        try {
            return ApiResponse.of(atmService.withdraw(account, dollar), "withdraw");
        } catch (CacheBinException cbe) {
            return ApiResponse.fail(cbe.getMessage());
        } catch (UserLossWithdrawException ulwe) {
            return ApiResponse.majorFail(ulwe.getMessage());
        }

    }

}
