package com.mygdx.game.object;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.mygdx.game.core.Assets;
import com.mygdx.game.screens.MainGameScreen;
import com.mygdx.game.util.MathUtils;

import java.util.Random;

import static com.mygdx.game.util.Constants.BALL_PHYSIC;
import static com.mygdx.game.util.Constants.RATIO_TO_DUPLICATE_VALUE;
import static com.mygdx.game.util.Constants.WORLD_PHYSIC;

public class Square extends ObjectBox2d {

    MainGameScreen screen;

    int value;
    Random r = new Random();

    BitmapFont font;
    GlyphLayout layout;
    StringBuilder valueBuilder;

    private float getColorRandomValue(){
        return r.nextInt(255) / 255.0f;
    }

    public Square(MainGameScreen screen, World world, float x, float y) {
        super(world, Assets.instance.square, x, y);
        this.screen = screen;
//        value = r.nextInt(screen.currentLevel) +1;
        value = generateValueByLevel(screen.currentLevel);
        font = Assets.instance.fontSmall;
        sprite.setColor(getColorRandomValue(), getColorRandomValue(), getColorRandomValue(), 1);

        valueBuilder = new StringBuilder();
        layout = new GlyphLayout();
        setValueBuilder();
    }



    @Override
    public void createPhysics() {
        if (isWorldLock()) return;
        BodyDef bodyDef = new BodyDef();
        bodyDef.type = BodyType.StaticBody;
        bodyDef.position.set(sprite.getX() + sprite.getWidth() / 2, sprite.getY() + sprite.getHeight() / 2);

        body = world.createBody(bodyDef);

        PolygonShape shape = new PolygonShape();
        //  shape.setAsBox(sprite.getWidth()/2 , sprite.getHeight() /2 );
        shape.setAsBox(sprite.getWidth() / 2, sprite.getHeight() / 2);

        FixtureDef fixtureDef = new FixtureDef();
        fixtureDef.shape = shape;
        fixtureDef.density = 0.1f;
        fixtureDef.filter.categoryBits = WORLD_PHYSIC;
        fixtureDef.filter.maskBits = BALL_PHYSIC;

        body.createFixture(fixtureDef);
        shape.dispose();
        body.setUserData(this);
    }


    @Override
    public void draw(Batch batch, float parentAlpha) {
        super.draw(batch, parentAlpha);
//        layout.setText(fontSmall, );
        font.draw(batch, valueBuilder, getCenterPostionX() - layout.width / 2, getCenterPostionY() + layout.height / 2);
    }

    @Override
    public void act(float delta) {
        super.act(delta);
        sprite.setSize(this.getScaleX(), this.getScaleY());
    }

    @Override
    public boolean remove() {
//        screen.uiObject.increaseMoney();
        screen.increaseScore();
        return super.remove();
    }

    public void descreaseValue() {
        value--;
        setValueBuilder();
        if (value != 0) {
            addCollisionEffect();
            return;
        }
        screen.setEffectAtPosition(body.getPosition());
        this.remove();
    }

    private int generateValueByLevel(int level) {
        return MathUtils.instance.ratio(RATIO_TO_DUPLICATE_VALUE) ? level * 2 : level;
    }

    public void addCollisionEffect(){
        this.addAction(Actions.sequence(Actions.scaleTo(1.05f,1.05f,0.005f), Actions.scaleTo(1.0f,1.0f,0.005f)));
    }

    private void setValueBuilder() {
        valueBuilder.setLength(0);
        valueBuilder.append(value);
        layout.setText(font, valueBuilder);
    }
}
