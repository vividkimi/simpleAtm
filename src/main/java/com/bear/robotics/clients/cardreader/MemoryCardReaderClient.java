package com.bear.robotics.clients.cardreader;

import com.bear.robotics.clients.bank.MemoryBankApiClient;
import com.bear.robotics.domain.Card;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Random;

@Slf4j
public class MemoryCardReaderClient implements CardReaderClientSpec {

    // just for test
    private static List<Card> cards = new ArrayList<>();

    @Override
    public Optional<Card> readCard() {
        Random rand = new Random();
        return Optional.ofNullable(cards.get(rand.nextInt(cards.size())));
    }

    public static Card registerNewCard(String userName) {
        try {
            // just for test..
            Card card = MemoryBankApiClient.registerNewCard(userName);

            cards.add(card);
            return card;

        } catch (Exception e) {
            log.error(e.getMessage());
        }

        return null;

    }
}
