package com.mygdx.game.object;


import box2dLight.RayHandler;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.Pool;

public class BallPool extends Pool<Ball> {
    World world;
    RayHandler handler;

    public BallPool(World world, RayHandler handler) {
        super(20);
        this.world = world;
        this.handler = handler;
    }

    @Override
    protected Ball newObject() {
        return (Ball) (new Ball(world, handler)).init(-10, 0);
    }
}
