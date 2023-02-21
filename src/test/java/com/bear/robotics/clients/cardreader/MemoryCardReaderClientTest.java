package com.bear.robotics.clients.cardreader;

import com.bear.robotics.domain.Card;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
class MemoryCardReaderClientTest {

    private static MemoryCardReaderClient memoryCardReaderClient = new MemoryCardReaderClient();


    @DisplayName("readCard method test")
    @Test
    void readCard() {
        Card actual = MemoryCardReaderClient.registerNewCard("Sungmin");
        // it's test, I don't do ifPresent or something like that
        Card getCard = memoryCardReaderClient.readCard().get();


        assertThat(actual).isEqualTo(getCard);
    }


}
