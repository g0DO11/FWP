package com.inhyung.fwp;

//아예 이걸 따로 이케 만들어놓는게 좋은 띵킹인것 같다~
public class HandEvaluator {
//    // 족보별로 공격력 점수 부여
//    public static int evaluate(List<Card> cards) {
//        if (isFlush(cards)) return 100;
//        if (isStraight(cards)) return 80;
//        if (isPair(cards)) return 50;
//        return 20; // 기본값
//    }
//
//    private static boolean isFlush(List<Card> cards) {
//        String suit = cards.get(0).getSuit();
//        for (Card c : cards) {
//            if (!c.getSuit().equals(suit)) return false;
//        }
//        return true;
//    }
//
//    private static boolean isStraight(List<Card> cards) {
//        List<Integer> nums = new ArrayList<>();
//        for (Card c : cards) nums.add(c.getNumber());
//        Collections.sort(nums);
//        for (int i = 0; i < nums.size() - 1; i++) {
//            if (nums.get(i + 1) - nums.get(i) != 1) return false;
//        }
//        return true;
//    }
//
//    private static boolean isPair(List<Card> cards) {
//        Map<Integer, Integer> map = new HashMap<>();
//        for (Card c : cards) {
//            map.put(c.getNumber(), map.getOrDefault(c.getNumber(), 0) + 1);
//        }
//        for (int val : map.values()) {
//            if (val >= 2) return true;
//        }
//        return false;
//    }
}
