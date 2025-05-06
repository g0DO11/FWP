package com.inhyung.fwp;

public class Enemy extends Character {
    private String name;
    public Enemy(String name, int hp) {
        super(hp);
        this.name = name;
    }
}
