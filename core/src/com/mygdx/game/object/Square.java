package com.mygdx.game.object;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;
import com.mygdx.game.core.Assets;
import com.mygdx.game.screens.MainGameScreen;

import java.util.Random;

import static com.mygdx.game.util.Constants.BALL_PHYSIC;
import static com.mygdx.game.util.Constants.WORLD_PHYSIC;

public class Square extends ObjectBox2d {

    MainGameScreen screen;

    int value;
    Random r = new Random();

    BitmapFont font;

    private float getColorRandomValue(){
        return r.nextInt(255) / 255.0f;
    }

    public Square(MainGameScreen screen, World world, float x, float y) {
        super(world, Assets.instance.square, x, y);
        this.screen = screen;
        value = r.nextInt(screen.currentLevel) +1;
        font = Assets.instance.font;
        sprite.setColor(getColorRandomValue(), getColorRandomValue(), getColorRandomValue(), 1);

    }

    @Override
    public void createPhysics() {
        if (isWorldLock()) return;
        BodyDef bodyDef = new BodyDef();
        bodyDef.type = BodyType.StaticBody;
        bodyDef.position.set(sprite.getX() + sprite.getWidth() / 2, sprite.getY() + sprite.getHeight() / 2);

        body = world.createBody(bodyDef);

        PolygonShape shape = new PolygonShape();
        //  shape.setAsBox(sprite.getWidth()/2 , sprite.getHeight() /2 );
        shape.setAsBox(sprite.getWidth() / 2, sprite.getHeight() / 2);

        FixtureDef fixtureDef = new FixtureDef();
        fixtureDef.shape = shape;
        fixtureDef.density = 0.1f;
        fixtureDef.filter.categoryBits = WORLD_PHYSIC;
        fixtureDef.filter.maskBits = BALL_PHYSIC;

        body.createFixture(fixtureDef);
        shape.dispose();
        body.setUserData(this);
    }


    @Override
    public void draw(Batch batch, float parentAlpha) {
        super.draw(batch, parentAlpha);
        font.draw(batch, value+"", sprite.getX(), sprite.getY() + sprite.getHeight());
    }

    @Override
    public boolean remove() {
        screen.uiObject.increaseScore();
        return super.remove();
    }

    public void descreaseValue() {
        if (--value != 0) return;
        screen.setEffectAtPosition(body.getPosition());
        this.remove();
    }
}
