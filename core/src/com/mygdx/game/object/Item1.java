package com.mygdx.game.object;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.ParticleEffect;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.World;
import com.mygdx.game.core.Assets;

import static com.mygdx.game.util.Constants.BALL_PHYSIC;
import static com.mygdx.game.util.Constants.WORLD_PHYSIC;

public class Item1 extends ObjectBox2d {

    public ParticleEffect effect;

    public Item1(World world, float x, float y) {
        super(world, Assets.instance.circle, x, y);
    }

    @Override
    public void initObjects(Texture texture) {
        super.initObjects(texture);
        effect = getTestEffect();
    }

    @Override
    public void createPhysics() {
        if (isWorldLock()) return;
        BodyDef bodyDef = new BodyDef();
        bodyDef.type = BodyType.StaticBody;
        bodyDef.position.set(sprite.getX() + sprite.getWidth() / 2, sprite.getY() + sprite.getHeight() / 2);
        body = world.createBody(bodyDef);

        CircleShape shape = new CircleShape();
        shape.setRadius(sprite.getWidth() / 2);

        FixtureDef fixtureDef = new FixtureDef();
        fixtureDef.shape = shape;
        fixtureDef.isSensor = true;
        fixtureDef.filter.maskBits = BALL_PHYSIC;
        fixtureDef.filter.categoryBits = WORLD_PHYSIC;

        body.createFixture(fixtureDef);
        shape.dispose();
        body.setUserData(this);
    }

    @Override
    public void setPosition(float x, float y) {
        super.setPosition(x, y);
        sprite.setPosition(x + 0.5f - sprite.getWidth() / 2, y + 0.5f - sprite.getHeight() / 2);
        effect.setPosition(sprite.getX() + sprite.getWidth() / 2, sprite.getY() + sprite.getHeight() / 2);
        if (body != null)
            body.setTransform(sprite.getX() + sprite.getWidth() / 2, sprite.getY() + sprite.getHeight() / 2, 0);
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        super.draw(batch, parentAlpha);
        effect.draw(batch);
    }

    @Override
    public void act(float delta) {
        super.act(delta);
        effect.update(delta);
        if (effect.isComplete()) {
            effect.start();
        }
    }

    private ParticleEffect getTestEffect() {
        ParticleEffect pe = new ParticleEffect();
        pe.load(Gdx.files.internal("effect3.party"), Gdx.files.internal(""));
        pe.getEmitters().first().setPosition(0, 0);
        pe.scaleEffect(1.0f / 200f);
        pe.start();
        return pe;
    }

    @Override
    public boolean remove() {
        Level levelStage = (Level) getStage();
        levelStage.increaseBallWillBeAddNextStep();
        sprite = null;
        effect = null;
        return super.remove();
    }
}
