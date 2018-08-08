package com.mygdx.game.storage;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;
import com.badlogic.gdx.graphics.Color;
import com.mygdx.game.util.Constants;


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
        preferences.putInteger("highestScore", 0).flush();
        generateBallItems();
        preferences.putInteger("defaultColor", Color.rgba8888(Constants.defaultColor)).flush();
    }

    private void generateBallItems() {
        for (int i = 0; i < Constants.colors.length; ++i) {
            preferences.putBoolean(Constants.colors[i].toString(), false).flush();
        }
        preferences.putBoolean(Constants.defaultColor.toString(), true).flush();

    }

    private void loadPreferences() {
        if (!preferences.getBoolean("notFirstLaunch")) {
            generatePreferences();
        }
    }

    public Color getCurrentColor() {
        return new Color(preferences.getInteger("defaultColor"));
    }

    public void setCurrentColor(Color color) {
        preferences.putInteger("defaultColor", Color.rgba8888(color)).flush();
    }

    public boolean isColorSold(Color color) {
        return preferences.getBoolean(color.toString());
    }

    public void soldColor(Color color) {
        preferences.putBoolean(color.toString(), true).flush();
    }

    public int getHighestScore() {
        return preferences.getInteger("highestScore");
    }

    public void setHighestScore(int score) {
        preferences.putInteger("highestScore", score).flush();
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
