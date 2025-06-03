package com.inhyung.fwp;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class GameEnd extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_game_end);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        TextView battleResultTextView = findViewById(R.id.battleresult);
        Button NextButton = findViewById(R.id.next_btn);

        // Intent로부터 결과 메시지 받기
        Intent intent = getIntent();
        if (intent != null && intent.hasExtra("GAME_RESULT")) {
            String result = intent.getStringExtra("GAME_RESULT");
            battleResultTextView.setText(result);
        }

        NextButton.setOnClickListener(v -> {
            Intent mainIntent = new Intent(GameEnd.this, StageMapActivity.class);
            mainIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(mainIntent);
            finish();
        });
    }
}