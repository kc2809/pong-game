package com.mygdx.game.object;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.viewport.Viewport;

public class Trajectory {
    final float DENTAT = 0.1f;
    float[] vertices;
    int count;
    ShapeRenderer renderer;
    Viewport viewport;

    boolean isVisible = true;

    public Trajectory(Viewport viewport, int count) {
        this.count = count;
        vertices = new float[count];
        renderer = new ShapeRenderer();
        this.viewport = viewport;
    }

    public void projected(Vector2 start, Vector2 velocity) {
//        Vector2 velocity = end.cpy().sub(start).nor();
        vertices[0] = start.x;
        vertices[1] = start.y;
//        Vector2 normal = new Vector2(0, 0);
//        boolean isReflect = false;
        for (int i = 2; i < count; i += 2) {
            vertices[i] = vertices[i - 2] + velocity.x * DENTAT;
            vertices[i + 1] = vertices[i - 1] + velocity.y * DENTAT;

//            if (vertices[i] > viewport.getCamera().viewportWidth / 2) {
//                normal = new Vector2(-1, 0);
//                isReflect = true;
//            }
//            if (vertices[i] < -viewport.getCamera().viewportWidth / 2) {
//                normal = new Vector2(1, 0);
//                isReflect = true;
//            }
//            if (vertices[i + 1] > viewport.getCamera().viewportHeight / 2) {
//                normal = new Vector2(0, -1);
//                isReflect = true;
//            }
//            if (vertices[i + 1] < -viewport.getCamera().viewportHeight / 2) {
//                normal = new Vector2(0, 1);
//                isReflect = true;
//            }
//            if (isReflect) {
//                velocity = VectorUtil.reflectVector(velocity, normal);
//                isReflect = false;
//            }
//            vertices[i] = vertices[i - 2] + velocity.x * DENTAT;
//            vertices[i + 1] = vertices[i - 1] + velocity.y * DENTAT;
        }
    }

    public void draw() {
        if (!isVisible) return;
        renderer.setProjectionMatrix(viewport.getCamera().combined);
        renderer.setColor(Color.WHITE);
        renderer.begin(ShapeType.Line);

        for (int i = 0; i < vertices.length; i += 4) {
            renderer.line(vertices[i], vertices[i + 1], vertices[i + 2], vertices[i + 3]);
        }

        renderer.end();
    }

    public void setVisible(boolean visible) {
        isVisible = visible;
    }

}
