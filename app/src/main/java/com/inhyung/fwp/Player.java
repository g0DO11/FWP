package com.inhyung.fwp;

public class Player extends Character {
    private int money;

    public Player() {
        super(100); // HP 설정
        this.money = 0;
    }

    public int getMoney() {
        return money;
    }
}

