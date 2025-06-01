package com.inhyung.fwp;

import android.util.Log;

public class Enemy {
    private int hp;
    private int maxHp;
    private int turn;
    private int maxTurn;
    private int dmg;
    private String name;

    public Enemy(String name, int hp, int maxTurn, int dmg) {
        this.name = name;
        this.hp = hp;
        this.maxHp = hp;
        this.turn = maxTurn;
        this.maxTurn = maxTurn;
        this.dmg = dmg;
    }

    public void takeDamage(int dmg) {
        hp -= dmg;
        if (hp < 0) hp = 0;
    }

    public void decrementTurn(Player player) {
        turn--;
        if (turn <= 0) {
            attackPlayer(player);
            turn = maxTurn;
        }
    }

    public void attackPlayer(Player player) {
        player.takeDamage(dmg);
        Log.d("Enemy", name + "이 플레이어를 공격했습니다! DMG: " + dmg);
    }

    public int getHp() {
        return hp;
    }

    public int getMaxHp() {
        return maxHp;
    }

    public int getTurn() {
        return turn;
    }

    public int getMaxTurn() {
        return maxTurn;
    }

    public int getDmg() {
        return dmg;
    }

    public String getName() {
        return name;
    }

    public void setTurn(int turn) {
        this.turn = turn;
    }

    public void setDmg(int dmg) {
        this.dmg = dmg;
    }
}
