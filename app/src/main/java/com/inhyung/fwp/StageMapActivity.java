package com.inhyung.fwp;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import java.util.HashMap;
import java.util.Map;

public class StageMapActivity extends AppCompatActivity {
    private ImageView playerIcon;
    private StageMap stageMap;
    GameApplication app;
    Map<Integer, View> nodeViews = new HashMap<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stage_map);
        playerIcon = findViewById(R.id.player_icon);
        app = GameApplication.getInstance();

        // 1. 맵 데이터 불러오기
        stageMap = ((GameApplication) getApplication()).getStageMap();

        // 2. UI 그리기
        drawMap();
    }

    private void drawMap() {
        FrameLayout nodeContainer = findViewById(R.id.node_container);
        LineView lineView = findViewById(R.id.line_view);
        nodeContainer.removeAllViews();
        lineView.clearLines();

        for (StageNode node : stageMap.getNodes()) {
            Button btn = new Button(this);
            btn.setText(String.valueOf(node.getId()));
            btn.setX(node.getX()); // JSON 좌표
            btn.setY(node.getY());
            btn.setLayoutParams(new FrameLayout.LayoutParams(160, 160));

            StageNode current = stageMap.getCurrentNode();
            if (node.getId() == current.getId()) {
                btn.setBackgroundColor(Color.GREEN);
            } else if (node.getType() == StageNode.Type.BOSS) {
                btn.setBackgroundColor(Color.RED);
            } else if (node.getType() == StageNode.Type.RECOVER){
                btn.setBackgroundColor(Color.BLUE);
            } else if (node.isAccessibleFrom(current)) {
                btn.setBackgroundColor(Color.YELLOW);
            }  else if (node.isCleared() || node.getType() == StageNode.Type.START) {
                btn.setBackgroundColor(Color.DKGRAY);
            } else {
                btn.setBackgroundColor(Color.GRAY);
            }

            btn.setOnClickListener(v -> {
                if (node.isAccessibleFrom(current)) { //이동 가능할 때
                    stageMap.moveTo(node.getId());

                    movePlayerTo(node, () -> {
                        if (node.getType() == StageNode.Type.NORMAL || node.getType() == StageNode.Type.BOSS) {
                            Intent intent = new Intent(this, BattleActivity.class);
                            intent.putExtra("nodeId", node.getId());
                            startActivity(intent);
                        } else if (node.getType() == StageNode.Type.RECOVER) {
                            new AlertDialog.Builder(this)
                                    .setTitle("체력 회복")
                                    .setMessage("체력이 50만큼 회복되었습니다.")
                                    .setPositiveButton("확인", (dialog, which) -> {
                                        app.getPlayer().recoverHp(50);
                                        drawMap(); // 회복 후 다시 맵 갱신
                                    })
                                    .setCancelable(false)
                                    .show();
                        }
                    });
                } else { //이동이 불가능한 노드일 경우
                    if (node.getId() == current.getId())
                        Toast.makeText(this, "현재 위치입니다.", Toast.LENGTH_SHORT).show();
                    else
                        Toast.makeText(this, "접근 불가!", Toast.LENGTH_SHORT).show();
                }
            });

            nodeContainer.addView(btn);
            nodeViews.put(node.getId(), btn);
        }

        // 레이아웃 완료 후 중심 좌표로 선 그리기
        nodeContainer.getViewTreeObserver().addOnGlobalLayoutListener(() -> {
            lineView.clearLines();  // 기존 선 초기화

            for (StageNode node : stageMap.getNodes()) {
                View from = nodeViews.get(node.getId());
                float x1 = from.getX() + from.getWidth() / 2f;
                float y1 = from.getY() + from.getHeight() / 2f;

                for (int toId : node.getNextNodeIds()) {
                    View to = nodeViews.get(toId);
                    float x2 = to.getX() + to.getWidth() / 2f;
                    float y2 = to.getY() + to.getHeight() / 2f;

                    lineView.addLine(x1, y1, x2, y2);
                }
            }

            StageNode current = stageMap.getCurrentNode();
            placePlayer(current);
        });
    }

    private void movePlayerTo(StageNode node, Runnable onEnd) {
        View target = nodeViews.get(node.getId());
        if (target == null) return;

        float centerX = target.getX() + target.getWidth() / 2f - playerIcon.getWidth() / 2f;
        float centerY = target.getY() + target.getHeight() / 2f - playerIcon.getHeight() / 2f;

        playerIcon.animate()
                .x(centerX)
                .y(centerY)
                .setDuration(500)
                .withEndAction(onEnd)
                .start();
    }

    private void placePlayer(StageNode node) {
        View target = nodeViews.get(node.getId());
        if (target == null) return;

        float centerX = target.getX() + target.getWidth() / 2f - playerIcon.getWidth() / 2f;
        float centerY = target.getY() + target.getHeight() / 2f - playerIcon.getHeight() / 2f;

        playerIcon.setX(centerX);
        playerIcon.setY(centerY);
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

