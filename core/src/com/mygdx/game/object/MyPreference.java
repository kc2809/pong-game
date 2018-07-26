package com.mygdx.game.object;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;


public class MyPreference {
    public static MyPreference instance;

    private Preferences preferences;

    private MyPreference() {
        preferences = Gdx.app.getPreferences("myPrefs");
        loadPreferences();
    }

    public static MyPreference getInstance() {
        if (instance == null) {
            instance = new MyPreference();
        }
        return instance;
    }

    private void generatePreferences() {
        preferences.clear();
        preferences.putBoolean("notFirstLaunch", true).flush();
        preferences.putInteger("score", 0).flush();
        preferences.putInteger("money", 0).flush();
        preferences.putBoolean("isSoundOn", true).flush();
    }

    private void loadPreferences() {
        if (!preferences.getBoolean("notFirstLaunch")) {
            generatePreferences();
        }
    }

    public int getMoney() {
        return preferences.getInteger("money");
    }

    public void setMoney(int money) {
        preferences.putInteger("money", money).flush();
    }

    public boolean isSoundOn() {
        return preferences.getBoolean("isSoundOn");
    }

    public void toggleSound() {
        preferences.putBoolean("isSoundOn", !preferences.getBoolean("isSoundOn")).flush();
    }
}
