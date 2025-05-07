package com.inhyung.fwp;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DmgEvaluator {

    // 족보별로 공격력 점수 부여
    public static int evaluate(List<Card> cards) {
        if (isFlush(cards)) return 100;
        if (isStraight(cards)) return 80;
        if (isNumPair(cards)) return 50;
        if(isSuitPair(cards)) return 30;
        return 20; // 기본값
    }

    //플러쉬 = 같은 무늬 5개
    private static boolean isFlush(List<Card> cards) {
        if(cards.size() != 5){
            return false;
        }

        String suit = cards.get(0).getSuit();
        for (Card c : cards) {
            if (!c.getSuit().equals(suit)) return false;
        }
        return true;
    }

    //스트레이트 = 연속된 숫자 5개
    private static boolean isStraight(List<Card> cards) {
        if(cards.size() != 5){
            return false;
        }

        List<Integer> nums = new ArrayList<>();
        for (Card c : cards) nums.add(c.getNumber());
        Collections.sort(nums);
        for (int i = 0; i < nums.size() - 1; i++) {
            if (nums.get(i + 1) - nums.get(i) != 1) return false;
        }
        return true;
    }


    private static boolean isNumPair(List<Card> cards) {
        Map<Integer, Integer> map = new HashMap<>();
        for (Card c : cards) {
            map.put(c.getNumber(), map.getOrDefault(c.getNumber(), 0) + 1);
        }
        for (int val : map.values()) {
            if (val >= 2) return true;
        }
        return false;
    }

    private static boolean isSuitPair(List<Card> cards) {
        Map<Integer, Integer> map = new HashMap<>();
        for (Card c : cards) {
            map.put(c.getSuitInt(), map.getOrDefault(c.getSuitInt(), 0) + 1);
        }
        for (int val : map.values()) {
            if (val >= 2) return true;
        }
        return false;
    }
}
