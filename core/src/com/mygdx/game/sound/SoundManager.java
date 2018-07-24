package com.mygdx.game.sound;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;

import static com.mygdx.game.util.Constants.HIT_SOUND_PATH;
import static com.mygdx.game.util.Constants.LEVEL_SOUND_PATH;

public class SoundManager {
    public static SoundManager instance = new SoundManager();
    Sound hitSound;
    private long deadId;

    Sound levelSound;
    private long deadLevelId;

    private SoundManager() {
        hitSound = Gdx.audio.newSound(Gdx.files.internal(HIT_SOUND_PATH));
        deadId = hitSound.play();

        levelSound = Gdx.audio.newSound(Gdx.files.internal(LEVEL_SOUND_PATH));
        deadLevelId = levelSound.play();
    }

    public void playHitSound() {
        hitSound.stop(deadId);
        deadId = hitSound.play();
    }

    public void playLevelUpSound() {
        levelSound.stop(deadLevelId);
        deadLevelId = levelSound.play();
    }

    public void dispose(){
        hitSound.dispose();
        levelSound.dispose();
    }
}
