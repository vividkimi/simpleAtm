package com.bear.robotics.clients.cardreader;

import com.bear.robotics.domain.Card;

import java.util.Optional;

public interface CardReaderClientSpec {
    Optional<Card> readCard();
}
