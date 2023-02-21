package com.bear.robotics.domain;

import lombok.Builder;
import lombok.Getter;

import java.util.Objects;

@Getter
public class Card {
    private Long cardNo;
    private String userName;

    @Builder
    public Card(Long cardNo, String userName) {
        this.cardNo = cardNo;
        this.userName = userName;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Card card = (Card) o;
        return cardNo.equals(card.cardNo);
    }

    @Override
    public int hashCode() {
        return Objects.hash(cardNo);
    }
}
