package com.Betulis.Game2D.engine.math;

public final class AABB {

    public float x;
    public float y;
    public float width;
    public float height;

    public AABB(float x, float y, float width, float height) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
    }

    public boolean intersects(AABB other) {
        return x < other.x + other.width &&
               x + width > other.x &&
               y < other.y + other.height &&
               y + height > other.y;
    }

    public AABB translate() {
        return new AABB(
            this.x,
            this.y,
            this.width,
            this.height
        );
    }


    public float getMinX() {
        return x;
    }

    public float getMinY() {
        return y;
    }

    public float getMaxX() {
        return x + width;
    }

    public float getMaxY() {
        return y + height;
    }
}
