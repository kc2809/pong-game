package com.mygdx.game.object;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Actor;

import static com.mygdx.game.util.Constants.PPM;

public class Object2D extends Actor {

    protected Sprite sprite;

    public Object2D() {
    }

    protected void initTexture(TextureRegion region) {
        sprite = new Sprite(region);
        sprite.setSize(sprite.getWidth() * 2 / PPM, sprite.getHeight() * 2 / PPM);
    }


    @Override
    public void setPosition(float x, float y) {
        super.setPosition(x, y);
        sprite.setPosition(x, y);
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        sprite.draw(batch);
    }


}
