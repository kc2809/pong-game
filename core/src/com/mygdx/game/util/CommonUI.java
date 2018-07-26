package com.mygdx.game.util;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;

public class CommonUI {

    public static CommonUI instance;

    private CommonUI() {
    }

    public static CommonUI getInstance() {
        if (instance == null) instance = new CommonUI();
        return instance;
    }

    public Button createImageButton(TextureRegion up, TextureRegion down, TextureRegion checked, ClickListener listener) {
        Drawable drawableUp = up != null ? new TextureRegionDrawable(up) : null;
        Drawable drawableDown = down != null ? new TextureRegionDrawable(down) : null;
        Drawable drawableChecked = checked != null ? new TextureRegionDrawable(checked) : null;

        Button imageBtn = new ImageButton(drawableUp, drawableDown, drawableChecked);
        imageBtn.addListener(listener);
        return imageBtn;
    }
}
