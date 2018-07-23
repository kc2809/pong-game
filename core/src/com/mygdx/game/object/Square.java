package com.mygdx.game.object;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.utils.Pool.Poolable;
import com.mygdx.game.box2d.Box2dManager;
import com.mygdx.game.core.Assets;
import com.mygdx.game.screens.MainGameScreen;

import java.util.Random;

import static com.mygdx.game.util.Constants.BALL_PHYSIC;
import static com.mygdx.game.util.Constants.WORLD_PHYSIC;

public class Square extends ObjectBox2d implements Poolable {

    MainGameScreen screen;

    int value;
    Random r = new Random();

    BitmapFont font;
    GlyphLayout layout;
    StringBuilder valueBuilder;

    public Square(World world, MainGameScreen screen) {
        super(world, Assets.instance.assetSquare.square);
        this.screen = screen;
        font = Assets.instance.fontSmall;
        valueBuilder = new StringBuilder();
        layout = new GlyphLayout();
    }

    public Square setValue(int val) {
        value = val;
        setValueBuilder();
        return this;
    }

    public void setColor(Color color) {
        sprite.setColor(color);
    }

    @Override
    void initComponent() {

    }


    @Override
    public void createPhysics() {
        BodyDef bodyDef = new BodyDef();
        bodyDef.type = BodyType.StaticBody;
        bodyDef.position.set(sprite.getX() + sprite.getWidth() / 2, sprite.getY() + sprite.getHeight() / 2);
        if (isWorldLock()) return;
        body = world.createBody(bodyDef);

        PolygonShape shape = new PolygonShape();
        shape.setAsBox(sprite.getWidth() / 2, sprite.getHeight() / 2);
        shape.setAsBox(sprite.getWidth() / 2, sprite.getHeight() / 2);

        FixtureDef fixtureDef = new FixtureDef();
        fixtureDef.shape = shape;
        fixtureDef.density = 0.1f;
        fixtureDef.filter.categoryBits = WORLD_PHYSIC;
        fixtureDef.filter.maskBits = BALL_PHYSIC;

        body.createFixture(fixtureDef);
        body.setUserData(this);
        shape.dispose();
        reset();
    }

    public Square active() {
        Box2dManager.getInstance().addActiveBodyToQueue(body);
        return this;
    }
    @Override
    public void draw(Batch batch, float parentAlpha) {
        super.draw(batch, parentAlpha);
        font.draw(batch, valueBuilder, getCenterPostionX() - layout.width / 2, getCenterPostionY() + layout.height / 2);
    }

    @Override
    public void act(float delta) {
        super.act(delta);
        sprite.setSize(this.getScaleX(), this.getScaleY());
    }

    @Override
    public boolean remove() {
        screen.increaseScore();
        screen.level.freeSquare(this);
        return super.remove();
    }

    private Level getLevelStage() {
        return (Level) getStage();
    }

    public void descreaseValue() {
        value--;
        setValueBuilder();
        if (value != 0) {
//            addCollisionEffect();
            return;
        }
        screen.setEffectAtPosition(body.getPosition(), sprite.getColor());
        this.remove();
    }

    public void addCollisionEffect(){
        this.addAction(Actions.sequence(Actions.scaleTo(1.1f,1.1f,0.005f), Actions.scaleTo(1.0f,1.0f,0.005f)));
    }

    private void setValueBuilder() {
        valueBuilder.setLength(0);
        valueBuilder.append(value);
        layout.setText(font, valueBuilder);
    }

    @Override
    public void reset() {
        Box2dManager.getInstance().addInActiveBodyToQueue(body);
    }


}
