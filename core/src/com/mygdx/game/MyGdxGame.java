package com.mygdx.game;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.utils.viewport.ExtendViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.mygdx.game.core.Assets;
import com.mygdx.game.object.MyPreference;
import com.mygdx.game.screens.GameOverScreen;
import com.mygdx.game.screens.MainGameScreen;
import com.mygdx.game.screens.MenuScreen;
import com.mygdx.game.util.Constants;

public class MyGdxGame extends Game {
	Viewport viewport;
	OrthographicCamera camera;

	GameOverScreen gameOverScreen;
	MenuScreen menuScreen;
	MainGameScreen mainGameScreen;

	@Override
	public void create () {
		init();
		Assets.instance.init(new AssetManager());
		MyPreference.getInstance();
//		setScreen(new MainGameScreen());
//		setScreen(menuScreen);
		gameOverScreen = new GameOverScreen(this);
		menuScreen = new MenuScreen(this, camera, viewport);
		mainGameScreen = new MainGameScreen(this, camera, viewport);
		changeMenuScreen();
//		changeMainGameScreen();
	}

	private void init() {
		camera = new OrthographicCamera();
		viewport = new ExtendViewport(Constants.VIEWPORT_WIDTH, Constants.VIEWPORT_HEIGHT, Constants.VIEWPORT_WIDTH, 50, camera);

	}

	@Override
	public void render () {
		super.render();
	}

	@Override
	public void resize(int width, int height) {
		super.resize(width, height);
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
	public void dispose () {
		super.dispose();
		Assets.instance.dispose();
	}

	public void changeMainGameScreen() {
//		MainGameScreen mainGameScreen = new MainGameScreen(this);
		mainGameScreen.resize(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
		mainGameScreen.reset();
		setScreen(mainGameScreen);
	}

	public void changeMenuScreen() {
		setScreen(menuScreen);
	}

	public void changeGameOverScreen() {
		setScreen(gameOverScreen);
	}
}
