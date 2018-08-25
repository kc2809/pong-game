package com.mygdx.game.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Label.LabelStyle;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.mygdx.game.MyGdxGame;
import com.mygdx.game.core.Assets;
import com.mygdx.game.storage.MyPreference;
import com.mygdx.game.util.CommonUI;
import com.mygdx.game.util.Constants;

import static com.mygdx.game.util.Constants.PPM;

public class GameOverScreen implements Screen {
    MyGdxGame game;
    Stage stage;

    OrthographicCamera camera;
    Viewport viewport;
    Label labelBestScore;
    Label labelScore;

    Label labelMoney;

    int score;

    public GameOverScreen(MyGdxGame game, OrthographicCamera camera, Viewport viewPort) {
        this.game = game;
        this.camera = camera;
        this.viewport = viewPort;
        create();
    }

    private void create() {
        stage = new Stage(viewport);

        LabelStyle style = new LabelStyle();
        style.font = Assets.instance.titleFont;

        LabelStyle styleBtn = new LabelStyle();
        styleBtn.font = Assets.instance.fontBig;
        // BEST label
        labelBestScore = new Label("BEST", style);
        labelBestScore.setPosition(-1.5f, 1.0f);
        stage.addActor(labelBestScore);

        //
        LabelStyle styleBig = new LabelStyle();
        styleBig.font = Assets.instance.bigbigFont;
        labelScore = new Label("80", styleBig);
        labelScore.setPosition(-labelScore.getWidth() / 2, 2.0f);
        stage.addActor(labelScore);

        //
        createOneMoreTimeBtn(styleBtn);
        createCurrentMoney(styleBtn);

        //Replay button
        Group replayButton = CommonUI.getInstance().createBox(0, -0.5f, styleBtn, "REPLAY", Constants.customRed,
                new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                super.clicked(event, x, y);
                rePlay();
            }
        });
        stage.addActor(replayButton);

        // main menu button
        Group mainMenuButton = CommonUI.getInstance().createBox(0, -2.3f, styleBtn, "MAIN MENU", Constants.customBlue
                , new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                super.clicked(event, x, y);
                changeMenuScreen();
            }
        });
        stage.addActor(mainMenuButton);

        createAdmobBtn();
    }

    public void setScore(int score) {
        this.score = score;
    }

    private void createAdmobBtn() {
        Button btnAdmob = CommonUI.getInstance().createImageButton(Assets.instance.getAsset(Assets.VIDEO_REWARDED_ICON), null,
                null, new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                super.clicked(event, x, y);
                System.out.println("cai lz j vay");
                game.callVideoAd();
            }
        });
        btnAdmob.setSize(btnAdmob.getWidth() * 3.0f / PPM, btnAdmob.getHeight() * 3.0f / PPM);
        btnAdmob.setPosition(-3.0f, camera.viewportHeight *2/8);
        btnAdmob.setTransform(true);
        btnAdmob.setRotation(40);
        stage.addActor(btnAdmob);
    }

    private void createOneMoreTimeBtn(LabelStyle style) {
        Button btnReplay = CommonUI.getInstance().createImageButton(Assets.instance.getAsset(Assets.REPLAY_ICON),
                null, null, new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                super.clicked(event, x, y);
                oneMoreTime();
            }
        });
        btnReplay.setSize(btnReplay.getWidth() * 3 / PPM, btnReplay.getHeight() * 3 / PPM);
        btnReplay.setPosition(-btnReplay.getWidth() - 1.0f, btnReplay.getHeight() - 6.0f);
        stage.addActor(btnReplay);

        Label label1 = new Label(Constants.MONEY_FOR_REPLAY + "", style);
        label1.setPosition(btnReplay.getX() + 2.0f, btnReplay.getY());
        stage.addActor(label1);

        Image imgGold = new Image(Assets.instance.getAsset(Assets.MONEY_ITEM));
        imgGold.setSize(0.8f, 0.8f);
        imgGold.setPosition(label1.getX() + 1.0f, label1.getY() + 0.05f);
        stage.addActor(imgGold);
    }

    private void createCurrentMoney(LabelStyle style) {
        Image image = new Image(Assets.instance.getAsset(Assets.MONEY_ITEM));
        image.setSize(image.getWidth() / PPM, image.getHeight() / PPM);
        image.setPosition(1.0f, camera.viewportHeight / 2 - 1.0f);
        stage.addActor(image);

        labelMoney = new Label("", style);
        labelMoney.setPosition(image.getX() + 1.0f, image.getY() + 0.5f);
        stage.addActor(labelMoney);
    }

    public void setCurrentMoney() {
        labelMoney.setText(MyPreference.getInstance().getMoney() + "");
    }

    @Override
    public void show() {
        setHighestScore();
        setCurrentMoney();
        setLabelScore();
        Gdx.input.setInputProcessor(stage);
    }

    @Override
    public void render(float delta) {
//        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClearColor(Constants.backgroundColor.r, Constants.backgroundColor.g, Constants.backgroundColor.b,
                Constants.backgroundColor.a);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        stage.draw();
    }

    @Override
    public void resize(int width, int height) {

    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {
    }

    @Override
    public void dispose() {
    }

    private void setHighestScore() {
        labelBestScore.setText("Best: " + MyPreference.getInstance().getHighestScore());
    }

    private void setLabelScore() {
        labelScore.setText(score + "");
        labelScore.setX(-labelScore.getWidth() / 2);
    }

    private void rePlay() {
        game.changeMainGameScreen();
    }

    private boolean validOneMoreTime() {
        return MyPreference.getInstance().getMoney() >= Constants.MONEY_FOR_REPLAY;
    }

    private void changeMenuScreen() {
        game.changeMenuScreen();
    }

    private void oneMoreTime() {
        if (validOneMoreTime()) {
            // minus values to play once more time
            MyPreference pre = MyPreference.getInstance();
            pre.setMoney(pre.getMoney() - Constants.MONEY_FOR_REPLAY);
            game.oneMoreTime();
        }
//        game.oneMoreTime();
    }
}
