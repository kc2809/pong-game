package com.mygdx.game.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label.LabelStyle;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.mygdx.game.MyGdxGame;
import com.mygdx.game.core.Assets;
import com.mygdx.game.util.CommonUI;
import com.mygdx.game.util.Constants;

public class PausedScreen implements Screen {
    private static final String REPLAY_TITLE = "RESUME";
    private static final String MENU_TITLE = "MAIN MENU";

    MyGdxGame game;
    Stage stage;

    OrthographicCamera camera;
    Viewport viewport;

    public PausedScreen(MyGdxGame game, OrthographicCamera camera, Viewport viewPort) {
        this.game = game;
        this.camera = camera;
        this.viewport = viewPort;
        createButton();
    }

    private void createButton() {
        stage = new Stage(viewport);
        LabelStyle styleBtn = new LabelStyle();
        styleBtn.font = Assets.instance.fontBig;
        Group btnResume = CommonUI.getInstance().createBox(0, 1.0f, styleBtn, REPLAY_TITLE,
                Constants.customRed, new ClickListener() {
                    @Override
                    public void clicked(InputEvent event, float x, float y) {
                        super.clicked(event, x, y);
                        resumeMainGameScreen();
                    }
                });
        stage.addActor(btnResume);

        Group btnMainMenu = CommonUI.getInstance().createBox(0, -1.5f, styleBtn, MENU_TITLE, Constants.customBlue,
                new ClickListener() {
                    @Override
                    public void clicked(InputEvent event, float x, float y) {
                        super.clicked(event, x, y);
                        changeMenu();
                    }
                });
        stage.addActor(btnMainMenu);
    }

    private void resumeMainGameScreen() {
        game.resumeMainGameScreen();
    }

    private void changeMenu() {
        game.setAdViewVisibility(false);
        game.changeMenuScreen();
    }

    @Override
    public void show() {
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
}
