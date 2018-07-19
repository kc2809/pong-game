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

        pool = new ParticleEffectPool(prototype, 1, 500);
        poolEffects = new Array<>();
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
//        effects.forEach(effect -> effect.draw(batch));
//        for (ParticleEffect effect : effects) {
//               if(!effect.isComplete()) effect.draw(batch);
//        }
        for (PooledEffect effect : poolEffects) {
            effect.draw(batch, Gdx.graphics.getDeltaTime());
            if (effect.isComplete()) {
                poolEffects.removeValue(effect, true);
                effect.free();
            }
        }
    }

    @Override
    public void act(float delta) {
        super.act(delta);
//        effects.forEach(particleEffect -> particleEffect.update(delta));
//        for (ParticleEffect effect : effects) {
//            effect.update(delta);
//        }

//        for(PooledEffect effect: poolEffects){
//            effect.update(delta);
//        }
    }

    public void setEffectAtPosition(float x, float y, Color color) {
//        Optional<ParticleEffect> optinalEffect = effects.stream().filter(ParticleEffect::isComplete).findAny();
//        if (!optinalEffect.isPresent()) {
//            effects.add(newParticleEffectAtPosition(x, y));
//            return;
//        }
//        optinalEffect.ifPresent(particleEffect -> {
//            optinalEffect.get().reset(false);
//            setColor(optinalEffect.get(), color);
//            optinalEffect.get().setPosition(x, y);
////            particleEffect.setPosition(x, y);
////            particleEffect.start();
//        });

//        ParticleEffect optinalEffect = null;
//        for (ParticleEffect effect : effects) {
//            if (effect.isComplete()) {
//                optinalEffect = effect;
//                break;
//            }
//        }
////
//        if (optinalEffect != null) {
////            optinalEffect.reset(false);
//          //  setColor(optinalEffect, color);
//            optinalEffect.setPosition(x, y);
//            optinalEffect.start();
//        } else {
//            effects.add(newParticleEffectAtPosition(x, y));
//        }

        PooledEffect effect = pool.obtain();
        prototype.getEmitters().first().setPosition(x, y);

        effect.setPosition(x, y);
        poolEffects.add(effect);
        setColor(effect, color);
        effect.start();
    }

    private ParticleEffect newParticleEffectAtPosition(float x, float y) {
        ParticleEffect pe = new ParticleEffect();
        pe.load(Assets.instance.effectFile, Assets.instance.imagesDir);
        pe.scaleEffect(1.0f / 150f);
        pe.start();

//        ParticleEffect pe = particleEffectFactory();
//        pe.getEmitters().first().setPosition(x, y);
//        pe.scaleEffect(1.0f / 150f);
//        pe.start();
        return pe;
    }

    private ParticleEffect particleEffectFactory(){
        ParticleEffect pe = new ParticleEffect();
        pe.load(Assets.instance.effectFile, Assets.instance.imagesDir);
        pe.scaleEffect(1.0f / 150f);

        return pe;
    }

    private void setColor(ParticleEffect pe, Color color) {
        for (int i = 0; i < pe.getEmitters().size; ++i) {
            pe.getEmitters().get(i).getTint().setColors(new float[]{color.r, color.g, color.b, color.a});
        }
    }


}
