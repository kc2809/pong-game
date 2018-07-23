package com.mygdx.game.object;


import box2dLight.RayHandler;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.Pool;
import com.mygdx.game.screens.MainGameScreen;

public class BallPool extends Pool<Ball> {
    World world;
    RayHandler handler;
    MainGameScreen screen;

    public BallPool(World world, RayHandler handler, MainGameScreen screen) {
        super(20);
        this.world = world;
        this.handler = handler;
        this.screen = screen;
    }

    @Override
    protected Ball newObject() {
        return (Ball) (new Ball(world, handler, screen)).init(-10, 0);
    }
}
