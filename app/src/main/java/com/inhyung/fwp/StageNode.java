package com.inhyung.fwp;

import java.util.ArrayList;
import java.util.List;

public class StageNode {
    public enum Type { START, NORMAL, BOSS, RECOVER }

    private int id;
    private int x;
    private int y;
    private int stage;
    private Type type;
    private boolean cleared;
    private List<Integer> nextNodeIds = new ArrayList<>();

    public StageNode(int id, int x, int y, int stage, Type type) {
        this.id = id;
        this.x = x;
        this.y = y;
        this.stage = stage;
        this.type = type;
        this.cleared = false;
    }

    public void addNext(int nextId) {
        nextNodeIds.add(nextId);
    }

    public List<Integer> getNextNodeIds() {
        return nextNodeIds;
    }

    public int getId() { return id; }
    public int getX() { return x; }
    public int getY() { return y; }
    public int getStage() { return stage; }
    public Type getType() { return type; }
    public boolean isCleared() { return cleared; }
    public void setCleared(boolean cleared) { this.cleared = cleared; }

    public boolean isAccessibleFrom(StageNode current) {
        return current.nextNodeIds.contains(this.id);
    }
}

