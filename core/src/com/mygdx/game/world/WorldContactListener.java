package com.mygdx.game.world;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Contact;
import com.badlogic.gdx.physics.box2d.ContactImpulse;
import com.badlogic.gdx.physics.box2d.ContactListener;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.Manifold;
import com.mygdx.game.object.Ball;
import com.mygdx.game.object.Item1;
import com.mygdx.game.object.MoneyItem;
import com.mygdx.game.object.Square;
import com.mygdx.game.screens.MainGameScreen;

import static com.mygdx.game.util.Constants.DEGREE_DECREASE;
import static com.mygdx.game.util.Constants.LOWER_LIMIT;
import static com.mygdx.game.util.Constants.SPEED;
import static com.mygdx.game.util.Constants.UPPER_LIMIT;
import static com.mygdx.game.util.VectorUtil.reflectVector;

public class WorldContactListener implements ContactListener {

    MainGameScreen screen;

    public WorldContactListener(MainGameScreen gameScreen) {
        this.screen = gameScreen;
    }

    private <T> T getObjectByFixture(Fixture fixA, Fixture fixB, Class<T> neededClass) {
        return neededClass.isInstance(fixA.getBody().getUserData()) ?
                neededClass.cast(fixA.getBody().getUserData()) :
                neededClass.cast(fixB.getBody().getUserData());
    }

    private boolean checkIsInstanceOf(Fixture fixtureA, Fixture fixtureB, Class neededClass) {
        return neededClass.isInstance(fixtureA.getBody().getUserData()) ||
                neededClass.isInstance(fixtureB.getBody().getUserData());
    }

    private boolean checkIsIntanceOfString(Fixture fixtureA, Fixture fixtureB, String str) {
        return str.equals(fixtureA.getBody().getUserData()) || str.equals((fixtureB.getBody().getUserData()));
    }

    @Override
    public void beginContact(Contact contact) {
        Fixture fixA = contact.getFixtureA();
        Fixture fixB = contact.getFixtureB();

        if (item1Contact(fixA, fixB) || moneyItemContact(fixA, fixB)) return;

        if (squareContact(fixA, fixB)) {
            Ball ball = getObjectByFixture(fixA, fixB, Ball.class);
            if(ball.isProgress) {

            }
        }

        // Ball contact
        Ball ball = getObjectByFixture(fixA, fixB, Ball.class);

        if (checkIsIntanceOfString(fixA, fixB, "botWall")) {
            ball.stop();
            return;
        }

        Vector2 normal = contact.getWorldManifold().getNormal();
        Vector2 roundNormal = new Vector2(Math.round(normal.x), Math.round(normal.y));
        Vector2 velocity = ball.getBody().getLinearVelocity();
        Vector2 r = reflectVector(velocity, roundNormal).nor().scl(SPEED);

        if (checkIsIntanceOfString(fixA, fixB, "rightWall")) {
            float angle = r.angle();
            if (angle > LOWER_LIMIT && angle < UPPER_LIMIT) {
                System.out.println("Decrease " + DEGREE_DECREASE + " degrees");
                float newAngle = angle + DEGREE_DECREASE;
                r = new Vector2(r.x, (float) (r.len() * Math.sin(Math.toRadians(newAngle))));
            }
        }
        // reflect when collision with walls and square happened
        ball.changeVelocity(r.x, r.y);
    }

    @Override
    public void endContact(Contact contact) {
        if(checkIsInstanceOf(contact.getFixtureA(), contact.getFixtureB(), Square.class)){
            Square square = getObjectByFixture(contact.getFixtureA(), contact.getFixtureB(), Square.class);
            square.resetColor();
        }
    }

    private boolean item1Contact(Fixture fixtureA, Fixture fixtureB) {
        if (checkIsInstanceOf(fixtureA, fixtureB, Item1.class)) {
            Item1 item1 = getObjectByFixture(fixtureA, fixtureB, Item1.class);
            item1.addBallsToGame();
            return true;
        }
        return false;
    }

    private boolean moneyItemContact(Fixture fixA, Fixture fixB) {
        if (checkIsInstanceOf(fixA, fixB, MoneyItem.class)) {
            MoneyItem moneyItem = getObjectByFixture(fixA, fixB, MoneyItem.class);
            moneyItem.increaseMoney();
            return true;
        }
        return false;
    }

    private boolean squareContact(Fixture fixA, Fixture fixB) {
        if (checkIsInstanceOf(fixA, fixB, Square.class)) {
            Square square = getObjectByFixture(fixA, fixB, Square.class);
            square.descreaseValue();
            return true;
        }
        return false;
    }

    @Override
    public void preSolve(Contact contact, Manifold oldManifold) {
    }

    @Override
    public void postSolve(Contact contact, ContactImpulse impulse) {

    }
}
