package com.inhyung.fwp;

public class Character {
    protected int hp;
    protected int maxHp;

    public Character(int maxHp) {
        this.maxHp = maxHp;
        this.hp = maxHp;
    }

    public void takeDamage(int dmg) {
        hp -= dmg;
        if (hp < 0) hp = 0;
    }

    public boolean isDead() {
        return hp <= 0;
    }
}

