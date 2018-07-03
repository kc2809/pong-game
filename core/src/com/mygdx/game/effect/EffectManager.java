package com.mygdx.game.effect;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.ParticleEffect;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.mygdx.game.core.Assets;

import java.util.ArrayList;
import java.util.List;

public class EffectManager extends Actor {

    List<ParticleEffect> effects;

    public EffectManager() {
        this.effects = new ArrayList<ParticleEffect>();
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
//        effects.forEach(effect -> effect.draw(batch));
        for (ParticleEffect effect : effects) {
            effect.draw(batch);
        }
    }

    @Override
    public void act(float delta) {
        super.act(delta);
//        effects.forEach(particleEffect -> particleEffect.update(delta));
        for (ParticleEffect particleEffect : effects) {
            particleEffect.update(delta);
        }
    }

    public void setEffectAtPosition(float x, float y) {
//        Optional<ParticleEffect> optinalEffect = effects.stream().filter(ParticleEffect::isComplete).findAny();
//        if (!optinalEffect.isPresent()) {
//            effects.add(newParticleEffectAtPosition(x, y));
//        }
//        optinalEffect.ifPresent(particleEffect -> {
//            particleEffect.setPosition(x, y);
//            particleEffect.start();
//        });

        ParticleEffect optinalEffect = null;
        for (int i = 0; i < effects.size(); ++i) {
            if (effects.get(i).isComplete()) {
                optinalEffect = effects.get(i);
                break;
            }
        }

        if (optinalEffect != null) {
            optinalEffect.setPosition(x, y);
            optinalEffect.start();
        } else {
            effects.add(newParticleEffectAtPosition(x, y));
        }
    }

    private ParticleEffect newParticleEffectAtPosition(float x, float y) {
        ParticleEffect pe = new ParticleEffect();
        pe.load(Assets.instance.effectFile, Assets.instance.imagesDir);
        pe.getEmitters().first().setPosition(x, y);
        pe.scaleEffect(1.0f / 150f);
        pe.start();
        return pe;
    }

}
