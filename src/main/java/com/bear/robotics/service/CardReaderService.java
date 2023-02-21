package com.bear.robotics.service;


import com.bear.robotics.clients.cardreader.CardReaderClientSpec;
import com.bear.robotics.clients.cardreader.MemoryCardReaderClient;
import com.bear.robotics.domain.Card;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class CardReaderService {

    private final CardReaderClientSpec cardReaderClient = new MemoryCardReaderClient();

    public Optional<Card> readCard(){
        return cardReaderClient.readCard();
    }
}
