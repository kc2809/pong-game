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
import com.badlogic.gdx.utils.viewport.ExtendViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.mygdx.game.object.ItemLight;

public class StoreScreen implements Screen {
    private static float WIDTH = 9;

    OrthographicCamera camera;
    Viewport viewport;
    World world;
    RayHandler handler;
    Stage stage;

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

        ItemLight s = new ItemLight(handler, Color.RED);
        s.setPosition(0,0);
        stage.addActor(s);
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
