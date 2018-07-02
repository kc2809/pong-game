package com.mygdx.game.object;

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
import static com.mygdx.game.util.Constants.WORLD_PHYSIC;

public class Ball extends ObjectBox2d {

    public boolean isProgress;

    int name;


    public Ball(World world, Vector2 position, int name) {
        super(world, Assets.instance.circle, position.x, position.y);
        isProgress = false;
        updateByBody = true;
        this.name = name;
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
        shape.dispose();
        body.setUserData(this);
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
        if (body.getLinearVelocity().len() > 0 && body.getLinearVelocity().len() < 10) {
            body.setLinearVelocity(body.getLinearVelocity().scl(10));
        }
    }

    public void fire(float x, float y) {
        body.setLinearVelocity(x, y);
    }

    public void fireWithVelocity(Vector2 velocity) {
        isProgress = true;
        updateByBody = true;
        body.setLinearVelocity(velocity);
    }


    public void stop() {
        if(!isProgress) return;
        System.out.println("STOP IS CALLL from ball :  " + name);
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
