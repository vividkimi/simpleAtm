package com.bear.robotics.clients.bank;

import com.bear.robotics.domain.Account;
import com.bear.robotics.domain.Card;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
class MemoryBankApiClientTest {

    private static MemoryBankApiClient memoryBankApiClient = new MemoryBankApiClient();


    @DisplayName("validatePIN method test")
    @ParameterizedTest(name = "[{index}] {0}")
    @MethodSource
    void validatePIN(String name, Long settingPin, Long tryPin, Boolean expect) {
        Card card = MemoryBankApiClient.registerNewCard("Sungmin");
        MemoryBankApiClient.saveCardPIN(card, settingPin);
        Boolean actual = memoryBankApiClient.validatePIN(card, tryPin);
        assertThat(actual).isEqualTo(expect);
    }


    static Stream<Arguments> validatePIN() {
        return Stream.of(
                Arguments.of(
                        "set 1234 try 1234",
                        1234L,
                        1234L,
                        true
                ),
                Arguments.of(
                        "set 1234 try 4321",
                        1234L,
                        4321L,
                        false
                )
        );
    }

    @DisplayName("getAccounts method test")
    @ParameterizedTest(name = "[{index}] {0}")
    @MethodSource
    void getAccounts(String name, Card card, List<Account> expect) {
        List<Account> actual = memoryBankApiClient.getAccounts(card);

        assertThat(actual).containsExactlyInAnyOrder(expect.toArray(new Account[]{}));
    }


    static Stream<Arguments> getAccounts() {
        // given
        Card noramlCard = MemoryBankApiClient.registerNewCard("Sungmin");

        Account account1 = MemoryBankApiClient.registerNewAccount("Sungmin");
        Account account2 = MemoryBankApiClient.registerNewAccount("Sungmin");

        MemoryBankApiClient.saveCardAccounts(noramlCard, account1);
        MemoryBankApiClient.saveCardAccounts(noramlCard, account2);

        return Stream.of(
                Arguments.of(
                        "normal card(2 accounts)",
                        noramlCard,
                        Arrays.asList(account1, account2)

                ),
                Arguments.of(
                        "empty card",
                        MemoryBankApiClient.registerNewCard("Sungmin"),
                        Collections.emptyList()
                )
        );
    }

    @DisplayName("deposit method test")
    @ParameterizedTest(name = "[{index}] {0}")
    @MethodSource
    void deposit(String name, Account account, Long dollar, Long expect) {
        memoryBankApiClient.deposit(account, dollar);
        Long actual = account.getBalance();


        assertThat(actual).isEqualTo(expect);
    }


    static Stream<Arguments> deposit() {
        Account account0 = MemoryBankApiClient.registerNewAccount("Sungmin");
        Account account20 = MemoryBankApiClient.registerNewAccount("Sungmin");

        account20.setBalance(20L);

        return Stream.of(
                Arguments.of(
                        "$0 -> $10",
                        account0,
                        10L,
                        10L

                ),
                Arguments.of(
                        "$20 -> $520",
                        account20,
                        500L,
                        520L
                )
        );
    }


    @DisplayName("withdraw method test")
    @ParameterizedTest(name = "[{index}] {0}")
    @MethodSource
    void withdraw(String name, Account account, Long dollar, Long balanceExpect, Boolean isSuccessExpect) {
        Boolean isSuccessActual = memoryBankApiClient.withdraw(account, dollar);
        Long actual = account.getBalance();


        assertThat(actual).isEqualTo(balanceExpect);
        assertThat(isSuccessActual).isEqualTo(isSuccessExpect);
    }


    static Stream<Arguments> withdraw() {
        Account account500 = MemoryBankApiClient.registerNewAccount("Sungmin");
        Account account20 = MemoryBankApiClient.registerNewAccount("Sungmin");

        account500.setBalance(500L);
        account20.setBalance(20L);

        return Stream.of(
                Arguments.of(
                        "$500 -> $10",
                        account500,
                        490L,
                        10L,
                        true

                ),
                Arguments.of(
                        "$20 -> try $500 -> fail",
                        account20,
                        500L,
                        20L,
                        false
                )
        );
    }


}
