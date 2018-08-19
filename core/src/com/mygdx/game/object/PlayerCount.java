package com.mygdx.game.object;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.mygdx.game.core.Assets;
import com.mygdx.game.screens.MainGameScreen;
import com.mygdx.game.util.Constants;

import static com.mygdx.game.util.Constants.PPM;

public class PlayerCount extends Actor {

    BitmapFont font;

    MainGameScreen screen;

    Vector2 positionToDraw;

    Sprite sprite;

    public PlayerCount(MainGameScreen screen) {
        this.screen = screen;
        font = Assets.instance.fontSmall;
        initSprite();
        setPositionToDraw();
    }

    private void initSprite(){
        sprite = new Sprite(Assets.instance.getAsset(Assets.ARROW_ICON));
        sprite.setSize(sprite.getWidth() * 2 / PPM, sprite.getHeight() * 2 / PPM);
        sprite.setOrigin(sprite.getWidth()/2,0);
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        font.draw(batch, "X" + screen.getRemainBall(), positionToDraw.x - Constants.BALL_WIDTH, positionToDraw.y + 1.0f);
        sprite.setPosition(positionToDraw.x - sprite.getWidth()/2 + Constants.BALL_WIDTH/2,
                positionToDraw.y +  Constants.BALL_HEIGHT/2);
        sprite.draw(batch);
    }

    public void setPositionToDraw() {
        positionToDraw = screen.getPlayer().positionToFire;
    }

    public void rotateArrow(float angle){
        sprite.rotate(angle - 90- sprite.getRotation());
    }
}
