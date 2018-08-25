package com.mygdx.game.screens;


import box2dLight.PointLight;
import box2dLight.RayHandler;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.viewport.ExtendViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.mygdx.game.MyGdxGame;
import com.mygdx.game.core.Assets;
import com.mygdx.game.object.ItemLight;
import com.mygdx.game.storage.MyPreference;
import com.mygdx.game.util.CommonUI;
import com.mygdx.game.util.Constants;

import static com.mygdx.game.util.Constants.PPM;


public class StoreScreen implements Screen, InputProcessor {
    private static float WIDTH = 10.0f;

    OrthographicCamera camera;
    Viewport viewport;
    World world;
    RayHandler handler;
    Stage stage;
    Label label;
    MyGdxGame game;

    PointLight pointLight;

    public StoreScreen(MyGdxGame game) {
        this.game = game;
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

        Color color = MyPreference.getInstance().getCurrentColor();

        pointLight = new PointLight(handler, 1000, color, 4, -2.0f, camera.viewportHeight * 2 / 5);
        Label.LabelStyle style = new Label.LabelStyle();
        style.font = Assets.instance.fontBig;
        label = new Label(MyPreference.getInstance().getMoney() + "", style);
        label.setPosition(camera.viewportWidth / 2 - 3.0f, camera.viewportHeight / 2 - 1.5f);
        stage.addActor(label);

        Image image = new Image(Assets.instance.getAsset(Assets.MONEY_ITEM));
        image.setSize(2.0f, 2.0f);
        image.setPosition(camera.viewportWidth / 2 - 2.0f, camera.viewportHeight / 2 - 2.0f);
        stage.addActor(image);
        float x, y;
        for (int i = 0; i < 3; ++i) {
            y = camera.viewportHeight * 1 / 10 - 3.0f * i;
            for (int j = 0; j < 3; ++j) {
                x = -4.3f + 3.7f * j;
                ItemLight s11 = new ItemLight(this, handler, Constants.colors[i * 3 + j]);
                s11.setPosition(x, y);
                stage.addActor(s11);
            }
        }

        createBackButton();
        createAdmobutton();
    }

    private void createAdmobutton(){
        Button btnAdmob = CommonUI.getInstance().createImageButton(Assets.instance.getAsset(Assets.VIDEO_REWARDED_ICON), null,
                null, new ClickListener() {
                    @Override
                    public void clicked(InputEvent event, float x, float y) {
                        super.clicked(event, x, y);
                        System.out.println("cai lz j vay");
                        game.callVideoAd();
                    }
                });
        btnAdmob.setSize(btnAdmob.getWidth() * 4.0f / PPM, btnAdmob.getHeight() * 4.0f / PPM);
        btnAdmob.setPosition(0.0f, camera.viewportHeight *2/8);
        stage.addActor(btnAdmob);
    }

    private void createBackButton() {
        Image image = new Image(Assets.instance.getAsset(Assets.BACK_ICON));
        image.setSize(1.0f, 1.0f);
        image.setPosition(-camera.viewportWidth / 2, camera.viewportHeight / 2 - 2.0f);
        image.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                super.clicked(event, x, y);
                game.changeMenuScreen();
            }
        });
        stage.addActor(image);
    }

    @Override
    public void show() {
//        PointLight pointLight = new PointLight(handler, 1000, Color.SKY, 3, 0, 0);
        setMoneyLabel();
        InputMultiplexer multiplexer = new InputMultiplexer();
        multiplexer.addProcessor(stage);
        multiplexer.addProcessor(this);

        Gdx.input.setInputProcessor(multiplexer);
    }

    @Override
    public void render(float delta) {
        world.step(1f / 60f, 6, 2);
        camera.update();
//        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClearColor(Constants.backgroundColor.r,  Constants.backgroundColor.g, Constants.backgroundColor.b, Constants.backgroundColor.a);
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

    public void updateLabel(Color color) {
        MyPreference pre = MyPreference.getInstance();
        pre.setMoney(pre.getMoney() - 200);
        label.setText(pre.getMoney() + "");
        pre.soldColor(color);
        setPointLightColor(color);
    }

    public void setMoneyLabel(){
        label.setText(MyPreference.getInstance().getMoney() + "");
    }

    public boolean checkValidMoney() {
        return MyPreference.getInstance().getMoney() > 200;
    }

    public void setPointLightColor(Color color) {
        pointLight.setColor(color);
        MyPreference.getInstance().setCurrentColor(color);
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

    @Override
    public boolean keyDown(int keycode) {
        if (keycode == Keys.BACK) {
            System.out.println("back happened");
            return true;
        }
        return false;
    }

    @Override
    public boolean keyUp(int keycode) {
        return false;
    }

    @Override
    public boolean keyTyped(char character) {
        return false;
    }

    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {
        return false;
    }

    @Override
    public boolean touchUp(int screenX, int screenY, int pointer, int button) {
        return false;
    }

    @Override
    public boolean touchDragged(int screenX, int screenY, int pointer) {
        return false;
    }

    @Override
    public boolean mouseMoved(int screenX, int screenY) {
        return false;
    }

    @Override
    public boolean scrolled(int amount) {
        return false;
    }
}
