package com.inhyung.fwp;

import android.util.Log;

public class Enemy {
    private int hp;
    private int maxHp;
    private int turn;
    private int maxTurn;
    private int dmg;
    private String name;
    private int reward;
    private int imgRes;
    private int stage;
    private StageNode.Type type;
    private SoundManager soundManager;

    public Enemy(String name, int hp, int maxTurn, int dmg, int reward, int img, int stage, StageNode.Type type) {
        this.name = name;
        this.hp = hp;
        this.maxHp = hp;
        this.turn = maxTurn;
        this.maxTurn = maxTurn;
        this.dmg = dmg;
        this.reward = reward;
        this.imgRes = img;
        this.stage = stage;
        this.type = type;
    }

    //복사 생성자
    public Enemy(Enemy other) {
        this.name = other.getName();
        this.hp = other.getHp();
        this.maxHp = other.getHp();
        this.turn = other.getTurn();
        this.maxTurn = other.getTurn();
        this.dmg = other.getDmg();
        this.reward = other.getReward();
        this.imgRes = other.getImgRes();
        this.stage = other.getStage();
        this.type = other.getType();
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
        if (soundManager != null) soundManager.playSound(SoundManager.SOUND_ENEMY_ATTACK);
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

    public int getReward() {
        return reward;
    }

    public int getImgRes() { return imgRes; }

    public int getStage() { return stage;}

    public StageNode.Type getType() { return type; }

    public void setTurn(int turn) {
        this.turn = turn;
    }

    public void setDmg(int dmg) {
        this.dmg = dmg;
    }
}
