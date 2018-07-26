package com.mygdx.game.object;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.mygdx.game.core.Assets;
import com.mygdx.game.core.Text;
import com.mygdx.game.screens.MainGameScreen;
import com.mygdx.game.util.CommonUI;
import com.mygdx.game.util.Constants;

import static com.mygdx.game.core.Assets.MONEY_ITEM;
import static com.mygdx.game.core.Assets.PAUSE_ICON;
import static com.mygdx.game.util.Constants.PPM;

public class UIObjects extends Actor {

    BitmapFont fontBig;
    BitmapFont fontSmall;
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
        fontSmall = Assets.instance.fontSmall;
        layout = new GlyphLayout();
        layoutScore = new GlyphLayout();

        moneyIcon = new Sprite(Assets.instance.getAsset(MONEY_ITEM));
        moneyIcon.setSize(moneyIcon.getWidth() / (2 * PPM), moneyIcon.getHeight() / (2 * PPM));
        moneyText = new Text();
        scoreText = new Text();
    }

    @Override
    public void setPosition(float x, float y) {
        super.setPosition(x, y);
        moneyIcon.setPosition(getX() + Constants.VIEWPORT_WIDTH * 8 / 20, getY() - moneyIcon.getHeight() * 1.2f);
        moneyText.setPosition(getX() + Constants.VIEWPORT_WIDTH * 7 / 20, getY());
        scoreText.setPosition(getX(), getY());
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        fontBig.draw(batch, moneyText.getText(), moneyText.x - layout.width, moneyText.y - layout.height / 2);
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

    public void setMoneyText(int money) {
        moneyText.setText(money);
        layout.setText(fontBig, moneyText.getText());
    }

    public void setScoreText(int score) {
        scoreText.setText(score);
        layoutScore.setText(fontBig, scoreText.getText());
    }

}
