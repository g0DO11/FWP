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
    private StageMap stageMap;

    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;
    }

    public static GameApplication getInstance() {
        return instance;
    }

    public void initStageMap(GameDifficulty difficulty) {
        List<StageNode> nodes = new ArrayList<>();
        Map<Integer, StageNode> nodeMap = new HashMap<>();

        switch (difficulty) {
            case EASY:
                // 노드 생성
                StageNode n1 = new StageNode(1, 0, 0, StageNode.Type.START);
                StageNode n2 = new StageNode(2, 1, 0, StageNode.Type.NORMAL_B);
                StageNode n3 = new StageNode(3, 1, 1, StageNode.Type.NORMAL_B);
                StageNode n4 = new StageNode(4, 2, 0, StageNode.Type.NORMAL_B);
                StageNode n5 = new StageNode(5, 3, 0, StageNode.Type.BOSS);

                // 연결 구성
                n1.addNext(2);
                n1.addNext(3); // n1에서 n2 또는 n3으로 분기
                n2.addNext(4); // n2에서 n4로
                n3.addNext(4); // n3에서도 n4로
                n4.addNext(5); // n4에서 보스로

                // 맵에 등록
                nodeMap.put(n1.getId(), n1);
                nodeMap.put(n2.getId(), n2);
                nodeMap.put(n3.getId(), n3);
                nodeMap.put(n4.getId(), n4);
                nodeMap.put(n5.getId(), n5);
                break;
            case NORMAL:
            case HARD:
        }
        // 리스트화해서 StageMap 생성
        List<StageNode> nodeList = new ArrayList<>(nodeMap.values());
        this.stageMap = new StageMap(nodeList, 1); // 시작 노드는 항상 ID 1로 설정
        Log.d("stagemap", stageMap.getCurrentNode().toString());
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

    public StageMap getStageMap() {
        return stageMap;
    }

    public void setStageMap(StageMap stageMap) {
        this.stageMap = stageMap;
    }

}
