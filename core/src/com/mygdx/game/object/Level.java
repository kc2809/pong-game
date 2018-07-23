package com.mygdx.game.object;


import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.scenes.scene2d.Action;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.mygdx.game.screens.MainGameScreen;
import com.mygdx.game.util.MathUtils;

import java.util.Random;

import static com.mygdx.game.util.Constants.RATIO_TO_DUPLICATE_VALUE;
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

    int materialType = 0;
    int numberType = 0;

    int ratioToGenerateItem1 = 30;
    int ratioToGenerateMoneyItem = 10;

    SquarePool squarePool;
    MoneyItemPool moneyItemPool;
    Item1Pool item1Pool;


    public Level(MainGameScreen screen, Viewport viewport, World world) {
        super(viewport);
        squarePool = new SquarePool(world, screen);
        moneyItemPool = new MoneyItemPool(world, screen);
        item1Pool = new Item1Pool(world, screen);

        this.screen = screen;
//        stage = new Stage(viewport);
        this.world = world;
//        generateNextStep();
        uiObjects = new UIObjects(screen);
        uiObjects.setPosition(0, this.getViewport().getWorldHeight() / 2);
        addActor(uiObjects);
    }

    public void reset() {
        for (Actor actor : getActors()) {
            if (actor instanceof Square || actor instanceof Item1 || actor instanceof MoneyItem)
                actor.addAction(Actions.removeActor());
        }
        uiObjects.setScoreText(0);
        uiObjects.setMoneyText(0);
    }


    public void oneMoreTime() {
        for (Actor actor : getActors()) {
            if (actor.getY() < 0) {
                actor.addAction(Actions.removeActor());
            }
        }
    }

    public int getCurrentLevel() {
        return screen.currentLevel;
    }

    public void increaseScore() {
        uiObjects.increaseScore();
    }

    public void moveOneRow() {
        if (screen.currentLevel == 10) {
            screen.setColorPlayer(Color.CYAN);
        }
        if (numberType++ == 5) {
            numberType = 0;
            materialType++;
            if(ratioToGenerateItem1 >15){
                ratioToGenerateItem1--;
            }
            if (materialType == 5) {
                materialType = 0;
            }
        }
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
        if (isLevelFinish()) {
            generateNextStep();
        }
        for (Actor actor : this.getActors()) {
            if (actor instanceof Square || actor instanceof Item1 || actor instanceof MoneyItem) {
                if (i++ == 0) {
                    Action moveToAction = Actions.moveTo(actor.getX(), actor.getY() - SQUARE_HEIGHT - 0.1f, 0.3f);
                    actor.addAction(Actions.sequence(moveToAction, Actions.run(getRunable())));
                } else {
                    actor.addAction(Actions.moveTo(actor.getX(), actor.getY() - SQUARE_HEIGHT - 0.1f, 0.3f));
                }
            }
        }
    }

    private Runnable getRunable() {
        return new Runnable() {
            @Override
            public void run() {
                generateNextStep();
                screen.nextRow();
            }
        };
    }

    public boolean checkGameOver() {
        for (Actor actor : this.getActors()) {
            if (actor instanceof Square)
                if (actor.getY() < -getCamera().viewportHeight * 6 / 14 + 0.4f)
                    return true;
        }
        return false;
    }

    public void generateNextStep() {
        int value = random.nextInt(64);
        //one value means one row
        // value from [0, 512]
        // generate 8 square based on binary value of it.
        int mask = 0b1;
        float y = getPositionForGenerate();
//        Color color = MaterialColor.getMaterialColors(random.nextInt(5));
        Color color = MaterialColor.getMaterialColors(materialType);
        int squareValue = MathUtils.instance.ratio(RATIO_TO_DUPLICATE_VALUE) ? screen.currentLevel * 2 : screen.currentLevel;
        for (int i = 0; i < MAX_SQUARE_PER_ROW; ++i) {
            float x = -VIEWPORT_WIDTH / 2 + SPACE_BETWEEN_SQUARE + SQUARE_WIDTH * i + SPACE_BETWEEN_SQUARE * i;
            if ((value & mask) != 0) {
                // add square if it is 1
                Square s = squarePool.obtain();
                s.setPosition(x, y);
                s.active().setColor(color);
                this.addActor(s);
                s.setValue(squareValue);
            } else {
                // 5% generate Item1
                if (MathUtils.instance.ratio(ratioToGenerateItem1)) {
//                    this.addActor((new Item1(world)).init(x, y));
                    Item1 item1 = item1Pool.obtain();
                    item1.active();
                    item1.setPosition(x, y);
                    this.addActor(item1);
                } else {
                    if (MathUtils.instance.ratio(ratioToGenerateMoneyItem)) {
                        MoneyItem moneyItem = moneyItemPool.obtain();
                        moneyItem.setActive();
                        moneyItem.setPosition(x, y);
                        this.addActor(moneyItem);
                    }
                }
            }
            value = value >> 1;
        }

    }


    private float getPositionForGenerate() {
        return getCamera().viewportHeight * 5 / 12 - 1.0f;
    }

    public void increaseMoney() {
        uiObjects.increaseMoney();
    }

    public void freeSquare(Square square) {
        squarePool.free(square);
    }

    public void freeMoneyItem(MoneyItem moneyItem) {
        moneyItemPool.free(moneyItem);
    }

    public void freeItem1(Item1 item1) {
        item1Pool.free(item1);
    }
}
