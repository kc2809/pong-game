package com.mygdx.game.object;


import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.Pool;
import com.mygdx.game.screens.MainGameScreen;

public class Item1Pool extends Pool<Item1> {
    World world;
    MainGameScreen screen;

    public Item1Pool(World world, MainGameScreen screen) {
//        super(10);
        this.world = world;
        this.screen = screen;
    }

    @Override
    protected Item1 newObject() {
        return (Item1) new Item1(world, screen).init(-40, 0);
    }
}
