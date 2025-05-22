# FWP

그 카드 선택 최대 5개만 되게 바꿔야함!~@~


약간 특이성 주기 위해서 뭐 물이랑 풀이랑 만나면 가산점?
이런식으로 싸이감만의 족보를 만드는것도 나쁘지 않을듯

*버리기 버튼 문제
    //버리기 버튼
    //선택됐는지 확인, 인덱스 저장 후 그 인덱스의 카드 discardpile로 보내고 그 자리에 새로운 카드 draw
    Button drawCardBtn = findViewById(R.id.drawCard_btn);
    drawCardBtn.setOnClickListener(v -> {
    for (int i = 0; i < cardSlots.length; i++) {
    if (cardSelected[i]) {
    // 1. 카드 버리기
    Card used = hand.getCards().remove(i);
    discardPile.discard(used);
    
                        // 2. 새로운 카드 뽑기
                        hand.drawFromDeck(deck, 1);
    
                        // 3. 선택 상태 초기화
                        cardSelected[i] = false;
                        cardSlots[i].setTranslationY(0); // 원위치로 이동
    
                        // 4. 화면 갱신
                        updateHandDisplay();
                    }
                }
            });
=> 기존에 이렇게 하면 다른것들이 바뀌는 문제있었는데 그게 hand.getCards().remove(i); 를 하게 되면, 리스트에서 카드가 삭제되며 한 칸씩 앞으로 당겨져서 생긴 문제라 remove 말고 set으로 해겷함