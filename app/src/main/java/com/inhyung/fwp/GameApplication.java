package com.inhyung.fwp;

import android.app.Application;

public class GameApplication extends Application {
    private Player player;
    private GameDifficulty difficulty;

    @Override
    public void onCreate() {
        super.onCreate();
    }

    public void initPlayer(GameDifficulty difficulty) {
        this.difficulty = difficulty;
        this.player = new Player();
    }

    public Player getPlayer() {
        return player;
    }

    public GameDifficulty getDifficulty() {
        return difficulty;
    }

    public void resetPlayer() {
        this.player = null;
    }

    public enum GameDifficulty {
        EASY,
        NORMAL,
        HARD
    }
}
