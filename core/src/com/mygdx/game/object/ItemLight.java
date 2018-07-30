package com.mygdx.game.object;

import box2dLight.PointLight;
import box2dLight.RayHandler;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.mygdx.game.core.Assets;

import static com.mygdx.game.util.Constants.PPM;

public class ItemLight extends Actor {

    PointLight pointLight;
    BitmapFont font;
    Sprite sprite;

    public ItemLight(RayHandler handler, Color color) {
        super();
        font = Assets.instance.fontSmall;
        sprite = new Sprite(Assets.instance.getAsset(Assets.MONEY_ITEM));
        sprite.setSize(sprite.getWidth() / (2 * PPM), sprite.getHeight() / (2 * PPM));

        pointLight = new box2dLight.PointLight(handler, 1000, color, 3, 0, 0);
        setSize(1, 1);
        addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                super.clicked(event, x, y);
                System.out.println("fuckkk");
            }
        });
    }

    @Override
    public void setPosition(float x, float y) {
        super.setPosition(x, y);
        pointLight.setPosition(x + 0.5f, y + 0.5f);
        sprite.setPosition(x + 0.8f, y + 0.1f);
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        font.draw(batch, "200", getX() + 0.25f, getY() + 0.5f);
        sprite.draw(batch);
    }
}
