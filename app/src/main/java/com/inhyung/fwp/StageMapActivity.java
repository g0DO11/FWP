package com.inhyung.fwp;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.widget.Button;
import android.widget.GridLayout;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

public class StageMapActivity extends AppCompatActivity {
    private GridLayout stageGrid;
    private StageMap stageMap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stage_map);

        stageGrid = findViewById(R.id.stage_grid);

        // 1. 맵 데이터 불러오기
        stageMap = ((GameApplication) getApplication()).getStageMap();

        // 2. UI 그리기
        drawMap();
    }

    private void drawMap() {
        stageGrid.removeAllViews();

        for (StageNode node : stageMap.getNodes()) {
            Button btn = new Button(this);
            btn.setText(String.valueOf(node.getId()));

            StageNode current = stageMap.getCurrentNode();
            if (node.getId() == current.getId()) {
                btn.setBackgroundColor(Color.GREEN); // 현재 위치
            } else if (node.getType() == StageNode.Type.BOSS) {
                btn.setBackgroundColor(Color.RED); //보스
            } else if (node.isAccessibleFrom(current)) {
                btn.setBackgroundColor(Color.YELLOW); // 이동 가능한 노드
            } else if (node.isCleared()||node.getType() == StageNode.Type.START){
                btn.setBackgroundColor(Color.DKGRAY); // 클리어된 노드
            } else if (!node.isCleared() && node.getType() != StageNode.Type.BOSS){
                btn.setBackgroundColor(Color.GRAY); //보스가 아니면서 클리어되지 않은(갈 수 없는) 노드
            }

            btn.setOnClickListener(v -> {
                if (node.isAccessibleFrom(current)) {
                    // 이동 처리
                    stageMap.moveTo(node.getId());

                    // BattleActivity로 이동
                    Intent intent = new Intent(this, Battle.class);
                    intent.putExtra("nodeId", node.getId());
                    startActivity(intent);
                } else {
                    Toast.makeText(this, "접근 불가!", Toast.LENGTH_SHORT).show();
                }
            });

            GridLayout.LayoutParams params = new GridLayout.LayoutParams();
            params.rowSpec = GridLayout.spec(node.getY());
            params.columnSpec = GridLayout.spec(node.getX());
            stageGrid.addView(btn, params);
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        drawMap(); // 돌아왔을 때 다시 그리기
    }

    @Override
    public void onBackPressed(){
        new AlertDialog.Builder(this)
                .setTitle("게임 종료")
                .setMessage("메인 화면으로 돌아가시겠습니까?\n진행 중인 게임은 사라집니다.")
                .setPositiveButton("예", (dialog, which) -> {
                    // 메인 액티비티를 새 작업으로 시작하면서 모든 기존 액티비티 제거
                    Intent intent = new Intent(StageMapActivity.this, MainActivity.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                    startActivity(intent);
                })
                .setNegativeButton("아니오", (dialog, which) -> dialog.dismiss())
                .show();
    }
}

