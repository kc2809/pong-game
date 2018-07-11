package com.mygdx.game.effect;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.ParticleEffect;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.mygdx.game.core.Assets;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class EffectManager extends Actor {

    List<ParticleEffect> effects;

    public EffectManager() {
        this.effects = new ArrayList<ParticleEffect>();
        for(int i=0;i<20;++i){
            effects.add(particleEffectFactory());
        }
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        effects.forEach(effect -> effect.draw(batch));
    }

    @Override
    public void act(float delta) {
        super.act(delta);
        effects.forEach(particleEffect -> particleEffect.update(delta));
    }

    public void setEffectAtPosition(float x, float y, Color color) {
        Optional<ParticleEffect> optinalEffect = effects.stream().filter(ParticleEffect::isComplete).findAny();
        if (!optinalEffect.isPresent()) {
            effects.add(newParticleEffectAtPosition(x, y));
            return;
        }
        optinalEffect.ifPresent(particleEffect -> {
            optinalEffect.get().reset(false);
            setColor(optinalEffect.get(), color);
            optinalEffect.get().setPosition(x, y);
//            particleEffect.setPosition(x, y);
//            particleEffect.start();
        });

//        ParticleEffect optinalEffect = null;
//        for (int i = 0; i < effects.size(); ++i) {
//            if (effects.get(i).isComplete()) {
//                optinalEffect = effects.get(i);
//                break;
//            }
//        }
//
//        if (optinalEffect != null) {
//            optinalEffect.reset(false);
//            optinalEffect.setPosition(x, y);
////            optinalEffect.start();
//        } else {
//            effects.add(newParticleEffectAtPosition(x, y));
//        }
    }

    private ParticleEffect newParticleEffectAtPosition(float x, float y) {
//        ParticleEffect pe = new ParticleEffect();
//        pe.load(Assets.instance.effectFile, Assets.instance.imagesDir);
//        pe.getEmitters().first().setPosition(x, y);
//        pe.scaleEffect(1.0f / 150f);
//        pe.start();

        ParticleEffect pe = particleEffectFactory();
        pe.getEmitters().first().setPosition(x, y);
        pe.scaleEffect(1.0f / 150f);
        pe.start();
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
