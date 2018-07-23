package com.mygdx.game.effect;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.ParticleEffect;
import com.badlogic.gdx.graphics.g2d.ParticleEffectPool;
import com.badlogic.gdx.graphics.g2d.ParticleEffectPool.PooledEffect;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.utils.Array;
import com.mygdx.game.core.Assets;

import java.util.List;

public class EffectManager extends Actor {

    List<ParticleEffect> effects;

    private ParticleEffect prototype;
    private ParticleEffectPool pool;
    private Array<PooledEffect> poolEffects;


    public EffectManager() {

        prototype = new ParticleEffect();
        prototype.load(Assets.instance.effectFile, Assets.instance.imagesDir);
        prototype.scaleEffect(1.0f / 150f);

        prototype.start();

        pool = new ParticleEffectPool(prototype, 1, 30);
        poolEffects = new Array<>();

        setupCirclePool();
    }

    private void setupCirclePool() {
        ParticleEffect ciclePrototype = new ParticleEffect();
        ciclePrototype.load(Gdx.files.internal("effect4.party"), Gdx.files.internal(""));
        ciclePrototype.scaleEffect(1.0f / 250f);
        ciclePrototype.start();
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        for (PooledEffect effect : poolEffects) {
            effect.draw(batch, Gdx.graphics.getDeltaTime());
            if (effect.isComplete()) {
                poolEffects.removeValue(effect, true);
                effect.free();
            }
        }
    }

    public void setEffectAtPosition(float x, float y, Color color) {

        PooledEffect effect = pool.obtain();
        prototype.getEmitters().first().setPosition(x, y);

        effect.setPosition(x, y);
        poolEffects.add(effect);
        setColor(effect, color);
        effect.start();
    }

    private void setColor(ParticleEffect pe, Color color) {
        for (int i = 0; i < pe.getEmitters().size; ++i) {
            pe.getEmitters().get(i).getTint().setColors(new float[]{color.r, color.g, color.b, color.a});
        }
    }


}
