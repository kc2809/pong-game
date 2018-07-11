package com.mygdx.game.object;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.mygdx.game.box2d.Box2dManager;

import static com.mygdx.game.util.Constants.PPM;

abstract class ObjectBox2d extends Actor {

    protected Sprite sprite;
    protected Body body;
    protected World world;
    protected boolean isCreatePhysics;
    protected boolean updateByBody;

    public ObjectBox2d(World world, TextureRegion texture, float x, float y) {
        this.world = world;
        isCreatePhysics = false;
        updateByBody = false;

        initObjects(texture);
        setPosition(x, y);
        createPhysics();
    }

    abstract void createPhysics();

    public void initObjects(TextureRegion texture) {
        sprite = new Sprite(texture);
        sprite.setSize(sprite.getWidth() / PPM, sprite.getHeight() / PPM);
    }

    @Override
    public void setPosition(float x, float y) {
        super.setPosition(x, y);
        setAttibutesPosition(x, y);
    }

    private void setAttibutesPosition(float x, float y) {
        sprite.setPosition(x, y);
        if (body != null)
            body.setTransform(sprite.getX() + sprite.getWidth() / 2, sprite.getY() + sprite.getHeight() / 2, 0);
    }


    @Override
    public void draw(Batch batch, float parentAlpha) {
        super.draw(batch, parentAlpha);
//        setPosition(getX(), getY());
        sprite.draw(batch);
    }

    @Override
    public void act(float delta) {
        super.act(delta);
        if (isCreatePhysics) {
            createPhysics();
        }

        if (updateByBody) {
            setPosByBodyPos();
        } else {
            setPosition(getX(), getY());
        }
    }

    public void setPosByBodyPos() {
        sprite.setPosition(body.getPosition().x - sprite.getWidth() / 2, body.getPosition().y - sprite.getHeight() / 2);
        setX(sprite.getX());
        setY(sprite.getY());
    }

    @Override
    public boolean remove() {
        Box2dManager.getInstance().addBodyToDestroy(body);
        return super.remove();
    }

    protected boolean isWorldLock() {
        if (world.isLocked()) {
            isCreatePhysics = true;
            return true;
        }
        isCreatePhysics = false;
        return false;
    }

    public float getCenterPostionX() {
        return sprite.getX() + sprite.getWidth() / 2;
    }

    public float getCenterPostionY() {
        return sprite.getY() + sprite.getHeight() / 2;
    }
}
