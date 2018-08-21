package com.mygdx.game.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Label.LabelStyle;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.mygdx.game.MyGdxGame;
import com.mygdx.game.core.Assets;
import com.mygdx.game.util.CommonUI;
import com.mygdx.game.util.Constants;

import static com.mygdx.game.util.Constants.PPM;

public class PausedScreen implements Screen {
    MyGdxGame game;
    Stage stage;

    OrthographicCamera camera;
    Viewport viewport;

    public PausedScreen(MyGdxGame game, OrthographicCamera camera, Viewport viewPort) {
        this.game = game;
        this.camera = camera;
        this.viewport = viewPort;
        create();
    }

    private void create() {
        stage = new Stage(viewport);
        Button btn = CommonUI.getInstance().createImageButton(Assets.instance.getAsset(Assets.PLAY_ICON), null, null, new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                super.clicked(event, x, y);
                resumeMainGameScreen();
            }
        });
        btn.setSize(btn.getWidth() * 3 / PPM, btn.getHeight() * 3 / PPM);
        btn.setPosition(-btn.getWidth() - 1.0f, btn.getHeight());
        stage.addActor(btn);

        // create label
        LabelStyle style = new LabelStyle();
        style.font = Assets.instance.fontBig;
        Label label = new Label("RESUME", style);
        label.setPosition(btn.getX() + 2.0f, btn.getY());
        stage.addActor(label);

        //create quit label
        Button btnMenu = CommonUI.getInstance().createImageButton(Assets.instance.getAsset(Assets.MENU_ICON), null, null, new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                super.clicked(event, x, y);
                changeMenu();
            }
        });
        btnMenu.setSize(btnMenu.getWidth() * 3 / PPM, btnMenu.getHeight() * 3 / PPM);
        btnMenu.setPosition(-btnMenu.getWidth() - 1.0f, -btnMenu.getHeight());
        stage.addActor(btnMenu);

        // create label
        Label quitLabel = new Label("MENU", style);
        quitLabel.setPosition(btnMenu.getX() + 2.0f, btnMenu.getY());
        stage.addActor(quitLabel);
    }

    private void resumeMainGameScreen() {
        game.resumeMainGameScreen();
    }

    private void changeMenu() {
        game.changeMenuScreen();
    }

    @Override
    public void show() {
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
}
