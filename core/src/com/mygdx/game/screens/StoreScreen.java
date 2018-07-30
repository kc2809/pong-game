package com.mygdx.game.screens;


import box2dLight.PointLight;
import box2dLight.RayHandler;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.utils.viewport.ExtendViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.mygdx.game.core.Assets;
import com.mygdx.game.object.ItemLight;


public class StoreScreen implements Screen {
    private static float WIDTH = 10.0f;

    OrthographicCamera camera;
    Viewport viewport;
    World world;
    RayHandler handler;
    Stage stage;

    Color[] colors = {Color.CYAN, Color.PURPLE, Color.YELLOW
            , Color.FIREBRICK, Color.MAGENTA, Color.ORANGE, Color.SKY,
            Color.LIME, Color.WHITE};

    Button btn;

    public StoreScreen() {
        init();
    }

    private void init() {
        camera = new OrthographicCamera();
        viewport = new ExtendViewport(WIDTH, WIDTH, WIDTH, 50, camera);
        stage = new Stage(viewport);
        world = new World(new Vector2(0, 0), true);
        handler = new RayHandler(world);
        handler.setCombinedMatrix(camera);
        handler.setShadows(false);


        new PointLight(handler, 1000, Color.WHITE, 4, -2.0f, camera.viewportHeight * 2 / 5);
//        ItemLight s = new ItemLight(handler, Color.RED);
//        s.setPosition(-0.5f,0);
//        ItemLight s1 = new ItemLight(handler, Color.CYAN);
//        s1.setPosition(-4.5f,0);
//        ItemLight s2 = new ItemLight(handler, Color.YELLOW);
//        s2.setPosition(3.0f,0);

        float x, y;
        for (int i = 0; i < 3; ++i) {
            y = camera.viewportHeight * 1 / 10 - 3.0f * i;
            for (int j = 0; j < 3; ++j) {
                x = -4.3f + 3.7f * j;
                ItemLight s11 = new ItemLight(handler, colors[i * 3 + j]);
                s11.setPosition(x, y);
                stage.addActor(s11);
            }
        }

        Label.LabelStyle style = new Label.LabelStyle();
        style.font = Assets.instance.fontMedium;
        Label label = new Label("fuck", style);
        label.setPosition(camera.viewportWidth/2 -3.0f,  camera.viewportHeight/2 - 1.5f);
        stage.addActor(label);

        Image image = new Image(Assets.instance.getAsset(Assets.MONEY_ITEM));
        image.setSize(2.0f, 2.0f);
        image.setPosition(camera.viewportWidth/2 -2.0f,  camera.viewportHeight/2 - 2.0f);
        stage.addActor(image);
//        stage.addActor(s);
//        stage.addActor(s1);
//        stage.addActor(s2);
    }

    public void show() {
//        PointLight pointLight = new PointLight(handler, 1000, Color.SKY, 3, 0, 0);
        Gdx.input.setInputProcessor(stage);
    }

    @Override
    public void render(float delta) {
        world.step(1f / 60f, 6, 2);
        camera.update();
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        stage.draw();
        handler.updateAndRender();
    }

    @Override
    public void resize(int width, int height) {
        viewport.update(width, height);
        viewport.apply();
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
