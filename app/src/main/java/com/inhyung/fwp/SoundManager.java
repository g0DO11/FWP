package com.inhyung.fwp;

import android.content.Context;
import android.media.AudioAttributes;
import android.media.SoundPool;
import android.os.Build;
import android.util.Log;
import android.util.SparseIntArray;

public class SoundManager {

    private static final String TAG = "SoundManager";
    private static SoundManager instance; // 싱글톤 인스턴스

    private SoundPool soundPool;
    private SparseIntArray soundMap; // 사운드 리소스 ID 저장
    private Context context;

    // 사운드 ID 상수 (R.raw 폴더의 파일 이름에 맞춰 수정)
    public static final int SOUND_BTN_CLICK = 0; // btnclick.mp3
    public static final int SOUND_CARD_SELECTED = 1; // cardselected.mp3
    public static final int SOUND_GENERIC_CARD = 2; // cardsounds.mp3 (용도 불분명, 필요시 변경)
    public static final int SOUND_DRAW_CARD = 3; // drawcard.mp3
    public static final int SOUND_ENEMY_ATTACK = 4; // enemyattack.mp3 (적의 공격 사운드)
    public static final int SOUND_PLAYER_ATTACK = 5; // playerattack.mp3 (플레이어가 공격할 때)


    private SoundManager(Context context) {
        // 메모리 누수 방지를 위해 Application Context 사용
        this.context = context.getApplicationContext();

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            AudioAttributes audioAttributes = new AudioAttributes.Builder()
                    .setUsage(AudioAttributes.USAGE_GAME)
                    .setContentType(AudioAttributes.CONTENT_TYPE_SONIFICATION)
                    .build();
            soundPool = new SoundPool.Builder()
                    .setMaxStreams(5) // 동시에 재생할 수 있는 최대 스트림 수
                    .setAudioAttributes(audioAttributes)
                    .build();
        } else {
            // API 레벨 21 미만 (구형 방식)
            soundPool = new SoundPool(5, android.media.AudioManager.STREAM_MUSIC, 0);
        }

        soundMap = new SparseIntArray();
        loadSounds();
    }

    // 싱글톤 인스턴스를 얻는 메서드
    public static synchronized SoundManager getInstance(Context context) {
        if (instance == null) {
            instance = new SoundManager(context);
        }
        return instance;
    }

    private void loadSounds() {
        // res/raw 폴더의 실제 파일 이름과 매핑
        soundMap.put(SOUND_BTN_CLICK, soundPool.load(context, R.raw.btnclick, 1));
        soundMap.put(SOUND_CARD_SELECTED, soundPool.load(context, R.raw.cardselected, 1));
        soundMap.put(SOUND_GENERIC_CARD, soundPool.load(context, R.raw.cardsounds, 1));
        soundMap.put(SOUND_DRAW_CARD, soundPool.load(context, R.raw.drawcard, 1));
        soundMap.put(SOUND_ENEMY_ATTACK, soundPool.load(context, R.raw.enemyattack, 1));
        soundMap.put(SOUND_PLAYER_ATTACK, soundPool.load(context, R.raw.playerattack, 1));

        // 사운드 로드 완료 리스너 (선택 사항, 디버깅 용도)
        soundPool.setOnLoadCompleteListener((soundPool, sampleId, status) -> {
            if (status == 0) {
                Log.d(TAG, "Sound loaded successfully: " + sampleId);
            } else {
                Log.e(TAG, "Failed to load sound: " + sampleId + " with status: " + status);
            }
        });
    }

    /**
     * 특정 사운드를 재생합니다.
     * @param soundConstant SoundManager에 정의된 사운드 상수 (예: SoundManager.SOUND_BUTTON_CLICK)
     */
    public void playSound(int soundConstant) {
        // soundConstant에 해당하는 soundId를 찾아 재생
        int soundId = soundMap.get(soundConstant, -1); // 못 찾으면 -1 반환
        if (soundId != -1 && soundPool != null) {
            // play(soundID, leftVolume, rightVolume, priority, loop, rate)
            soundPool.play(soundId, 1.0f, 1.0f, 1, 0, 1.0f);
            Log.d(TAG, "Playing sound: " + soundConstant);
        } else {
            Log.e(TAG, "Attempted to play unloaded or invalid sound ID: " + soundConstant);
        }
    }

    /**
     * SoundPool 리소스를 해제합니다. 앱이 완전히 종료될 때 호출해야 합니다.
     */
    public void release() {
        if (soundPool != null) {
            soundPool.release();
            soundPool = null;
            instance = null; // 싱글톤 인스턴스 초기화
            soundMap.clear();
            Log.d(TAG, "SoundPool released.");
        }
    }
}