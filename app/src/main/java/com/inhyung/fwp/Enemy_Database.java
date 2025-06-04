package com.inhyung.fwp;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Enemy_Database {
    private static List<Enemy> enemyList = new ArrayList<>();

    static{
        //1스테이지 적들
        enemyList.add(new Enemy("섹시감자", 100, 2, 60, 30, R.drawable.enemy_sexy_p, 1, StageNode.Type.NORMAL));
        enemyList.add(new Enemy("스윗포테토", 150, 3, 100, 40, R.drawable.enemy_sweet_p, 1, StageNode.Type.NORMAL));
        enemyList.add(new Enemy("호미패밀리", 200, 1, 10, 20, R.drawable.enemy_homi, 1, StageNode.Type.NORMAL));
        enemyList.add(new Enemy("목장갑", 120, 1, 50, 40, R.drawable.enemy_redglove, 1, StageNode.Type.NORMAL));
        enemyList.add(new Enemy("두더쥐", 180, 2, 90, 50, R.drawable.enemy_mole, 1, StageNode.Type.NORMAL));
        enemyList.add(new Enemy("진딧물", 50, 1, 20, 5, R.drawable.enemy_jindi, 1, StageNode.Type.NORMAL));

        //1스테이지 보스
        enemyList.add(new Enemy("농부", 500, 2, 20, 100, R.drawable.enemy_farmer, 1, StageNode.Type.BOSS));
    }

    public static Enemy getRandomEnemy(int stage, StageNode.Type type) {
        List<Enemy> filtered = new ArrayList<>();
        for (Enemy e : enemyList) {
            if (e.getStage() == stage && e.getType() == type) {
                filtered.add(e);
            }
        }
        if (filtered.isEmpty()) return null;

        Random rand = new Random();
        Enemy selected = filtered.get(rand.nextInt(filtered.size()));
        return new Enemy(selected);
    }
}
