package com.Betulis.Game2D.engine.system;

import java.util.ArrayList;
import java.util.List;

public class Transform extends Component {

    private float x = 0;
    private float y = 0;
    private float z = 0;
    private float zVelocity = 0;
    private float rotation = 0; // degrees
    private float scaleX = 1;
    private float scaleY = 1;

    private Transform parent = null;
    private final List<Transform> children = new ArrayList<>();

    /* POSITION */
    public float getX() { return x; }
    public float getY() { return y; }
    public float getZ() { return z; }
    public float getZVelocity() { return zVelocity; }

    public void setX(float x) { this.x = x; }
    public void setY(float y) { this.y = y; }
    public void setZ(float z) { this.z = z; }
    public void setZVelocity(float zVelocity) { this.zVelocity = zVelocity; }

    public void setPosition(float x, float y) {
        this.x = x;
        this.y = y;
    }

    public void translate(float dx, float dy) {
        this.x += dx;
        this.y += dy;
    }

    /* ROTATION */
    public float getRotation() { return rotation; }

    public void setRotation(float rotation) {
        this.rotation = rotation;
    }

    public void rotate(float dAngle) {
        this.rotation += dAngle;
    }

    /* SCALE */
    public float getScaleX() { return scaleX; }
    public float getScaleY() { return scaleY; }

    public void setScale(float scaleX, float scaleY) {
        this.scaleX = scaleX;
        this.scaleY = scaleY;
    }

    /* HIERARCHY SYSTEM */
    public Transform getParent() {
        return parent;
    }

    public void setParent(Transform newParent) {
        if (this.parent != null) {
            this.parent.children.remove(this); // remove from old parent
        }
        
        this.parent = newParent; // assign new parent

        if (newParent != null) {
            newParent.children.add(this); // attach to new parent's children list
        }
    }

    public List<Transform> getChildren() {
        return children;
    }

    /* WORLD */
    public float getWorldX() {
        if (parent == null) return x;
        return parent.getWorldX() + x;
    }

    public float getWorldY() {
        if (parent == null) return y;
        return parent.getWorldY() + y;
    }

    public void setWorldPosition(float worldX, float worldY) {
        if (parent == null) {
            this.x = worldX;
            this.y = worldY;
        } else {
            this.x = worldX - parent.getWorldX();
            this.y = worldY - parent.getWorldY();
        }
    }


    public float getWorldZ() {
        if (parent == null) return z;
        return parent.getWorldZ() + z;
    }

    public float getWorldRotation() {
        if (parent == null) return rotation;
        return parent.getWorldRotation() + rotation;
    }

    public float getWorldScaleX() {
        if (parent == null) return scaleX;
        return parent.getWorldScaleX() * scaleX;
    }

    public float getWorldScaleY() {
        if (parent == null) return scaleY;
        return parent.getWorldScaleY() * scaleY;
    }

    

    /* ACTIVE */
    @Override
    public void onDestroy() {
        
        setParent(null);
        for (Transform child : children) {
            child.parent = null; // Detach hierarchy cleanly
        }
        children.clear();
    }
}

