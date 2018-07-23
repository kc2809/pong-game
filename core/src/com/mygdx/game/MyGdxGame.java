package com.mygdx.game;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetManager;
import com.mygdx.game.core.Assets;
import com.mygdx.game.screens.GameOverScreen;
import com.mygdx.game.screens.MainGameScreen;
import com.mygdx.game.screens.MenuScreen;

public class MyGdxGame extends Game {

	GameOverScreen gameOverScreen;
	MenuScreen menuScreen;
	MainGameScreen mainGameScreen;

	@Override
	public void create () {
		Assets.instance.init(new AssetManager());
//		setScreen(new MainGameScreen());
//		setScreen(menuScreen);
		gameOverScreen = new GameOverScreen(this);
		menuScreen = new MenuScreen(this);
		mainGameScreen = new MainGameScreen(this);
		changeMenuScreen();
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
