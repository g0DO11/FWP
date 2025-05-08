package com.inhyung.fwp;

import java.util.*;

public class DmgEvaluator {

    // 카드 숫자 합
    private static int cardDmg(List<Card> cards) {
        int dmg = 0;
        for (Card c : cards) {
            dmg += c.getNumber();
        }
        return dmg;
    }

    public static int evaluate(List<Card> cards) {
        HandInfo info = analyzeHand(cards);
        int baseDmg = cardDmg(cards);

        int maxSameNumber = Collections.max(info.numberCount.values());
        int pairCount = (int) info.numberCount.values().stream().filter(v -> v == 2).count();
        //val == 2인, 즉 pair인 애들만 셌음.

        if (info.isFlush && info.isStraight && isTopStraight(info.sortedNumbers)) {
            return 500 + baseDmg; // 로얄 스트레이트 플러쉬
        } else if (info.isFlush && info.isStraight && isLowStraight(info.sortedNumbers)) {
            return 400 + baseDmg; // 백 스트레이트 플러쉬
        } else if (info.isFlush && info.isStraight) {
            return 400 + baseDmg; // 스트레이트 플러쉬
        } else if (maxSameNumber == 4) {
            return 350 + baseDmg; // 포커
        } else if (maxSameNumber == 3 && pairCount == 1) {
            return 300 + baseDmg; // 풀 하우스
        } else if (info.isFlush) {
            return 200 + baseDmg; // 플러쉬
        } else if (isLowStraight(info.sortedNumbers)) {
            return 150 + baseDmg; // 백 스트레이트
        } else if (info.isStraight) {
            return 150 + baseDmg; // 스트레이트
        } else if (maxSameNumber == 3) {
            return 120 + baseDmg; // 트리플
        } else if (pairCount >= 2) {
            return 100 + baseDmg; // 투 페어
        } else if (pairCount == 1) {
            return 60 + baseDmg; // 원 페어
        } else {
            return 30 + baseDmg; // 노 페어
        }
    }


    // 숫자가 10,11,12,13,1인 경우
    private static boolean isTopStraight(List<Integer> nums) {
        return new HashSet<>(nums).containsAll(Arrays.asList(1, 10, 11, 12, 13));
    }

    private static boolean isLowStraight(List<Integer> nums) {
        return new HashSet<>(nums).containsAll(Arrays.asList(1, 2, 3, 4, 5));
    }


    //스트레이트인지 판별
    private static boolean isConsecutive(List<Integer> nums) {
        if (nums.size() < 5) return false;
        for (int i = 0; i <= nums.size() - 5; i++) { //이런 식으로 만든 이유는 5장 이상의 카드에서도 판별 위함. 추후 특능 활용 가능
            boolean straight = true;
            for (int j = 0; j < 4; j++) {
                if (nums.get(i + j + 1) - nums.get(i + j) != 1) {
                    straight = false;
                    break;
                }
            }
            if (straight) return true;
        }
        return false;
    }

    private static HandInfo analyzeHand(List<Card> cards) {
        HandInfo info = new HandInfo();

        for (Card c : cards) {
            //각 숫자 1~13이 몇 번 나왔는지 셈. //.put(key, value)순으로 받아서, 새로운 key면 기본밸류 0으로 주고 //아래 문양도 동일
            info.numberCount.put(c.getNumber(), info.numberCount.getOrDefault(c.getNumber(), 0) + 1);
            info.suitCount.put(c.getSuit(), info.suitCount.getOrDefault(c.getSuit(), 0) + 1);
        }

        Set<Integer> numSet = new HashSet<>(info.numberCount.keySet()); //지금 내가 가진 숫자들만 받은거고
        info.sortedNumbers = new ArrayList<>(numSet); //그걸 넘겨서 정렬했고
        Collections.sort(info.sortedNumbers);

        for (int count : info.suitCount.values()) { //val을 넘겨서 5인게 있으면 플러쉬니까
            if (count >= 5) {
                info.isFlush = true;
                break;
            }
        }

        info.isStraight = isConsecutive(info.sortedNumbers);

        return info; //숫자와 문양의 등장횟수들이 저장돼있고, 플러쉬와 스트레이트 여부가 저장된 정보.
        //이 info를 이용해서 데미지 계산하면 됨.
    }

    private static class HandInfo {

        //Map<key, value>형식으로 key는 중복X, value는 중복O이므로 각 숫자별로 몇개가 있는지 셀 수 있음.
        Map<Integer, Integer> numberCount = new HashMap<>(); //<숫자, 등장 횟수>
        Map<String, Integer> suitCount = new HashMap<>();
        List<Integer> sortedNumbers = new ArrayList<>();
        boolean isFlush = false;
        boolean isStraight = false;
    }
}
/