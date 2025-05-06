package com.inhyung.fwp;

import java.util.ArrayList;

public class Hand {
    private ArrayList<Card> cards;

    public Hand() {
        cards = new ArrayList<>();
    }

    public void drawFromDeck(Deck deck, int count) {
        for (int i = 0; i < count; i++) {
            if (!deck.isEmpty()) {
                cards.add(deck.draw());
            }
        }
    }

    public ArrayList<Card> getCards() {
        return cards;
    }

    public void clear() {
        cards.clear();
    }

    public void showHand() {
        for (Card card : cards) {
            System.out.println(card);
        }
    }
}
