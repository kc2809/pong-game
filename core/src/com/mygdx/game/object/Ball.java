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
import com.mygdx.game.core.Assets;

import static com.mygdx.game.util.Constants.BALL_PHYSIC;
import static com.mygdx.game.util.Constants.BALL_WIDTH;
import static com.mygdx.game.util.Constants.SPEED;
import static com.mygdx.game.util.Constants.WORLD_PHYSIC;

public class Ball extends ObjectBox2d {

    public boolean isProgress;

    int name;
    public boolean inContact;
    PointLight pointLight;
    RayHandler handler;

    public Ball(World world, int name, RayHandler handler) {
        super(world, Assets.instance.assetCircle.circle);
        isProgress = false;
        updateByBody = true;
        this.name = name;
        inContact = false;
        this.handler = handler;
    }

    @Override
    public void createPhysics() {
        if (isWorldLock()) return;
        BodyDef bodyDef = new BodyDef();
        bodyDef.type = BodyType.DynamicBody;
        bodyDef.position.set(sprite.getX() + sprite.getWidth() / 2, sprite.getY() + sprite.getHeight() / 2);
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
    }

    @Override
    void initComponent() {
        pointLight = new PointLight(handler, 4, Color.LIGHT_GRAY, 2, getX(), getY());
        pointLight.setActive(false);
    }

    public Body getBody() {
        return body;
    }

    public Sprite getSprite() {
        return sprite;
    }

    @Override
    public void act(float delta) {
        super.act(delta);
        if (body.getLinearVelocity().len() > 0 && body.getLinearVelocity().len() < 5) {
            System.out.println("BOM SPEED VOOOOOOOOO");
            body.setLinearVelocity(body.getLinearVelocity().nor().scl(SPEED * 4.0f));
        }

        if (pointLight != null) pointLight.setPosition(getX() + BALL_WIDTH / 2, getY() + BALL_WIDTH / 2);
    }

    @Override
    public void setPosition(float x, float y) {
        super.setPosition(x, y);
    }

    public void fire(float x, float y) {
        body.setLinearVelocity(x, y);
        pointLight.setActive(true);
    }

    public void fireWithVelocity(Vector2 velocity) {
        isProgress = true;
        updateByBody = true;
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
}
