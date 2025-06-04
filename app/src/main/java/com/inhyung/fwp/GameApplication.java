package com.inhyung.fwp;

import android.app.Application;
import android.util.Log;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class GameApplication extends Application {
    private static GameApplication instance;
    private Player player;
    private GameDifficulty difficulty;
    private List<StageMap> stageMaps;
    private StageMap currentStageMap;

    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;
        stageMaps = StageMapLoader.load(this);
        currentStageMap = stageMaps.get(0);
    }

    public static GameApplication getInstance() {
        return instance;
    }

    public void initStageMap(GameDifficulty difficulty) {
        List<StageNode> nodes = new ArrayList<>();
        Map<Integer, StageNode> nodeMap = new HashMap<>();

        switch (difficulty) {
            case EASY:
                break;
            case NORMAL:
                break;
            case HARD:
                break;
            default:
                break;
        }
    }


    public void initPlayer(GameDifficulty difficulty) { //플레이어 생성
        this.difficulty = difficulty;
        this.player = new Player();
    }

    public Player getPlayer() {
        return player;
    }

    public GameDifficulty getDifficulty() {
        return difficulty;
    }

    public enum GameDifficulty {
        EASY,
        NORMAL,
        HARD
    }

    public StageMap getStageMap() { return currentStageMap; }

}
