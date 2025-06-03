package com.inhyung.fwp;

import java.util.Objects; // Objects 클래스 임포트

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

    //그냥하면 이게 다른걸로 인식을 해버리니까 동등성있다고 해줘야됨.
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Card card = (Card) o;
        return number == card.number && suit == card.suit;
    }

    @Override
    public int hashCode() {
        return Objects.hash(number, suit);
    }
}