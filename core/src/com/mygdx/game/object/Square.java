package com.mygdx.game.object;

import box2dLight.RayHandler;
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

    BitmapFont font;
    GlyphLayout layout;
    StringBuilder valueBuilder;
    Color color;

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
        this.color = color;
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
//        sprite.setSize(this.getScaleX(), this.getScaleY());
    }

    @Override
    public boolean remove() {
        screen.increaseScore();
        screen.level.freeSquare(this);
        return super.remove();
    }

    public void descreaseValue() {
        if(screen.power ==1)
            value--;
        else
            value-=2;
        sprite.setColor(Color.RED);
        setValueBuilder();
        if (value > 0) {
            return;
        }
        screen.setEffectAtPosition(body.getPosition(), color);
        this.remove();
    }

    public void resetColor() {
        sprite.setColor(color);
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
