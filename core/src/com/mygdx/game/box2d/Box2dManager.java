package com.mygdx.game.box2d;

import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.World;

import java.util.ArrayList;
import java.util.List;

public class Box2dManager {
    private static Box2dManager instance = null;

    private List<Body> bodiesWillBeDestroyed;

    List<Body> tempAcive;
    List<Body> tempInActive;

    private List<Body> bodiesWillBeActive;
    private List<Body> bodiesWillBeInActive;


    private Box2dManager() {
        bodiesWillBeDestroyed = new ArrayList<>();

        tempAcive = new ArrayList<>();
        tempInActive = new ArrayList<>();

        bodiesWillBeInActive = new ArrayList<>();
        bodiesWillBeActive = new ArrayList<>();
    }

    public void addActiveBodyToQueue(Body body) {
        tempAcive.add(body);
    }

    public void addInActiveBodyToQueue(Body body) {
        tempInActive.add(body);
    }

    public void activeBodies(World world) {
        bodiesWillBeActive.addAll(tempAcive);
        if (!bodiesWillBeActive.isEmpty()) {
            for (Body body : bodiesWillBeActive) {
                body.setActive(true);
            }
        }
        bodiesWillBeActive.clear();
        tempAcive.clear();
    }

    public void inActiveBodies(World world) {
        bodiesWillBeInActive.addAll(tempInActive);
        if (!bodiesWillBeInActive.isEmpty()) {
            for (Body body : bodiesWillBeInActive) {
                body.setActive(false);
            }
        }
        bodiesWillBeInActive.clear();
        tempInActive.clear();
    }

    public static Box2dManager getInstance() {
        if (instance == null) {
            instance = new Box2dManager();
        }
        return instance;
    }

    public List<Body> getBodiesWillBeDestroyed() {
        return bodiesWillBeDestroyed;
    }

    public void addBodyToDestroy(Body body) {
        bodiesWillBeDestroyed.add(body);
    }

    public void destroyBody(World world) {
        if (!bodiesWillBeDestroyed.isEmpty())
//            bodiesWillBeDestroyed.forEach(world::destroyBody);
            for (Body body : bodiesWillBeDestroyed) {
                world.destroyBody(body);
            }
        bodiesWillBeDestroyed.clear();
    }
}
