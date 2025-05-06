package com.inhyung.fwp;

import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.util.ArrayList;

public class Battle extends AppCompatActivity {

    private Deck deck;
    private Hand hand;
    private DiscardPile discardPile;

    // 카드 슬롯에 대응하는 뷰들
    private FrameLayout[] cardSlots = new FrameLayout[8];
    private TextView[] cardRanks = new TextView[8];
    private ImageView[] cardSuits = new ImageView[8];

    // 무늬에 따른 이미지 리소스 매핑
    private int getSuitDrawable(String suit) {
        switch (suit) {
            case "Heart": return R.drawable.suit_heart;
            case "Droplet": return R.drawable.suit_water;
            case "Leaf": return R.drawable.suit_leaf;
            case "Wind": return R.drawable.suit_wind1;
            default: return R.drawable.circle_gray;
        }
    }

    private void updateHandDisplay() {
        ArrayList<Card> cards = hand.getCards();
        for (int i = 0; i < cardSlots.length; i++) {
            if (i < cards.size()) {
                Card card = cards.get(i);
                cardSlots[i].setVisibility(View.VISIBLE);
                cardRanks[i].setText(String.valueOf(card.getNumber()));
                cardSuits[i].setImageResource(getSuitDrawable(card.getSuit()));
            } else {
                cardSlots[i].setVisibility(View.INVISIBLE);
            }
        }
    }

    private void initCardViews() {
        cardSlots[0] = findViewById(R.id.card_inhand_1);
        cardSlots[1] = findViewById(R.id.card_inhand_2);
        cardSlots[2] = findViewById(R.id.card_inhand_3);
        cardSlots[3] = findViewById(R.id.card_inhand_4);
        cardSlots[4] = findViewById(R.id.card_inhand_5);
        cardSlots[5] = findViewById(R.id.card_inhand_6);
        cardSlots[6] = findViewById(R.id.card_inhand_7);
        cardSlots[7] = findViewById(R.id.card_inhand_8);

        cardRanks[0] = findViewById(R.id.card_inhnad1_rank);
        cardRanks[1] = findViewById(R.id.card_inhnad2_rank);
        cardRanks[2] = findViewById(R.id.card_inhnad3_rank);
        cardRanks[3] = findViewById(R.id.card_inhnad4_rank);
        cardRanks[4] = findViewById(R.id.card_inhnad5_rank);
        cardRanks[5] = findViewById(R.id.card_inhnad6_rank);
        cardRanks[6] = findViewById(R.id.card_inhnad7_rank);
        cardRanks[7] = findViewById(R.id.card_inhnad8_rank);

        cardSuits[0] = findViewById(R.id.card_inhnad1_suit);
        cardSuits[1] = findViewById(R.id.card_inhnad2_suit);
        cardSuits[2] = findViewById(R.id.card_inhnad3_suit);
        cardSuits[3] = findViewById(R.id.card_inhnad4_suit);
        cardSuits[4] = findViewById(R.id.card_inhnad5_suit);
        cardSuits[5] = findViewById(R.id.card_inhnad6_suit);
        cardSuits[6] = findViewById(R.id.card_inhnad7_suit);
        cardSuits[7] = findViewById(R.id.card_inhnad8_suit);
    }

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_battle);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.battle), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        GameApplication app = (GameApplication) getApplication();
        Player player = app.getPlayer();

        TextView health_point = findViewById(R.id.health_point);
        TextView wealth_point = findViewById(R.id.wealth_point);

        health_point.setText(String.valueOf(player.getHp()));
        wealth_point.setText(String.valueOf(player.getMoney()));

        // 게임 초기화
        deck = new Deck();
        hand = new Hand();
        discardPile = new DiscardPile();

        initCardViews();
        updateHandDisplay(); // 처음에는 아무것도 안보이게

        Button drawCardBtn = findViewById(R.id.drawCard_btn);
        drawCardBtn.setOnClickListener(v -> {
            if (!deck.isEmpty() && hand.getCards().size() < 8) {
                hand.drawFromDeck(deck, 1);
                updateHandDisplay();
            }
        });

        Button useCardBtn = findViewById(R.id.useCard_btn);
        useCardBtn.setOnClickListener(v -> {
            if (!hand.getCards().isEmpty()) {
                // 첫 번째 카드를 버리는 예시
                Card used = hand.getCards().remove(0);
                discardPile.discard(used);
                updateHandDisplay();
            }
        });
    }
}
