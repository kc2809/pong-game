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
        hand = new Sprite(Assets.instance.getAsset(Assets.HAND_ICON));
        hand.setSize(hand.getWidth() * 2 / PPM, hand.getHeight() * 2 / PPM);

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
        this.setVisible(true);
        Action slideDown = Actions.moveTo(-hand.getWidth() / 2, -2.0f, 1.0f);
        Action slideUp = Actions.moveTo(-hand.getWidth() / 2, 0, 0.1f);
        Action standBy = Actions.moveBy(0, 0, 0.8f);
        RepeatAction action = new RepeatAction();
        action.setAction( Actions.sequence(slideDown, slideUp, standBy));
        action.setCount(RepeatAction.FOREVER);
        this.addAction(action);
    }

    public void reset(){
        this.setVisible(false);
        this.clearActions();
        this.setPosition(-hand.getWidth() / 2, 0);
    }
}
