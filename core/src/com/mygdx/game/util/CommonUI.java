package com.mygdx.game.util;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Label.LabelStyle;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.mygdx.game.core.Assets;

import static com.mygdx.game.util.Constants.PPM;

public class CommonUI {

    public static CommonUI instance;

    private CommonUI() {
    }

    public static CommonUI getInstance() {
        if (instance == null) instance = new CommonUI();
        return instance;
    }

    public ImageButton createImageButton(TextureRegion up, TextureRegion down, TextureRegion checked, ClickListener listener) {
        Drawable drawableUp = up != null ? new TextureRegionDrawable(up) : null;
        Drawable drawableDown = down != null ? new TextureRegionDrawable(down) : null;
        Drawable drawableChecked = checked != null ? new TextureRegionDrawable(checked) : null;

        ImageButton imageBtn = new ImageButton(drawableUp, drawableDown, drawableChecked);
        if (listener != null) imageBtn.addListener(listener);
        return imageBtn;
    }

    public Group createBox(float x, float y, LabelStyle style, String title, Color color, ClickListener listener) {
        ImageButton box = createImageButton(Assets.instance.getAsset(Assets.BOX), null, null, null);
        box.setSize(box.getWidth() * 10 / PPM, box.getHeight() * 10 / PPM);
        box.setPosition(x - box.getWidth() / 2, y);
        box.getImage().setColor(color);

        Label label = new Label(title, style);
        label.setPosition(-label.getWidth() / 2, y);

        Group group = new Group();
        group.addActor(box);
        group.addActor(label);
        group.addListener(listener);
        return group;
    }
}
