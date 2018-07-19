package com.mygdx.game.object;

import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.Pool;
import com.mygdx.game.screens.MainGameScreen;

public class MoneyItemPool extends Pool<MoneyItem> {

    World world;
    MainGameScreen screen;

    public MoneyItemPool(World world, MainGameScreen screen) {
        this.world = world;
        this.screen = screen;
    }

    @Override
    protected MoneyItem newObject() {
        return (MoneyItem) (new MoneyItem(world, screen).init(-30, 0));
    }
}
