package com.mygdx.game.screens;

import box2dLight.PointLight;
import box2dLight.RayHandler;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.World;
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
import com.mygdx.game.util.Constants;

import static com.mygdx.game.core.Assets.PLAY_ICON;
import static com.mygdx.game.core.Assets.STORE_ICON;
import static com.mygdx.game.core.Assets.VOLUMNE_ACTIVE_ICON;
import static com.mygdx.game.core.Assets.VOLUMNE_INACTIVE_ICON;
import static com.mygdx.game.util.Constants.PPM;

public class MenuScreen implements Screen {
    private static String TITLE = "Lighting-Ball";
    MyGdxGame game;
    Button playBtn;
    Button volumeBtn;
    Button storeBtn;

    Stage stage;
    BitmapFont font;

    OrthographicCamera camera;
    Viewport viewport;
    World world;
    RayHandler handler;
    //    Ball ball;
    GlyphLayout layout;

    Label label;

    PointLight pointLight;

    public MenuScreen(MyGdxGame game, OrthographicCamera camera, Viewport viewport) {
        this.game = game;
        this.camera = camera;
        this.viewport = viewport;
        camera.position.set(0, 0, 0);
        layout = new GlyphLayout();
        font = Assets.instance.titleFont;
        initComponent();
    }

    private void initComponent() {
        stage = new Stage(viewport);
        world = new World(new Vector2(0, 0), true);
        handler = new RayHandler(world);
        handler.setCombinedMatrix(camera);
        handler.setShadows(false);

        pointLight = new PointLight(handler, 1000, Color.BLUE, 7, 0, 0);
        setupLabel();
        setupButton();
    }

    private void setupLabel() {
        layout.setText(font, TITLE);
        LabelStyle labelStyle = new LabelStyle(font, Color.WHITE);
        label = new Label(TITLE, labelStyle);
        label.setPosition(-layout.width / 2, camera.viewportHeight / 3);
        stage.addActor(label);
    }

    private void setupButton() {
        createPlayButton();
        createVolumeButton();
        createStoreButton();
    }

    private void createPlayButton() {
        playBtn = CommonUI.getInstance().createImageButton(Assets.instance.getAsset(PLAY_ICON), null, null, new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                super.clicked(event, x, y);
                game.setAdViewVisibility(true);
                game.callAdmod();
                game.changeMainGameScreen();
            }
        });
        playBtn.setSize(playBtn.getWidth() * 4 / PPM, playBtn.getHeight() * 4 / PPM);
        playBtn.setPosition(camera.viewportWidth / 4, -camera.viewportHeight * 3 / 8);
        stage.addActor(playBtn);
    }

    private void createVolumeButton() {
        volumeBtn = CommonUI.getInstance().createImageButton(Assets.instance.getAsset(VOLUMNE_INACTIVE_ICON)
                , null
                , Assets.instance.getAsset(VOLUMNE_ACTIVE_ICON)
                , new ClickListener() {
                    @Override
                    public void clicked(InputEvent event, float x, float y) {
                        super.clicked(event, x, y);
                        MyPreference.getInstance().toggleSound();
                        volumeBtn.setChecked(MyPreference.getInstance().isSoundOn());
                    }
                });
        volumeBtn.setSize(volumeBtn.getWidth() * 4 / PPM, volumeBtn.getHeight() * 4 / PPM);
        volumeBtn.setPosition(-camera.viewportWidth / 2, -camera.viewportHeight * 3 / 8);
        volumeBtn.setChecked(MyPreference.getInstance().isSoundOn());
        stage.addActor(volumeBtn);
    }

    private void createStoreButton() {
        storeBtn = CommonUI.getInstance().createImageButton(Assets.instance.getAsset(STORE_ICON), null, null, new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                super.clicked(event, x, y);
                game.changeStoreScreen();
            }
        });
        storeBtn.setSize(storeBtn.getWidth() * 2 / PPM, storeBtn.getHeight() * 2 / PPM);
        storeBtn.setPosition(-camera.viewportWidth *18/ 40 , camera.viewportHeight *2 / 10);
        stage.addActor(storeBtn);
    }

    @Override
    public void show() {
        Gdx.input.setInputProcessor(stage);

        init();
    }

    private void init() {

    }

    @Override
    public void render(float delta) {
        world.step(1f / 60f, 6, 2);
        camera.update();
//        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClearColor(Constants.backgroundColor.r,  Constants.backgroundColor.g, Constants.backgroundColor.b, Constants.backgroundColor.a);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        stage.act(delta);
        stage.draw();

        handler.updateAndRender();
        update();
    }

    private void update() {
        float y = (float) (1 * Math.cos(2 * Math.PI * 0.0005 * System.currentTimeMillis())) + 1;
//        ball.setPosition(0, y);
        pointLight.setPosition(0, y);
    }

    @Override
    public void resize(int width, int height) {
        viewport.update(width, height);
        camera.position.set(0, 0, 0);
        handler.setCombinedMatrix(camera);
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
