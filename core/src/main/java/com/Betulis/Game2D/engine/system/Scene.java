package com.Betulis.Game2D.engine.system;

import java.util.ArrayList;
import java.util.List;

import com.Betulis.Game2D.engine.camera.Camera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public abstract class Scene {
    protected List<GameObject> objects = new ArrayList<>();
    protected Game game;
    protected Camera camera;


    /* LOAD */
    public void load(Game game) {
        this.game = game;
        onLoad();
    }
    protected abstract void onLoad();

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
    /* RENDER AND UPDATE */
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

    /* ADD AND REMOVE */

    public void addObject(GameObject obj) {
        if (objects.contains(obj)) return;

        obj.setScene(this);
        objects.add(obj);
    }

    public void removeObject(GameObject obj) {
        if (!objects.contains(obj)) return;
        objects.remove(obj);
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

    public void setCamera(Camera camera) {
        this.camera = camera;
    }

    public Game getGame() {
        return game;
    }

    public Camera getCamera() {
        return camera;
    }
}
