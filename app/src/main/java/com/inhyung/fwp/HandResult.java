package com.inhyung.fwp;

public class HandResult {
    public String combinationName;
    public int combinationScore;
    public int cardBaseDamage;
    public int totalDamage;

    public HandResult(String combinationName, int combinationScore, int cardBaseDamage) {
        this.combinationName = combinationName;
        this.combinationScore = combinationScore;
        this.cardBaseDamage = cardBaseDamage;
        this.totalDamage = combinationScore + cardBaseDamage;
    }
}