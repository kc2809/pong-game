package com.mygdx.game.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
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
    Label label;

    Label labelMoney;

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
        label = new Label("BEST", style);
        label.setPosition(-2.1f, camera.viewportHeight / 2 - 3.3f);
        stage.addActor(label);



        LabelStyle styleBtn = new LabelStyle();
        styleBtn.font = Assets.instance.fontBig;
        createRePlayBtn(styleBtn);
        createOneMoreTimeBtn(styleBtn);
        createBtnMenu(styleBtn);
        createCurrentMoney(styleBtn);
    }

    private void createOneMoreTimeBtn(LabelStyle style) {
        Button btnReplay = CommonUI.getInstance().createImageButton(Assets.instance.getAsset(Assets.REPLAY_ICON), null, null, new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                super.clicked(event, x, y);
                oneMoreTime();
            }
        });
        btnReplay.setSize(btnReplay.getWidth() * 3 / PPM, btnReplay.getHeight() * 3 / PPM);
        btnReplay.setPosition(-btnReplay.getWidth() - 1.0f, btnReplay.getHeight() - 3.0f);
        stage.addActor(btnReplay);

        Label label1 = new Label(Constants.MONEY_FOR_REPLAY + "", style);
        label1.setPosition(btnReplay.getX() + 2.0f, btnReplay.getY());
        stage.addActor(label1);

        Image imgGold = new Image(Assets.instance.getAsset(Assets.MONEY_ITEM));
        imgGold.setSize(0.8f, 0.8f);
        imgGold.setPosition(label1.getX() + 1.0f, label1.getY() + 0.05f);
        stage.addActor(imgGold);
    }

    private void createRePlayBtn(LabelStyle style) {
        Button btnReplay = CommonUI.getInstance().createImageButton(Assets.instance.getAsset(Assets.PLAY_ICON), null, null, new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                super.clicked(event, x, y);
                rePlay();
            }
        });
        btnReplay.setSize(btnReplay.getWidth() * 3 / PPM, btnReplay.getHeight() * 3 / PPM);
        btnReplay.setPosition(-btnReplay.getWidth() - 1.0f, btnReplay.getHeight() - 1.0f);
        stage.addActor(btnReplay);

        Label label1 = new Label("REPLAY", style);
        label1.setPosition(btnReplay.getX() + 2.0f, btnReplay.getY());
        stage.addActor(label1);
    }

    private void createBtnMenu(LabelStyle style) {
        Button btnMenu = CommonUI.getInstance().createImageButton(Assets.instance.getAsset(Assets.MENU_ICON), null, null, new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                super.clicked(event, x, y);
                changeMenuScreen();
            }
        });
        btnMenu.setSize(btnMenu.getWidth() * 3 / PPM, btnMenu.getHeight() * 3 / PPM);
        btnMenu.setPosition(-btnMenu.getWidth() - 1.0f, -btnMenu.getHeight() - 2.0f);
        stage.addActor(btnMenu);

        // create label
        Label quitLabel = new Label("MENU", style);
        quitLabel.setPosition(btnMenu.getX() + 2.0f, btnMenu.getY());
        stage.addActor(quitLabel);
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

    private void setCurrentMoney() {
        labelMoney.setText(MyPreference.getInstance().getMoney() + "");
    }

    @Override
    public void show() {
        setHighestScore();
        setCurrentMoney();
        Gdx.input.setInputProcessor(stage);
    }

    @Override
    public void render(float delta) {
//        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClearColor(Constants.backgroundColor.r,  Constants.backgroundColor.g, Constants.backgroundColor.b, Constants.backgroundColor.a);
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
        label.setText("Best: " + MyPreference.getInstance().getHighestScore());
    }

    private void rePlay(){
            game.changeMainGameScreen();
    }

    private boolean validOneMoreTime() {
        return MyPreference.getInstance().getMoney() >= Constants.MONEY_FOR_REPLAY;
    }

    private void changeMenuScreen(){
        game.changeMenuScreen();
    }

    private void oneMoreTime() {
        if (validOneMoreTime()){
            // minus values to play once more time
            MyPreference pre =  MyPreference.getInstance();
            pre.setMoney(pre.getMoney() - Constants.MONEY_FOR_REPLAY);
            game.oneMoreTime();
        }
//        game.oneMoreTime();
    }
}
