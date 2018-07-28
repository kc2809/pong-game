package com.mygdx.game.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
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
import com.mygdx.game.storage.MyPreference;
import com.mygdx.game.util.CommonUI;

import static com.mygdx.game.util.Constants.PPM;

public class GameOverScreen implements Screen {
    MyGdxGame game;
    Stage stage;

    OrthographicCamera camera;
    Viewport viewport;
    Label label;
    public GameOverScreen(MyGdxGame game, OrthographicCamera camera, Viewport viewPort) {
        this.game = game;
        this.camera = camera;
        this.viewport = viewPort;
        create();
    }

    private void create() {
        stage = new Stage(viewport);
//        final Skin skin = new Skin();
//
//        Label.LabelStyle style = new Label.LabelStyle(new BitmapFont(), Color.WHITE);
//        TextField.TextFieldStyle textFieldStyle = new TextField.TextFieldStyle();
//        textFieldStyle.fontColor = Color.WHITE;
//        textFieldStyle.font = new BitmapFont();
//
//        skin.add("default", style);
//        skin.add("default", textFieldStyle);
//        // Keep your code clean by creating widgets separate from layout.
//        Label nameLabel = new Label("Name:", skin);
//        TextField nameText = new TextField("", skin);
//        Label addressLabel = new Label("Address:", skin);
//        TextField addressText = new TextField("", skin);
//
//        Table table = new Table();
//        table.setFillParent(true);
//        table.add(nameLabel);              // Row 0, column 0.
//        table.add(nameText).width(100);    // Row 0, column 1.
//        table.row();                       // Move to next row.
//        table.add(addressLabel);           // Row 1, column 0.
//        table.add(addressText).width(100); // Row 1, column 1.
//
////        table.setOriginX(0);
////        table.setOriginY(0);
////        table.setX(200);
////        table.setY(200);
//        table.debug();
//
//        stage.addActor(table);

        LabelStyle style = new LabelStyle();
        style.font = Assets.instance.fontBig;
         label = new Label("best", style);
        label.setPosition(0, 0);
        stage.addActor(label);


        Button btn = CommonUI.getInstance().createImageButton(Assets.instance.getAsset(Assets.PLAY_ICON), null, null, new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                super.clicked(event, x, y);
                rePlay();
            }
        });
        btn.setSize(btn.getWidth() / PPM, btn.getHeight() / PPM);
        btn.setPosition(0, -1.0f);
        stage.addActor(btn);

        Button btn2 = CommonUI.getInstance().createImageButton(Assets.instance.getAsset(Assets.PAUSE_ICON), null, null, new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                super.clicked(event, x, y);
                changeMenuScreen();
            }
        });
        btn2.setSize(btn2.getWidth() / PPM, btn2.getHeight() / PPM);
        btn2.setPosition(0, -3.0f);
        stage.addActor(btn2);
    }
    @Override
    public void show() {
        setHighestScore();
        Gdx.input.setInputProcessor(stage);
    }

    @Override
    public void render(float delta) {
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

    private void changeMenuScreen(){
        game.changeMenuScreen();
    }
}
