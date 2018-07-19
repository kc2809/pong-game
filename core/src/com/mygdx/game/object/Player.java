package com.mygdx.game.object;

import box2dLight.RayHandler;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.scenes.scene2d.Action;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.mygdx.game.screens.MainGameScreen;

import static com.mygdx.game.util.Constants.SPEED;

public class Player extends Stage {

    public Vector2 positionToFire;
    int countOnFire = 0;
    MainGameScreen mainGameScreen;
    World world;
    private Vector2 velocity;

    RayHandler handler;

    BallPool pool;

    public Player(Viewport viewport, Screen screen, World world, RayHandler handler) {
        super(viewport);
        this.mainGameScreen = (MainGameScreen) screen;
        this.world = world;
        setInitPositon();
        this.handler = handler;

        pool = new BallPool(world, handler);
    }

    public Vector2 getVelocity() {
        return velocity;
    }

    public void setVelocityWithClickPoint(Vector2 clickPoint) {
        velocity = clickPoint.cpy().sub(positionToFire).nor().scl(SPEED);
    }

    public void increteCountOnFire() {
        countOnFire++;
    }

    public void resetWhenFireEventFinish() {
        countOnFire = 0;
    }

    public boolean isFirstBallTouchGround() {
        return countOnFire == 0;
    }

    //Actor is ball
    public boolean eventBallTouchGround(Ball actor) {
        if (!isFirstBallTouchGround()) {
            //           actor.addAction(Actions.moveTo(positionToFire.x, positionToFire.y, 0.2f));
            Action moveToAction = Actions.moveTo(positionToFire.x, positionToFire.y, 0.3f);
            actor.addAction(Actions.sequence(moveToAction, Actions.run(getNextStep())));
//            actor.addAction(moveToAction);
            //     nextStep();
            return true;
        } else {
            positionToFire = new Vector2(actor.getSprite().getX(), actor.getSprite().getY());
            nextStep();
            return false;
        }
    }

    private Runnable getNextStep() {
        return new Runnable() {
            @Override
            public void run() {
                nextStep();
            }
        };
    }

    private void nextStep() {
//        System.out.println("count on fire : " + countOnFire);
//        countOnFire++;
        if (++countOnFire == this.getActors().size) {
            countOnFire = 0;
//            mainGameScreen.nextRow();
            mainGameScreen.nextStep();
        }
//        System.out.println(" after count on fire : " + countOnFire);

    }

    public void setInitPositon() {
//        positionToFire = new Vector2(0, -getCamera().viewportHeight / 2);
        positionToFire = new Vector2(0, -getCamera().viewportHeight * 6 / 14);
        if (this.getActors().size > 0) {
            for (int i = 0; i < this.getActors().size; ++i) {
                Ball b = (Ball) this.getActors().get(i);
                b.setPosition(this.positionToFire.x, this.positionToFire.y);
            }
        }
    }

    public void addNewBall() {
        Ball ball = pool.obtain();
        ball.setPosition(this.positionToFire.x, this.positionToFire.y);
//        this.addActor((new Ball(this.world, handler)).init(this.positionToFire.x, this.positionToFire.y));
        this.addActor(ball);
    }

    public void addBalls(int numberOfBall) {
        for (int i = 0; i < numberOfBall; ++i) {
            addNewBall();
        }
    }
}
