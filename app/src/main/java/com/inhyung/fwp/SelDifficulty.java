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

        easy.setOnClickListener(v -> {
            GameApplication app = (GameApplication) getApplication();
            app.initPlayer(GameApplication.GameDifficulty.EASY);
            Intent intent = new Intent(SelDifficulty.this, Battle.class);
            startActivity(intent);
        });
    }


}