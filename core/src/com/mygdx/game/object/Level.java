package com.mygdx.game.object;


import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.scenes.scene2d.Action;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.mygdx.game.screens.MainGameScreen;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import static com.mygdx.game.util.Constants.SPACE_BETWEEN_SQUARE;
import static com.mygdx.game.util.Constants.SQUARE_HEIGHT;
import static com.mygdx.game.util.Constants.SQUARE_WIDTH;
import static com.mygdx.game.util.Constants.VIEWPORT_WIDTH;


public class Level extends Stage {
    private static final int MAX_SQUARE_PER_ROW = 7;
    World world;

    MainGameScreen screen;

    Random random = new Random();

    UIObjects uiObjects;

    public Level(MainGameScreen screen, Viewport viewport, World world) {
        super(viewport);
        this.screen = screen;
//        stage = new Stage(viewport);
        this.world = world;
        generateNextStep();
        uiObjects = new UIObjects(screen);
        uiObjects.setPosition(0, this.getViewport().getWorldHeight() / 2);
        addActor(uiObjects);
    }

    public void increaseScore() {
        uiObjects.increaseScore();
    }

    public void moveOneRow() {
        addActionToAllSquare();
    }

    private boolean isLevelFinish() {
        //level finish when there's no square on game
        //then we generate next level
        for (Actor actor : this.getActors()) {
            if (actor instanceof Square) return false;
        }
        return true;
    }

    private void addActionToAllSquare() {
        int i = 0;
        if(isLevelFinish()) generateNextStep();
        for (Actor actor : this.getActors()) {
            if (actor instanceof Square || actor instanceof Item1 || actor instanceof MoneyItem) {
                if (i++ == 0) {
                    Action moveToAction = Actions.moveTo(actor.getX(), actor.getY() - SQUARE_HEIGHT - 0.1f, 0.3f);
//                    actor.addAction(Actions.sequence(moveToAction, Actions.run(this::generateNextStep)));
                    actor.addAction(Actions.sequence(moveToAction, Actions.run(() -> {
                        generateNextStep();
                        screen.nextRow();
                    })));
                } else {
                    actor.addAction(Actions.moveTo(actor.getX(), actor.getY() - SQUARE_HEIGHT - 0.1f, 0.3f));
                }
            }
        }
    }

    private void generateNextStep() {
        int value = random.nextInt(64);
        //one value means one row
        // value from [0, 512]
        // generate 8 square based on binary value of it.
        int mask = 0b1;
        float y = getPositionForGenerate();
        for (int i = 0; i < MAX_SQUARE_PER_ROW; ++i) {
            float x = -VIEWPORT_WIDTH / 2 + SPACE_BETWEEN_SQUARE + SQUARE_WIDTH * i + SPACE_BETWEEN_SQUARE * i;
            if ((value & mask) != 0) {
                // add square if it is 1
                Square s = (Square) (new Square(screen, world)).init(x, y);
                this.addActor(s);
            } else {
                // 5% generate Item1
                if (belowPercent(30)) {
                    this.addActor((new Item1(world, screen)).init(x, y));
                } else {
                    if (belowPercent(10))
                        this.addActor((new MoneyItem(world, screen)).init(x, y));
                }
            }
            value = value >> 1;
        }

    }

    private boolean belowPercent(int percent) {
        if (random.nextInt(100) > percent) return false;
        return true;
    }


    private float getPositionForGenerate() {
        return getCamera().viewportHeight * 5 / 12 - 1.0f;
    }

    public void increaseMoney() {
        uiObjects.increaseMoney();
    }
}
