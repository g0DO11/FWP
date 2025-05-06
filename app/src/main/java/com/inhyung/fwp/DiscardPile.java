package com.inhyung.fwp;

import java.util.ArrayList;
import java.util.LinkedList;

public class DiscardPile {
    private final LinkedList<Card> pile;

    public DiscardPile() {
        pile = new LinkedList<>();
    }

    public void discard(Card card) {
        pile.add(card);
    }

    public void discardAll(ArrayList<Card> cards) {
        pile.addAll(cards);
    }

    public int size() {
        return pile.size();
    }
}