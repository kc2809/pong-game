package com.mygdx.game.core;

import com.badlogic.gdx.graphics.g2d.GlyphLayout;

public class Text {
    StringBuilder text;
    public float x;
    public float y;

    public Text() {
        text = new StringBuilder();
    }

    public void setPosition(float x, float y) {
        this.x = x;
        this.y = y;
    }

    public void setText(int value) {
        text.setLength(0);
        text.append(value);
    }

    public StringBuilder getText() {
        return text;
    }

    public void setText(StringBuilder text) {
        this.text = text;
    }

    public float getX() {
        return x;
    }

    public void setX(float x) {
        this.x = x;
    }

    public float getY() {
        return y;
    }

    public void setY(float y) {
        this.y = y;
    }
}
