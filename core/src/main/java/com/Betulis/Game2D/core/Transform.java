package com.Betulis.Game2D.core;

import java.util.ArrayList;
import java.util.List;

public class Transform extends Component {

    private double x = 0;
    private double y = 0;
    private double z = 0;
    private double zVelocity = 0;
    private double rotation = 0; // degrees
    private double scaleX = 1;
    private double scaleY = 1;

    private Transform parent = null;
    private final List<Transform> children = new ArrayList<>();

    /* POSITION */
    public double getX() { return x; }
    public double getY() { return y; }
    public double getZ() { return z; }
    public double getZVelocity() { return zVelocity; }

    public void setX(double x) { this.x = x; }
    public void setY(double y) { this.y = y; }
    public void setZ(double z) { this.z = z; }
    public void setZVelocity(double zVelocity) { this.zVelocity = zVelocity; }

    public void setPosition(double x, double y) {
        this.x = x;
        this.y = y;
    }

    public void translate(double dx, double dy) {
        this.x += dx;
        this.y += dy;
    }

    /* ROTATION */
    public double getRotation() { return rotation; }

    public void setRotation(double rotation) {
        this.rotation = rotation;
    }

    public void rotate(double dAngle) {
        this.rotation += dAngle;
    }

    /* SCALE */
    public double getScaleX() { return scaleX; }
    public double getScaleY() { return scaleY; }

    public void setScale(double scaleX, double scaleY) {
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
    public double getWorldX() {
        if (parent == null) return x;
        return parent.getWorldX() + x;
    }

    public double getWorldY() {
        if (parent == null) return y;
        return parent.getWorldY() + y;
    }

    public void setWorldPosition(double worldX, double worldY) {
        if (parent == null) {
            this.x = worldX;
            this.y = worldY;
        } else {
            this.x = worldX - parent.getWorldX();
            this.y = worldY - parent.getWorldY();
        }
    }


    public double getWorldZ() {
        if (parent == null) return z;
        return parent.getWorldZ() + z;
    }

    public double getWorldRotation() {
        if (parent == null) return rotation;
        return parent.getWorldRotation() + rotation;
    }

    public double getWorldScaleX() {
        if (parent == null) return scaleX;
        return parent.getWorldScaleX() * scaleX;
    }

    public double getWorldScaleY() {
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

