package com.Betulis.Game2D.core;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public abstract class Component {
    protected GameObject gameObject;
    protected Transform transform; // convenience pointer
    private boolean enabled = true;

    
    public void setGameObject(GameObject obj) { // Called by GameObject when this component is attached
        this.gameObject = obj;
        this.transform = obj.getComponent(Transform.class);
    }
    
    /* ACTIVE (override these in children) */

    public void awake() {} //Called once immediately when the GameObject is created

    public void start() {} //Called once just before the first update

    public void update(double dt) {} //Called every frame while enabled

    public void render(SpriteBatch batch) {} //Rendering callback (only for render components) 

    public void onDestroy() {} //Called when destroyed or removed from the scene


    /* APIs */
    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean value) {
        this.enabled = value;
    }

    public GameObject getGameObject() {
        return gameObject;
    }

    public Scene getScene() {
        return gameObject != null ? gameObject.getScene() : null;
    }  
}
