package com.inhyung.fwp;

public class Enemy {
    int hp;

    public Enemy(int hp) {
        this.hp = hp;
    }

    public void takeDamage(int dmg) {
        hp -= dmg;
        if (hp < 0) hp = 0;
    }

    public int getHp() {
        return hp;
    }
}
