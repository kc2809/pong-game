package com.mygdx.game.object;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.scenes.scene2d.Actor;

import static com.mygdx.game.util.Constants.PPM;

abstract class ObjectBox2d extends Actor {

    protected Sprite sprite;
    protected Body body;
    protected World world;
    protected boolean isCreatePhysics;
    protected boolean updateByBody;

    public ObjectBox2d(World world, TextureRegion texture) {
        this.world = world;
        isCreatePhysics = false;
        updateByBody = false;

        initTexture(texture);
    }

    public ObjectBox2d init(float x, float y) {
        initComponent();
        setPosition(x, y);
        createPhysics();
        return this;
    }

    abstract void createPhysics();

    public void initTexture(TextureRegion texture) {
        sprite = new Sprite(texture);
        sprite.setSize(sprite.getWidth() / PPM, sprite.getHeight() / PPM);
    }

    abstract void initComponent();

    @Override
    public void setPosition(float x, float y) {
        super.setPosition(x, y);
        setAttibutesPosition(x, y);
    }

    private void setAttibutesPosition(float x, float y) {
        if(sprite!=null) sprite.setPosition(x, y);
        if (body != null && !world.isLocked())
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
        if (body != null)
            sprite.setPosition(body.getPosition().x - sprite.getWidth() / 2, body.getPosition().y - sprite.getHeight() / 2);
        setX(sprite.getX());
        setY(sprite.getY());
    }

    @Override
    public boolean remove() {
//        Box2dManager.getInstance().addBodyToDestroy(body);
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

    public void setUpdateByBody(boolean b) {
        updateByBody = b;
    }
}
