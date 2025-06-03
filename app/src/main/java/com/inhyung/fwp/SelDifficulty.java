package com.inhyung.fwp;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.widget.FrameLayout;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class SelDifficulty extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_sel_difficulty);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        FrameLayout easy = findViewById(R.id.easy);
        FrameLayout normal = findViewById(R.id.normal);
        FrameLayout hard = findViewById(R.id.hard);

        easy.setOnClickListener(v -> { //쉬움 버튼을 눌렀을 때 게임이 시작됨
            GameApplication app = (GameApplication) getApplication();
            app.initPlayer(GameApplication.GameDifficulty.EASY); //플레이어 객체 새로 생성(리셋할 필요 X),application에 난이도 기록
            app.initStageMap(GameApplication.GameDifficulty.EASY);
            Intent intent = new Intent(SelDifficulty.this, StageMapActivity.class);
            startActivity(intent);
        });
    }


}