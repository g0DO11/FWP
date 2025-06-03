package com.inhyung.fwp;

import android.app.Dialog;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.GridLayout;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;

public class Battle extends AppCompatActivity {

    private Deck deck;
    private Hand hand;
    private DiscardPile discardPile;

    Enemy enemy = new Enemy("기사감자", 100, 2, 60);
    private Player player;
    private SoundManager soundManager;

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

                if (soundManager != null) {
                    soundManager.playSound(SoundManager.SOUND_CARD_SELECTED);
                }

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
                    cardSlots[index].animate().translationY(-50f).setDuration(150).start();
                }
                updateHandResult();
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


    @Override
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
        player = app.getPlayer(); // Player 인스턴스 초기화

        TextView health_point = findViewById(R.id.health_point);
        TextView wealth_point = findViewById(R.id.wealth_point);

        health_point.setText(String.valueOf(Player.getHp())); // player 인스턴스 사용
        wealth_point.setText(String.valueOf(Player.getMoney())); // player 인스턴스 사용

        // 게임 초기화
        deck = new Deck();
        hand = new Hand();
        discardPile = new DiscardPile();
        soundManager = SoundManager.getInstance(this);

        initCardViews(); //카드 뷰 초기화
        hand.drawFromDeck(deck, 8); //처음 패 미리 draw 해둠
        setupCardClickListenersWithAnimation(); //카드 애니메이션 설정
        updateHandDisplay();
        updateEnemyStatus();

        //문양 정렬 버튼
        FrameLayout sortBySuitBtn = findViewById(R.id.sortBySuit_btn);
        sortBySuitBtn.setOnClickListener(v -> {
            if (soundManager != null) soundManager.playSound(SoundManager.SOUND_BTN_CLICK);
            Collections.sort(hand.getCards(), (c1, c2) -> {
                if (c1.getSuitInt() == c2.getSuitInt()) {
                    return Integer.compare(c1.getNumber(), c2.getNumber()); // 같은 문양이면 숫자순
                } else {
                    return Integer.compare(c1.getSuitInt(), c2.getSuitInt()); // 문양 기준 정렬
                }
            });
            resetCardSelection();
            updateHandDisplay(); // 정렬 후 화면 갱신
            updateHandResult();
        });


        //숫자 정렬 버튼
        FrameLayout sortByNumberBtn = findViewById(R.id.sortByNum_btn);
        sortByNumberBtn.setOnClickListener(v -> {
            if (soundManager != null) soundManager.playSound(SoundManager.SOUND_BTN_CLICK);
            Collections.sort(hand.getCards(), (c1, c2) -> {
                if (c1.getNumber() == c2.getNumber()) {
                    return Integer.compare(c1.getSuitInt(), c2.getSuitInt()); // 같은 숫자면 문양순
                } else {
                    return Integer.compare(c1.getNumber(), c2.getNumber()); // 숫자 기준 정렬
                }
            });
            resetCardSelection();
            updateHandDisplay(); // 정렬 후 화면 갱신
            updateHandResult();
        });

        //버리기 버튼
        //선택됐는지 확인, 인덱스 저장 후 그 인덱스의 카드 discardpile로 보내고 그 자리에 새로운 카드 draw
        Button drawCardBtn = findViewById(R.id.drawCard_btn);
        drawCardBtn.setOnClickListener(v -> {
            if (soundManager != null) soundManager.playSound(SoundManager.SOUND_DRAW_CARD);
            newcard();
        });


        TextView family_damage_textbox = findViewById(R.id.family_damage_textbox);
        //공격하기 버튼
        Button useCardBtn = findViewById(R.id.useCard_btn);
        useCardBtn.setOnClickListener(v -> {
            if (soundManager != null) soundManager.playSound(SoundManager.SOUND_PLAYER_ATTACK);
            ArrayList<Card> selectedcards = new ArrayList<>();
            for (int i = 0; i < cardSlots.length; i++) {
                if (cardSelected[i]) {
                    selectedcards.add(hand.getCards().get(i));
                }
            }

            if (selectedcards.isEmpty()) {
                family_damage_textbox.setText("카드를 선택하세요!");
                return;
            }

            HandResult result = DmgEvaluator.evaluate(selectedcards);
            int damage = result.totalDamage;

            enemy.takeDamage(damage);

            // 턴 감소 및 적 공격
            enemy.decrementTurn(player); // 턴 감소 및 플레이어 공격 로직 포함

            // 새로운 카드 뽑기 및 UI 갱신 (선택된 카드만)
            newcard();

            // UI 업데이트
            updateEnemyStatus();
            updatePlayerStatus();

            // 게임 종료 조건 확인 (공격 후)
            checkGameEndCondition();
        });

        ImageButton usedBtn = findViewById(R.id.usedcards_imgbtn);
        usedBtn.setOnClickListener(v -> showUsedCardsDialog());

    }

    private void newcard(){
        for (int i = 0; i < cardSlots.length; i++) {
            if (cardSelected[i]) {
                // 1. 카드 버리기: discardPile로 보내기만 하고 손패에서 제거 X
                Card used = hand.getCards().get(i);
                discardPile.discard(used);

                // 2. 새로운 카드 뽑아서 해당 위치에 교체
                if (!deck.isEmpty()) {
                    Card newCard = deck.draw();
                    hand.getCards().set(i, newCard);  // remove 대신 set
                } else {
                    // 덱이 비었을 경우 처리 (예: 버림 패 섞기, 새로운 덱 생성 등)
                    // 현재는 덱이 비어있으면 카드 교체를 하지 않음
                    Toast.makeText(this, "덱에 더 이상 카드가 없습니다!", Toast.LENGTH_SHORT).show();
                }

                // 3. 선택 상태 초기화
                cardSelected[i] = false;
                cardSlots[i].setTranslationY(0); // 원위치로 이동
            }
        }
        // 4. UI 갱신
        updateHandDisplay();
    }

    private void updatePlayerStatus() {
        TextView health_point = findViewById(R.id.health_point);
        TextView wealth_point = findViewById(R.id.wealth_point);
        health_point.setText(String.valueOf(Player.getHp()));
        wealth_point.setText(String.valueOf(Player.getMoney()));

        // 플레이어 HP 체크
        checkGameEndCondition();
    }

    private void updateEnemyStatus() {
        TextView enemyhp = findViewById(R.id.enemyhp);
        TextView enemydmg = findViewById(R.id.enemydmg);
        TextView enemyturn = findViewById(R.id.turn_textbox);
        enemyhp.setText(String.valueOf(enemy.getHp()));
        enemydmg.setText(String.valueOf(enemy.getDmg()));
        enemyturn.setText(String.valueOf(enemy.getTurn()));

        // 적 HP 체크
        checkGameEndCondition();
    }

    private void checkGameEndCondition() {
        String resultMessage = null;
        if (enemy.getHp() <= 0) {
            resultMessage = "승리!";
        } else if (Player.getHp() <= 0) {
            resultMessage = "패배!";
        }

        if (resultMessage != null) {
            Intent intent = new Intent(Battle.this, GameEnd.class);
            intent.putExtra("GAME_RESULT", resultMessage);
            startActivity(intent);
            finish(); // Battle 액티비티 종료
        }
    }


    private void updateHandResult(){
        TextView family_damage_textbox = findViewById(R.id.family_damage_textbox);

        ArrayList<Card> selectedcards = new ArrayList<>();
        for (int i = 0; i < cardSlots.length; i++) {
            if (cardSelected[i]) {
                //0. 선택된 카드를 저장하는 배열 하나 추가
                selectedcards.add(hand.getCards().get(i));
            }
        }
        if (selectedcards.isEmpty()) {
            family_damage_textbox.setText("카드를 선택하세요!");
        } else {
            HandResult result = DmgEvaluator.evaluate(selectedcards);
            String resultText = String.format("조합: %s\n조합 데미지: %d\n카드 데미지: %d\n총합: %d",
                    result.combinationName,
                    result.combinationScore,
                    result.cardBaseDamage,
                    result.totalDamage);
            family_damage_textbox.setText(resultText);
        }
    }

    @Override
    public void onBackPressed(){
        new AlertDialog.Builder(this)
                .setTitle("게임 종료")
                .setMessage("메인 화면으로 돌아가시겠습니까?\n진행 중인 게임은 사라집니다.")
                .setPositiveButton("예", (dialog, which) -> {
                    // 메인 액티비티를 새 작업으로 시작하면서 모든 기존 액티비티 제거
                    Intent intent = new Intent(Battle.this, MainActivity.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                    startActivity(intent);
                })
                .setNegativeButton("아니오", (dialog, which) -> dialog.dismiss())
                .show();
    }

    private void showUsedCardsDialog() {
        Dialog dialog = new Dialog(this, android.R.style.Theme_Black_NoTitleBar_Fullscreen);
        dialog.setContentView(R.layout.dialog_used_cards);
        dialog.show();

        GridLayout gridLayout = dialog.findViewById(R.id.grid_cards);
        Button closeBtn = dialog.findViewById(R.id.close_button);
        closeBtn.setOnClickListener(v -> dialog.dismiss());

        // 카드 전체 생성 (덱 초기화 로직과 동일)
        List<Card> allCards = new ArrayList<>();
        for (int suit = 0; suit < 4; suit++) {
            for (int number = 1; number <= 13; number++) {
                allCards.add(new Card(number, suit));
            }
        }

        Set<Card> discarded = new HashSet<>(discardPile.getCards());

        for (Card card : allCards) {
            View cardView = getLayoutInflater().inflate(R.layout.item_card, null);

            TextView cardRank = cardView.findViewById(R.id.item_card_rank);
            ImageView cardMainImg = cardView.findViewById(R.id.item_card_main_img);
            ImageView cardSuitImg = cardView.findViewById(R.id.item_card_suit_img);
            ImageView xOverlay = cardView.findViewById(R.id.x_overlay);

            // 카드 정보 설정
            cardRank.setText(String.valueOf(card.getNumber()));
            cardSuitImg.setImageResource(getSuitDrawable(card.getSuit()));
            // 메인 이미지는 항상 eg_potato_72_72로 설정 (기존 activity_battle.xml과 동일)
            cardMainImg.setImageResource(R.drawable.eg_potato_72_72);


            if (discarded.contains(card)) {
                xOverlay.setVisibility(View.VISIBLE);
            } else {
                xOverlay.setVisibility(View.GONE);
            }

            gridLayout.addView(cardView);
        }
    }
}