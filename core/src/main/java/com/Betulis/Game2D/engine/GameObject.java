package com.Betulis.Game2D.engine;

import java.util.ArrayList;
import java.util.List;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class GameObject {
    private Scene scene;
    private Transform transform;
    private String name = "GameObject";
    private final List<Component> components = new ArrayList<>();
    private boolean started = false;
    private boolean active = true;

    public GameObject(String name) {
        this.name = name;
        transform = new Transform();
        addComponent(transform);
    }

    public void start() {
        if (!started) {
            started = true;
            for (Component c : components) {
                c.start();
            }
        }
    }

    public void destroy() {
        if (scene != null) {
            scene.remove(this);
        }
        active = false;
        onDestroy();
    }

    public void onDestroy() {
        for (Component c : components) {
            c.onDestroy();
        }
    }

    /* RENDER AND UPDATE */
    public void update(double dt) {
        if (!active) return;
        if (!started) start(); // Start is delayed until the first update

        for (Component c : components) {
            if (c.isEnabled()) {
                c.update(dt);
            }
        }
    }

    public void render(SpriteBatch batch) {
        if (!active) return;

        for (Component c : components) {
            if (c.isEnabled()) {
                c.render(batch);
            }
        }
    }

    /* COMPONENT MANAGEMENT */
    public <T extends Component> T addComponent(T component) {
        component.setGameObject(this);
        components.add(component);
        return component;
    }

    public <T extends Component> T getComponent(Class<T> type) {
        for (Component c : components) {
            if (type.isInstance(c)) {
                return type.cast(c);
            }
        }
        return null;
    }

    public <T extends Component> List<T> getComponents(Class<T> type) {
        List<T> result = new ArrayList<>();
        for (Component c : components) {
            if (type.isInstance(c)) {
                result.add(type.cast(c));
            }
        }
        return result;
    }


    /* APIs */
    public void setScene(Scene scene) { // INTERNAL — Scene sets this when adding the object
        this.scene = scene;
        for (Component c : components) {
            c.setGameObject(this);
        }
    }

    public Scene getScene() {
        return scene;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Transform getTransform() {
        return transform;
    }
}
