package com.inhyung.fwp;

import java.util.ArrayList;
import java.util.Collections;

public class Deck {
    private ArrayList<Card> cards;

    public Deck() { //전체 카드 배열
        cards = new ArrayList<>();
        for (int suit = 0; suit < 4; suit++) {
            for (int num = 1; num <= 13; num++) {
                cards.add(new Card(num, suit));
            } //52장의 모든 카드 배열에 넣기
        }
        shuffle();
    }

    public void shuffle() { //무작위 순서로 섞음
        Collections.shuffle(cards);
    } //셔플 : 말 그대로 배열 섞어줌

    public Card draw() {
        if (cards.isEmpty()) return null;
        return cards.remove(0);
    }//이게 remove를 하면 없애는 것 뿐만아니라 그 없앤 애에 대한 정보를 저장을 해둠
    //그래서 리턴되는게 remove된 애임.

    public boolean isEmpty() {
        return cards.isEmpty();
    }
}

