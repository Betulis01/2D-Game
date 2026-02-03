package com.Betulis.Game2D.engine;

import java.util.ArrayList;
import java.util.List;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public abstract class Scene {
    protected List<GameObject> objects = new ArrayList<>();

    /* LOAD */
    public void load() {

    }

    /* UNLOAD */
    public void unload() {
        for (GameObject obj : objects) {
            obj.destroy();
        }
        objects.clear();
    }

    public void remove(GameObject obj) {
        obj.destroy();
        objects.remove(obj);
    }

    public void render(SpriteBatch batch) {
        for (GameObject obj : objects) {
            obj.render(batch);
        }
    }

    public void update(float dt) {
        for (GameObject obj : objects) {
            obj.update(dt);
        }
    }

    /* APIs */
    public GameObject getGameObjectByName(String name) {
        for (GameObject obj : objects) {
            if (obj.getName().equals(name)) {
                return obj;
            }
        }
        return null;
    }
}
