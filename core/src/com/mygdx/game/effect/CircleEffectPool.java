package com.mygdx.game.effect;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.ParticleEffect;
import com.badlogic.gdx.graphics.g2d.ParticleEffectPool;
import com.badlogic.gdx.graphics.g2d.ParticleEffectPool.PooledEffect;

public class CircleEffectPool {

    public static CircleEffectPool instance;

    private ParticleEffectPool pool;

    private CircleEffectPool() {
        ParticleEffect prototype = new ParticleEffect();
        prototype.load(Gdx.files.internal("effect4.party"), Gdx.files.internal(""));
        prototype.scaleEffect(1.0f / 250f);
        prototype.start();

        pool = new ParticleEffectPool(prototype, 0, 20);
    }

    public static CircleEffectPool getInstance() {
        if (instance == null) {
            instance = new CircleEffectPool();
        }
        return instance;
    }

    public PooledEffect getCircleEffect() {
        return pool.obtain();
    }

    public void freeCircleEffect(PooledEffect pooledEffect) {
        pooledEffect.free();
    }
}
