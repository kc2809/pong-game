package com.mygdx.game.object;


import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.scenes.scene2d.Action;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.mygdx.game.core.Assets;
import com.mygdx.game.screens.MainGameScreen;
import com.mygdx.game.storage.MyPreference;
import com.mygdx.game.util.CommonUI;
import com.mygdx.game.util.MathUtils;

import java.util.Random;

import static com.mygdx.game.core.Assets.PAUSE_ICON;
import static com.mygdx.game.util.Constants.BOTTOM_WALLS_POSITION;
import static com.mygdx.game.util.Constants.RATIO_TO_DUPLICATE_VALUE;
import static com.mygdx.game.util.Constants.SPACE_BETWEEN_SQUARE;
import static com.mygdx.game.util.Constants.SQUARE_HEIGHT;
import static com.mygdx.game.util.Constants.SQUARE_WIDTH;
import static com.mygdx.game.util.Constants.VIEWPORT_WIDTH;


public class Level extends Stage {
    private static final int MAX_SQUARE_PER_ROW = 7;
    private static int RATIO_GENERATE_ITEM1 = 30;
    private static int RATIO_GENERATE_MONEY_ITEM = 20;
    World world;
    MainGameScreen screen;
    Random random = new Random();
    UIObjects uiObjects;
    Button pauseBtn;
    int materialType = 0;
    int numberType = 0;
    SquarePool squarePool;
    MoneyItemPool moneyItemPool;
    Item1Pool item1Pool;

    float limitY;

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
        createPauseButton();
        limitY = getCamera().viewportHeight;
    }

    public void reset() {
        for (Actor actor : getActors()) {
            if (actor instanceof Square || actor instanceof Item1 || actor instanceof MoneyItem)
                actor.addAction(Actions.removeActor());
        }
        uiObjects.setScoreText(0);
        limitY = getCamera().viewportHeight;
        uiObjects.updateCurrentMoney();
    }


    public void oneMoreTime() {
//        float y = -getCamera().viewportHeight / 2.0f;
        float y = 0;
        for (Actor actor : getActors()) {
            if (actor.getY() < y) {
                actor.addAction(Actions.removeActor());
            }
        }
        uiObjects.setMoneyText(MyPreference.getInstance().getMoney());
    }

    public void increaseScore() {
        uiObjects.increaseScore();
    }

    public void moveOneRow() {
        if (numberType++ == 5) {
            numberType = 0;
            materialType++;
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
        Array<Actor> actors = this.getActors();
        int i = 0;
        if (isLevelFinish()) {
            generateNextStep();
        }
        limitY = getYLastSquare() - 1.1f;
        if (checkGameOver()) {
            screen.gameOver();
            return;
        }
        for (Actor actor : actors) {
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

    private float getYLastSquare() {
        Array<Actor> actors = this.getActors();
        float minY = getCamera().viewportHeight / 2;
        for (Actor actor : actors) {
            if (actor instanceof Square && actor.getY() < minY) {
                minY = actor.getY();
            }
        }
        return minY;
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
        return limitY < limitYToGameOver();
    }

    /**
     * limit value to gameover
     *
     * @return
     */
    private float limitYToGameOver() {
        return -getCamera().viewportHeight * BOTTOM_WALLS_POSITION + 0.4f;
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
        int squareValue = MathUtils.instance.ratio(RATIO_TO_DUPLICATE_VALUE) ? screen.currentLevel * 2 :
                screen.currentLevel;
        for (int i = 0; i < MAX_SQUARE_PER_ROW; ++i) {
            float x = -VIEWPORT_WIDTH / 2 + SPACE_BETWEEN_SQUARE + SQUARE_WIDTH * i + SPACE_BETWEEN_SQUARE * i;
            if ((value & mask) != 0)
                // add square if it is bit 1
                this.addActor(createNewSquare(x, y, squareValue, color));
            else
                // generate Item1 or money by bit 0 and ratio
                // 30% generate Item1
                if (MathUtils.instance.ratio(RATIO_GENERATE_ITEM1) && screen.isValidGenerateBall()) {
                    this.addActor(createNewItem1(x, y));
                } else if (MathUtils.instance.ratio(RATIO_GENERATE_MONEY_ITEM))
                    this.addActor(createNewMoneyItem(x, y));
            value = value >> 1;
        }
    }

    private MoneyItem createNewMoneyItem(float x, float y) {
        MoneyItem moneyItem = moneyItemPool.obtain();
        moneyItem.setActive();
        moneyItem.setPosition(x, y);
        return moneyItem;
    }

    private Item1 createNewItem1(float x, float y) {
        Item1 item1 = item1Pool.obtain();
        item1.setPosition(x, y);
        item1.active();
        return item1;
    }

    private Square createNewSquare(float x, float y, int value, Color color) {
        Square square = squarePool.obtain();
        square.setPosition(x, y);
        square.active().setColor(color);
        square.setValue(value);
        return square;
    }

    private void createPauseButton() {
        pauseBtn = CommonUI.getInstance().createImageButton(Assets.instance.getAsset(PAUSE_ICON), null, null,
                new ClickListener() {
                    @Override
                    public void clicked(InputEvent event, float x, float y) {
                        super.clicked(event, x, y);
//                screen.paused = !screen.paused;
                        screen.pauseScreen();
                    }
                });
        pauseBtn.setSize(1, 1);
        pauseBtn.setPosition(-VIEWPORT_WIDTH / 2 + 0.1f, getCamera().viewportHeight / 2 - pauseBtn.getHeight());
        addActor(pauseBtn);
    }

    private float getPositionForGenerate() {
        return getCamera().viewportHeight * 5 / 12 - 2.0f;
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
