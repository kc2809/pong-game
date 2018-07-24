package com.mygdx.game.object;

import box2dLight.PointLight;
import box2dLight.RayHandler;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
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
import static com.mygdx.game.util.Constants.BALL_WIDTH;
import static com.mygdx.game.util.Constants.SPEED;
import static com.mygdx.game.util.Constants.WORLD_PHYSIC;

public class Ball extends ObjectBox2d  implements Poolable{

    public boolean isProgress;
    PointLight pointLight;
    RayHandler handler;
    MainGameScreen screen;

    public Ball(World world, RayHandler handler, MainGameScreen screen) {
        super(world, Assets.instance.assetCircle.circle);
        isProgress = false;
        updateByBody = true;
        this.handler = handler;
        this.screen = screen;
    }

    public Ball active() {
        Box2dManager.getInstance().addActiveBodyToQueue(body);
//        pointLight.setActive(true);
        return this;
    }

    @Override
    public void createPhysics() {
        BodyDef bodyDef = new BodyDef();
        bodyDef.type = BodyType.DynamicBody;
        bodyDef.position.set(sprite.getX() + sprite.getWidth() / 2, sprite.getY() + sprite.getHeight() / 2);
        if (isWorldLock()) return;
        body = world.createBody(bodyDef);

        CircleShape shape = new CircleShape();
        shape.setRadius(sprite.getWidth() / 2);

        FixtureDef fixtureDef = new FixtureDef();
        fixtureDef.shape = shape;
        fixtureDef.filter.categoryBits = BALL_PHYSIC;
        fixtureDef.filter.maskBits = WORLD_PHYSIC;

        body.createFixture(fixtureDef);
        body.setUserData(this);
        shape.dispose();
//        this.setVisible(false);
    }

    @Override
    void initComponent() {
        pointLight = new PointLight(handler, 7, Color.PURPLE, 1, getX(), getY());
        pointLight.setActive(false);
    }

    public Body getBody() {
        return body;
    }

    public Sprite getSprite() {
        return sprite;
    }

    public void setPointLightColor(Color color, int distance) {
        pointLight.setColor(color);
        pointLight.setDistance(distance);
    }

    @Override
    public void act(float delta) {
        super.act(delta);
        if (body != null && !world.isLocked())
            if (body.getLinearVelocity().len() > 0 && body.getLinearVelocity().len() < 5) {
                System.out.println("BOM SPEED VOOOOOOOOO");
                body.setLinearVelocity(body.getLinearVelocity().nor().add(new Vector2(0.1f, 0.1f)).scl(SPEED * 2.0f));
            }

        if (pointLight != null) pointLight.setPosition(getX() + BALL_WIDTH / 2, getY() + BALL_WIDTH / 2);
    }

    @Override
    public void setPosition(float x, float y) {
        super.setPosition(x, y);
    }

    public void fire(float x, float y) {
        if (body != null)
            body.setLinearVelocity(x, y);
        pointLight.setActive(true);
    }

    public void fireWithVelocity(Vector2 velocity) {
        isProgress = true;
        updateByBody = true;
        if (body != null)
            body.setLinearVelocity(velocity);
        pointLight.setActive(true);
    }


    public void stop() {
        pointLight.setActive(false);
        if(!isProgress) return;
        body.setLinearVelocity(0, 0);
        sprite.setPosition(body.getPosition().x - sprite.getWidth() / 2, body.getPosition().y - sprite.getHeight() / 2);
        setX(sprite.getX());
        setY(sprite.getY());
        updateByBody = false;
        Player player = (Player) getStage();
        player.eventBallTouchGround(this);
        isProgress = false;
    }


    @Override
    public void reset() {
        Box2dManager.getInstance().addInActiveBodyToQueue(body);
    }

    @Override
    public boolean remove() {
        screen.getPlayer().freeBall(this);
        pointLight.setActive(false);
        return super.remove();
    }
}
