package com.mygdx.game.object;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.mygdx.game.core.Assets;
import com.mygdx.game.core.Text;
import com.mygdx.game.screens.MainGameScreen;
import com.mygdx.game.util.Constants;

import static com.mygdx.game.core.Assets.MONEY_ITEM;
import static com.mygdx.game.util.Constants.PPM;

public class UIObjects extends Actor {

    BitmapFont fontBig;
    BitmapFont fontMedium;
    MainGameScreen screen;

    Sprite moneyIcon;

    Text moneyText;
    Text scoreText;
    GlyphLayout layout;
    GlyphLayout layoutScore;


    public UIObjects(MainGameScreen screen) {
        this.screen = screen;
        initObjects();
        setMoneyText(screen.money);
        setScoreText(0);
    }

    private void initObjects() {
        fontBig = Assets.instance.fontBig;
        fontMedium = Assets.instance.fontMedium;
        layout = new GlyphLayout();
        layoutScore = new GlyphLayout();

        moneyIcon = new Sprite(Assets.instance.getAsset(MONEY_ITEM));
        moneyIcon.setSize(moneyIcon.getWidth() / (1.5f * PPM), moneyIcon.getHeight() / (1.5f * PPM));
        moneyText = new Text();
        scoreText = new Text();
    }

    @Override
    public void setPosition(float x, float y) {
        super.setPosition(x, y);
        moneyText.setPosition(getX() + Constants.VIEWPORT_WIDTH * 7 / 20, getY());
        moneyIcon.setPosition(getX() + Constants.VIEWPORT_WIDTH * 7 / 20, getY() - (moneyIcon.getHeight() * 1.35f));
        scoreText.setPosition(getX(), getY());
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        fontMedium.draw(batch, moneyText.getText(), moneyText.x - layout.width / 1.5f, moneyText.y - layout.height / 1.5f);
        fontBig.draw(batch, scoreText.getText(), scoreText.x - layoutScore.width / 2, scoreText.y - layout.height / 2);
        moneyIcon.draw(batch);
    }

    @Override
    public void act(float delta) {
        super.act(delta);
    }

    public void increaseMoney() {
        setMoneyText(++screen.money);
    }

    public void increaseScore() {
        setScoreText(++screen.score);
    }

    public void updateCurrentMoney(){
        setMoneyText(screen.money);
    }

    public void setMoneyText(int money) {
        moneyText.setText(money);
        layout.setText(fontBig, moneyText.getText());
    }

    public void setScoreText(int score) {
        scoreText.setText(score);
        layoutScore.setText(fontBig, scoreText.getText());
    }

}
