package com.inhyung.fwp;

public class Enemy extends Character {
    private String name;
    private int turn;
    private int dmg;

    public Enemy(String name, int hp, int turn, int dmg) {
        super(hp);
        this.name = name;
        this.turn = turn;
        this.dmg = dmg;
    }

    public String getName() {return name;}
    public int getTurn() {return turn;}
    public int getDmg() {return dmg;}

    public void setName(String name) {this.name = name;}
    public void setTurn(int turn) {this.turn = turn;}
    public void setDmg(int dmg) {this.dmg = dmg;}

    
}
