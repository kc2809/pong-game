package com.mygdx.game.object;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.mygdx.game.core.Assets;
import com.mygdx.game.screens.MainGameScreen;

public class PlayerCount extends Actor {

    BitmapFont font;

    MainGameScreen screen;

    Vector2 positionToDraw;

    public PlayerCount(MainGameScreen screen) {
        this.screen = screen;
        font = Assets.instance.fontSmall;
        setPositionToDraw();
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        font.draw(batch, "X" + screen.getRemainBall(), positionToDraw.x, positionToDraw.y + 1.0f);
    }

    public void setPositionToDraw() {
        positionToDraw = screen.getPlayer().positionToFire;
    }
}
