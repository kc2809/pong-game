package com.mygdx.game.object;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.ParticleEffectPool.PooledEffect;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.Pool.Poolable;
import com.mygdx.game.box2d.Box2dManager;
import com.mygdx.game.core.Assets;
import com.mygdx.game.effect.CircleEffectPool;
import com.mygdx.game.screens.MainGameScreen;

import static com.mygdx.game.util.Constants.BALL_PHYSIC;
import static com.mygdx.game.util.Constants.WORLD_PHYSIC;

public class Item1 extends ObjectBox2d implements Poolable {

    public PooledEffect effect;
    MainGameScreen screen;

    public Item1(World world, MainGameScreen screen) {
        super(world, Assets.instance.assetCircle.circle);
        this.screen = screen;
    }

//    private Level getLevelStage() {
//        return (Level) this.getStage();
//    }

    public void active() {
        Box2dManager.getInstance().addActiveBodyToQueue(body);
    }

    @Override
    void initComponent() {
//        effect = getTestEffect();
        effect = CircleEffectPool.getInstance().getCircleEffect();
    }

    @Override
    public void createPhysics() {
        BodyDef bodyDef = new BodyDef();
        bodyDef.type = BodyType.StaticBody;
        bodyDef.position.set(sprite.getX() + sprite.getWidth() / 2, sprite.getY() + sprite.getHeight() / 2);
        if (isWorldLock()) return;
        body = world.createBody(bodyDef);

        CircleShape shape = new CircleShape();
        shape.setRadius(sprite.getWidth() / 2);

        FixtureDef fixtureDef = new FixtureDef();
        fixtureDef.shape = shape;
        fixtureDef.isSensor = true;
        fixtureDef.filter.maskBits = BALL_PHYSIC;
        fixtureDef.filter.categoryBits = WORLD_PHYSIC;

        body.createFixture(fixtureDef);
        body.setUserData(this);
        shape.dispose();
    }

    @Override
    public void setPosition(float x, float y) {
        super.setPosition(x, y);
        sprite.setPosition(x + 0.5f - sprite.getWidth() / 2, y + 0.5f - sprite.getHeight() / 2);
        effect.setPosition(sprite.getX() + sprite.getWidth() / 2, sprite.getY() + sprite.getHeight() / 2);
        if (body != null && !world.isLocked())
            body.setTransform(sprite.getX() + sprite.getWidth() / 2, sprite.getY() + sprite.getHeight() / 2, 0);
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        super.draw(batch, parentAlpha);
        if(effect==null) return;
        effect.draw(batch, Gdx.graphics.getDeltaTime());
    }

    @Override
    public void act(float delta) {
        super.act(delta);
        effect.update(delta);
    }

    public void addBallsToGame() {
        ++this.screen.ballBeAddedNextRow;
        remove();
    }

    @Override
    public boolean remove() {
        screen.level.freeItem1(this);
        return super.remove();
    }

    @Override
    public void reset() {
        Box2dManager.getInstance().addInActiveBodyToQueue(body);
    }
}
