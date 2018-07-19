package com.mygdx.game.object;

import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.Pool;
import com.mygdx.game.screens.MainGameScreen;

public class SquarePool extends Pool<Square> {

    World world;
    MainGameScreen screen;

    public SquarePool(World world, MainGameScreen screen) {
        super(50);
        this.world = world;
        this.screen = screen;
    }

    @Override
    protected Square newObject() {
        return (Square) (new Square(world, screen)).init(-20, 0);
    }
}
