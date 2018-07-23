package com.mygdx.game.object;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.Pool.Poolable;
import com.mygdx.game.box2d.Box2dManager;
import com.mygdx.game.core.Assets;
import com.mygdx.game.screens.MainGameScreen;

import static com.mygdx.game.util.Constants.BALL_PHYSIC;
import static com.mygdx.game.util.Constants.WORLD_PHYSIC;

public class MoneyItem extends ObjectBox2d implements Poolable {

    MainGameScreen screen;

    public MoneyItem(World world, MainGameScreen screen) {
        super(world, Assets.instance.assetMoenyItem.moneyItem);
        this.screen = screen;
    }

    @Override
    void initComponent() {
    }

    private Level getLevelStage() {
        return (Level) this.getStage();
    }

    @Override
    public void createPhysics() {
        BodyDef bodyDef = new BodyDef();
        bodyDef.type = BodyType.StaticBody;
        bodyDef.position.set(sprite.getX() + sprite.getWidth() / 2, sprite.getY() + sprite.getHeight() / 2);
        if (isWorldLock()) return;
        body = world.createBody(bodyDef);
        isCreatePhysics = false;

        CircleShape shape = new CircleShape();
        shape.setRadius(sprite.getWidth() / 2);

        FixtureDef fixtureDef = new FixtureDef();
        fixtureDef.shape = shape;
        fixtureDef.isSensor = true;
        fixtureDef.filter.categoryBits = WORLD_PHYSIC;
        fixtureDef.filter.maskBits = BALL_PHYSIC;

        body.createFixture(fixtureDef);
        body.setUserData(this);
        shape.dispose();
    }

    public MoneyItem setActive() {
        Box2dManager.getInstance().addActiveBodyToQueue(body);
        return this;
    }

    @Override
    public void setPosition(float x, float y) {
        super.setPosition(x, y);
        sprite.setPosition(x + 0.5f - sprite.getWidth() / 2, y + 0.5f - sprite.getHeight() / 2);
        if (body != null && !world.isLocked())
            body.setTransform(sprite.getX() + sprite.getWidth() / 2, sprite.getY() + sprite.getHeight() / 2, 0);
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        super.draw(batch, parentAlpha);
    }

    @Override
    public boolean remove() {
        screen.level.freeMoneyItem(this);
        return super.remove();
    }

    public void increaseMoney() {
//        Level level = (Level) this.getStage();
//        level.screen.uiObject.increateMoney();
//        screen.uiObject.increateMoney();
        screen.increaseMoeny();
        remove();
    }

    @Override
    public void reset() {
        Box2dManager.getInstance().addInActiveBodyToQueue(body);
    }
}
