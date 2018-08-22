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
import com.mygdx.game.screens.StoreScreen;
import com.mygdx.game.storage.MyPreference;

import static com.mygdx.game.util.Constants.PPM;

public class ItemLight extends Actor {

    PointLight pointLight;
    BitmapFont font;
    Sprite sprite;
    Color color;

    boolean isSold;
    StoreScreen storeScreen;

    public ItemLight(final StoreScreen storeScreen, RayHandler handler, final Color color) {
        super();
        this.storeScreen = storeScreen;
        this.color = color;
        isSold = MyPreference.getInstance().isColorSold(color);
        font = Assets.instance.fontMedium;
        sprite = new Sprite(Assets.instance.getAsset(Assets.MONEY_ITEM));
        sprite.setSize(sprite.getWidth() / (2 * PPM), sprite.getHeight() / (2 * PPM));

        pointLight = new box2dLight.PointLight(handler, 1000, color, 2, 0, 0);
        setSize(1, 1);
        addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                super.clicked(event, x, y);
                if (!isSold)
                    updateLabel();
                else
                    storeScreen.setPointLightColor(color);
            }
        });
    }

    private void updateLabel() {
        if (!storeScreen.checkValidMoney()) return;
        storeScreen.updateLabel(color);
        isSold = true;
    }

    @Override
    public void setPosition(float x, float y) {
        super.setPosition(x, y);
        pointLight.setPosition(x + 0.5f, y + 0.5f);
        sprite.setPosition(x + 1.1f, y + 0.05f);
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
      if(!isSold){
          font.draw(batch, "200", getX() + 0.15f, getY() + 0.5f);
          sprite.draw(batch);
      } else {
          font.draw(batch, "active", getX() + 0.15f, getY() + 0.5f);
      }
    }
}
