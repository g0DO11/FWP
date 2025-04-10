package com.inhyung.fwp;

public class Card {
    int card_num; //1~13
    int card_suit; //0 = heart, 1 = droplet(물방울모양), 2= wind, 3=leaf
    public Card(int number, int suit) {
        this.card_num = number;
        this.card_suit = suit;
    }

    public int getNumber() {
        return card_num;
    }

    public int getSuit() {
        return card_suit;
    }
}
