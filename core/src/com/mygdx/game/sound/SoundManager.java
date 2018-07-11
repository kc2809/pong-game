package com.mygdx.game.sound;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static com.mygdx.game.util.Constants.HIT_SOUND_PATH;

public class SoundManager {
    public static SoundManager instance = new SoundManager();
    List<Music> sounds;

    private SoundManager() {
        sounds = new ArrayList<Music>();
        init();
    }

    public void playHitSound() {
        Optional<Music> optionalMusic = sounds.stream().filter(sound -> !sound.isPlaying()).findAny();

        if(optionalMusic.isPresent()){
            optionalMusic.get().play();
        } else{
            addNewSound();
        }
    }

    private void init() {
        for (int i = 0; i < 10; ++i) {
            addNewSound();
        }
    }

    private void addNewSound() {
        sounds.add(Gdx.audio.newMusic(Gdx.files.internal(HIT_SOUND_PATH)));
    }

}
