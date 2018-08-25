package com.mygdx.game.object;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.scenes.scene2d.Action;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.actions.RepeatAction;
import com.mygdx.game.core.Assets;

import static com.mygdx.game.util.Constants.PPM;

public class PointingHandTutorial extends Actor {

    private Sprite hand;
    private BitmapFont font;

    public PointingHandTutorial() {
        hand = new Sprite(Assets.instance.getAsset(Assets.BALL));
        hand.setSize(hand.getWidth() / PPM, hand.getHeight() / PPM);

        font = Assets.instance.tutorialFont;
        this.setPosition(hand.getWidth() / 2, 0);
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        super.draw(batch, parentAlpha);
        hand.draw(batch);
        font.draw(batch, "Slide dow to shoot", -2.0f, -2.5f);
    }

    @Override
    public void act(float delta) {
        super.act(delta);
        hand.setPosition(getX(), getY());
    }

    public void addSlideAction() {
        Action slideDown = Actions.moveTo(0, -2.0f, 1.0f);
        Action slideUp = Actions.moveTo(0, 0, 0.2f);
        Action standBy = Actions.moveBy(0, 0, 0.4f);
        RepeatAction action = new RepeatAction();
        action.setAction( Actions.sequence(slideDown, slideUp, standBy));
        action.setCount(RepeatAction.FOREVER);
        this.addAction(action);
    }

    @Override
    public boolean remove() {
        this.clearActions();
        return super.remove();
    }
}
