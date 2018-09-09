package com.mygdx.game;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.utils.viewport.ExtendViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.mygdx.game.core.Assets;
import com.mygdx.game.screens.*;
import com.mygdx.game.storage.MyPreference;
import com.mygdx.game.util.Constants;

public class MyGdxGame extends Game {
    Viewport viewport;
    OrthographicCamera camera;

    GameOverScreen gameOverScreen;
    MenuScreen menuScreen;
    MainGameScreen mainGameScreen;
    StoreScreen storeScreen;
    PausedScreen pausedScreen;

    AdmodCallBack callback;

    int numberToCallAdmobbanner = 0;

    public void setAdmobCallBack(AdmodCallBack callback) {
        this.callback = callback;
    }

    @Override
    public void create() {
        init();
        Assets.instance.init(new AssetManager());
        MyPreference.getInstance();
        initScreen();
        changeMenuScreen();
//		changeMainGameScreen();
//        setScreen(gameOverScreen);
//		setScreen(storeScreen);
//		setScreen(pausedScreen);
        // catch back key
        Gdx.input.setCatchBackKey(true);
    }

    private void initScreen() {
        gameOverScreen = new GameOverScreen(this, camera, viewport);
        menuScreen = new MenuScreen(this, camera, viewport);
        mainGameScreen = new MainGameScreen(this, camera, viewport);
        storeScreen = new StoreScreen(this);
        pausedScreen = new PausedScreen(this, camera, viewport);
    }

    private void init() {
        camera = new OrthographicCamera();
        viewport = new ExtendViewport(Constants.VIEWPORT_WIDTH, Constants.VIEWPORT_HEIGHT, Constants.VIEWPORT_WIDTH,
				50, camera);
    }

    @Override
    public void render() {
        super.render();
    }

    @Override
    public void resize(int width, int height) {
        super.resize(width, height);
        viewport.update(width, height);
        camera.position.set(0, 0, 0);
    }

    @Override
    public void pause() {
        super.pause();
    }

    @Override
    public void resume() {
        super.resume();
    }

    @Override
    public void dispose() {
        super.dispose();
//        System.out.println("GAME DISPOSE");
        Assets.instance.dispose();
        System.exit(0);
    }

    public void changeMainGameScreen() {
//		MainGameScreen mainGameScreen = new MainGameScreen(this);
        mainGameScreen.resize(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        mainGameScreen.reset();
        setScreen(mainGameScreen);
    }

    public void resumeMainGameScreen() {
        mainGameScreen.resize(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        setScreen(mainGameScreen);
    }

    public void changeMenuScreen() {
        setScreen(menuScreen);
    }

    public void changeGameOverScreen() {
        setScreen(gameOverScreen);
    }

    public void changeStoreScreen() {
        storeScreen.resize(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        setScreen(storeScreen);
    }

    public void changePausedScreen() {
        setScreen(pausedScreen);
    }

    public void oneMoreTime() {
        mainGameScreen.replay();
        mainGameScreen.resize(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        setScreen(mainGameScreen);
    }

    public void setScore(int score) {
        gameOverScreen.setScore(score);
    }

    public void callAdmod() {
        if (callback != null) {
            if (++numberToCallAdmobbanner % 3 == 0)
                callback.callAdmobBanner();
        }
    }

    public void callVideoAd() {
        if (callback != null) callback.callVideo();
    }

    public void setAdViewVisibility(boolean visible) {
        if (callback != null) callback.setAdViewVisibility(visible);
    }

    public void rewardUser() {
        MyPreference.getInstance().setMoney(MyPreference.getInstance().getMoney() + 20);
        gameOverScreen.setCurrentMoney();
        storeScreen.setMoneyLabel();
    }

    public void showToastMessage(String s){
        callback.showToastMessage(s);
    }

    public interface AdmodCallBack {
        void callAdmobBanner();

        void callVideo();

        void setAdViewVisibility(final boolean visible);

        void showToastMessage(String s);
    }

}
