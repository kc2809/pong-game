package com.mygdx.game.sound;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;

import static com.mygdx.game.util.Constants.HIT_SOUND_PATH;

public class SoundManager {
    public static SoundManager instance = new SoundManager();
    Sound s;
    private long deadId;

    private SoundManager() {
        s = Gdx.audio.newSound(Gdx.files.internal(HIT_SOUND_PATH));
        deadId = s.play();
    }

    public void playHitSound() {
        s.stop(deadId);
        deadId = s.play();
    }

    public void dispose(){
        s.dispose();
    }
}
