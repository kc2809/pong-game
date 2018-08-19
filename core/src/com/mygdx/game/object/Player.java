package com.mygdx.game.object;

import box2dLight.RayHandler;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.scenes.scene2d.Action;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.mygdx.game.screens.MainGameScreen;
import com.mygdx.game.storage.MyPreference;
import com.mygdx.game.util.Constants;

import static com.mygdx.game.util.Constants.BOTTOM_WALLS_POSITION;
import static com.mygdx.game.util.Constants.SPEED;

public class Player extends Stage {

    public Vector2 positionToFire;
    int countOnFire = 0;
    MainGameScreen mainGameScreen;
    World world;
    private Vector2 velocity;

    RayHandler handler;

    BallPool pool;

    Color color;
    int distanceColor;

    public Player(Viewport viewport, Screen screen, World world, RayHandler handler) {
        super(viewport);
        this.mainGameScreen = (MainGameScreen) screen;
        this.world = world;
        setInitPositon();
        this.handler = handler;

        pool = new BallPool(world, handler, mainGameScreen);
        this.color = MyPreference.getInstance().getCurrentColor();
        distanceColor = 1;
    }

    public void setDistance(int distance){
        this.distanceColor = distance;
        for (Actor actor : this.getActors()) {
            Ball b = (Ball) actor;
            b.setPointLightColor(this.color, distanceColor);
        }
    }

    public Vector2 getVelocity() {
        return velocity;
    }

    public void fire(Vector2 vel) {
        velocity = vel;
    }

    public boolean isFirstBallTouchGround() {
        return countOnFire == 0;
    }

    //Actor is ball
    public boolean eventBallTouchGround(Ball actor) {
        System.out.println("INSIDE PLAYER");
        if (!isFirstBallTouchGround()) {
            //           actor.addAction(Actions.moveTo(positionToFire.x, positionToFire.y, 0.2f));
            Action moveToAction = Actions.moveTo(positionToFire.x, positionToFire.y, 0.3f);
            actor.addAction(Actions.sequence(moveToAction, Actions.run(getNextStep())));
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
        if (++countOnFire == this.getActors().size) {
            countOnFire = 0;
            mainGameScreen.nextStep();
        }
    }

    public void setInitPositon() {
//        positionToFire = new Vector2(0, -getCamera().viewportHeight / 2);
        positionToFire = new Vector2(0, -getCamera().viewportHeight * BOTTOM_WALLS_POSITION);
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
        ball.setPointLightColor(color, distanceColor);
        ball.isProgress = false;
        this.addActor(ball);
        ball.active();
    }

    public void addBalls(int numberOfBall) {
        for (int i = 0; i < numberOfBall; ++i) {
            addNewBall();
        }
    }

    public void setColorBalls(Color color) {
        this.color = color;
        for (Actor actor : this.getActors()) {
            Ball b = (Ball) actor;
            b.setPointLightColor(this.color, distanceColor);
        }
    }

    public void reset() {
        setInitPositon();
        if (this.getActors().size < 2) return;
        for (int i = 1; i < this.getActors().size; ++i) {
            this.getActors().get(i).addAction(Actions.removeActor());
        }
        setDistance(1);
    }

    public void freeBall(Ball ball) {
        pool.free(ball);
    }

    public Vector2 centerPosition() {
        return positionToFire.cpy().add(new Vector2(Constants.BALL_WIDTH / 2, Constants.BALL_WIDTH / 2));
    }
}
