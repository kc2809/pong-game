package com.mygdx.game.screens;

import box2dLight.PointLight;
import box2dLight.RayHandler;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Label.LabelStyle;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.mygdx.game.MyGdxGame;
import com.mygdx.game.core.Assets;

import static com.mygdx.game.util.Constants.PPM;
import static com.mygdx.game.util.Constants.VIEWPORT_WIDTH;

public class MenuScreen implements Screen {
    private static String TITLE = "K BALL";
    MyGdxGame game;
    Button button;
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

        pointLight = new PointLight(handler, 1000, Color.SKY, 6, 0, 0);
        setupLabel();
        setupButton();

//        Object2D object2D = new Object2D();
//        object2D.initTexture(new Texture(Gdx.files.internal("playbtn1k.png")));
//        object2D.setPosition(camera.viewportWidth /4, -camera.viewportHeight * 3 /8);
//        stage.addActor(object2D);
    }

    private void setupLabel() {
        layout.setText(font, TITLE);
        LabelStyle labelStyle = new LabelStyle(font, Color.WHITE);
        label = new Label(TITLE, labelStyle);
        label.setPosition(-VIEWPORT_WIDTH / 2 + layout.width, camera.viewportHeight / 2 - 1.0f);
        stage.addActor(label);
    }

    private void setupButton() {
        Texture playTexture = new Texture(Gdx.files.internal("playbtn1k.png"));
        Drawable drawable = new TextureRegionDrawable(new TextureRegion(playTexture));
        button = new ImageButton(drawable);
        button.setSize(playTexture.getWidth() * 2/ PPM, playTexture.getHeight() * 2/ PPM);
        stage.addActor(button);
        button.setPosition(camera.viewportWidth /4, -camera.viewportHeight * 3 /8);
        button.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                super.clicked(event, x, y);
                game.changeMainGameScreen();
            }
        });
    }

    @Override
    public void show() {
//        stage = new Stage();
//        Skin skin = new Skin();
//        font = new BitmapFont();
//        TextureAtlas buttonAtlas = new TextureAtlas(Gdx.files.internal("number.pack"));
//        skin.addRegions(buttonAtlas);
//        TextButtonStyle textButtonStyle = new TextButtonStyle();
//        textButtonStyle.font = font;
//        textButtonStyle.up = skin.getDrawable("num0");
//        textButtonStyle.down = skin.getDrawable("num1");
//        button = new TextButton("Button1", textButtonStyle);
//
//        button.setPosition(Gdx.graphics.getWidth() / 2, Gdx.graphics.getHeight() / 2);
//
//        button.addListener(new ClickListener() {
//            @Override
//            public void clicked(InputEvent event, float x, float y) {
//                super.clicked(event, x, y);
//                System.out.println("cllicked");
//            }
//
//            @Override
//            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
//
//                return super.touchDown(event, x, y, pointer, button);
//            }
//
//            @Override
//            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
//
//                game.changeMainGameScreen();
//                super.touchUp(event, x, y, pointer, button);
//            }
//        });
//        stage.addActor(button);
        Gdx.input.setInputProcessor(stage);

        init();
    }

    private void init() {

    }

    @Override
    public void render(float delta) {
        world.step(1f / 60f, 6, 2);
        camera.update();
        Gdx.gl.glClearColor(0, 0, 0, 1);
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
