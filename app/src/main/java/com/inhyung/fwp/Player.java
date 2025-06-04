package com.inhyung.fwp;

public class Player {
    private static int hp;
    private int maxHp;
    private static int money;

    public Player() {
        this.maxHp = 100;
        this.hp = 100;
        this.money = 0;
    }

    public void takeDamage(int dmg) {
        hp -= dmg;
        if (hp < 0) hp = 0;
    }

    public boolean isDead() {
        return hp <= 0;
    }

    public static int getHp() {
        return hp;
    }

    public void recoverHp(int recovery){ if(hp+recovery>maxHp) hp=maxHp; else hp+=recovery; }

    public int getMaxHp() {
        return maxHp;
    }

    public void earnMoney(int reward) {
        money += reward;
    }
    public void spendMoney(int cost) { money -= cost; }

    public static int getMoney() {
        return money;
    }
}
