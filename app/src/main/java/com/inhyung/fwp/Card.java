package com.inhyung.fwp;

public class Card {
    private int number;     // 1 ~ 13
    private int suit;       // 0 = heart, 1 = droplet, 2 = wind, 3 = leaf

    public Card(int number, int suit) {
        this.number = number;
        this.suit = suit;
    }

    public int getNumber() {
        return number;
    }

    public String getSuit() {
        return suitToString(this.suit);
    }

    public int getSuitInt() {
        return suit;
    }

    @Override
    public String toString() {
        return number + " of " + suitToString(suit);
    }

    private String suitToString(int suit) {
        switch (suit) {
            case 0: return "Heart";
            case 1: return "Droplet";
            case 2: return "Leaf";
            case 3: return "Wind";
            default: return "Unknown";
        }
    }
}
