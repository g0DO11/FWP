package com.inhyung.fwp;

import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

public class Battle extends AppCompatActivity {

    private Deck deck;
    private Hand hand;
    private DiscardPile discardPile;


    // 카드 슬롯에 대응하는 뷰들
    private FrameLayout[] cardSlots = new FrameLayout[8];
    private TextView[] cardRanks = new TextView[8];
    private ImageView[] cardSuits = new ImageView[8];
    private boolean[] cardSelected = new boolean[8]; // 카드 선택 여부 추적


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

    private void updateHandDisplay() { //패 상태 업데이트
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

    // 카드 뷰를 XML에서 초기화
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


    private void setupCardClickListenersWithAnimation() {
        for (int i = 0; i < cardSlots.length; i++) {
            int index = i;
            cardSlots[i].setOnClickListener(v -> {

                // 현재 선택된 카드 수 세기
                int selectedCount = 0;
                for (boolean selected : cardSelected) {
                    if (selected) selectedCount++;
                }

                // 선택 해제
                if (cardSelected[index]) {
                    cardSelected[index] = false;
                    cardSlots[index].animate().translationY(0f).setDuration(150).start();
                }
                // 새로 선택하려고 하는 경우
                else {
                    if (selectedCount >= 5) {
                        // 선택 제한
                        Toast.makeText(this, "최대 5장까지만 선택할 수 있습니다.", Toast.LENGTH_SHORT).show();
                        return;
                    }
                    cardSelected[index] = true;
                    cardSlots[index].animate().translationY(-30f).setDuration(150).start();
                }
            });
        }
    }


    //모든 카드 선택 초기화 함수
    private void resetCardSelection() {
        for (int i = 0; i < cardSelected.length; i++) {
            cardSelected[i] = false;
            cardSlots[i].setTranslationY(0); // 원위치로 이동
        }
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

        initCardViews(); //카드 뷰 초기화
        hand.drawFromDeck(deck, 8); //처음 패 미리 draw 해둠
        setupCardClickListenersWithAnimation(); //카드 애니메이션 설정
        updateHandDisplay();

        //문양 정렬 버튼
        FrameLayout sortBySuitBtn = findViewById(R.id.sortBySuit_btn);
        sortBySuitBtn.setOnClickListener(v -> {
            Collections.sort(hand.getCards(), (c1, c2) -> {
                if (c1.getSuitInt() == c2.getSuitInt()) {
                    return Integer.compare(c1.getNumber(), c2.getNumber()); // 같은 문양이면 숫자순
                } else {
                    return Integer.compare(c1.getSuitInt(), c2.getSuitInt()); // 문양 기준 정렬
                }
            });
            resetCardSelection();
            updateHandDisplay(); // 정렬 후 화면 갱신
        });


        //숫자 정렬 버튼
        FrameLayout sortByNumberBtn = findViewById(R.id.sortByNum_btn);
        sortByNumberBtn.setOnClickListener(v -> {
            Collections.sort(hand.getCards(), (c1, c2) -> {
                if (c1.getNumber() == c2.getNumber()) {
                    return Integer.compare(c1.getSuitInt(), c2.getSuitInt()); // 같은 숫자면 문양순
                } else {
                    return Integer.compare(c1.getNumber(), c2.getNumber()); // 숫자 기준 정렬
                }
            });
            resetCardSelection();
            updateHandDisplay(); // 정렬 후 화면 갱신
        });

        //버리기 버튼
        //선택됐는지 확인, 인덱스 저장 후 그 인덱스의 카드 discardpile로 보내고 그 자리에 새로운 카드 draw
        Button drawCardBtn = findViewById(R.id.drawCard_btn);
        drawCardBtn.setOnClickListener(v -> {
            for (int i = 0; i < cardSlots.length; i++) {
                if (cardSelected[i]) {
                    // 1. 카드 버리기: discardPile로 보내기만 하고 손패에서 제거 X
                    Card used = hand.getCards().get(i);
                    discardPile.discard(used);

                    // 2. 새로운 카드 뽑아서 해당 위치에 교체
                    if (!deck.isEmpty()) {
                        Card newCard = deck.draw();
                        hand.getCards().set(i, newCard);  // remove 대신 set
                    }

                    // 3. 선택 상태 초기화
                    cardSelected[i] = false;
                    cardSlots[i].setTranslationY(0); // 원위치로 이동
                }
            }
            // 4. UI 갱신
            updateHandDisplay();

        });


        TextView family_damage_textbox = findViewById(R.id.family_damage_textbox);
        //공격하기 버튼
        Button useCardBtn = findViewById(R.id.useCard_btn);
        useCardBtn.setOnClickListener(v -> {
            ArrayList<Card> selectedcards = new ArrayList<>();
            for (int i = 0; i < cardSlots.length; i++) {
                if (cardSelected[i]) {
                    //0. 선택된 카드를 저장하는 배열 하나 추가
                    selectedcards.add(hand.getCards().get(i));
                }
            }
            if (selectedcards.isEmpty()) {
                family_damage_textbox.setText("카드를 선택하세요!");
            }else{
                //선택된 카드의 데미지 계산을 해서 보여줌, evaluate는 int니까 string으로 넘겨주고.
                family_damage_textbox.setText(String.valueOf(DmgEvaluator.evaluate(selectedcards)));
            }

        });
    }
}
