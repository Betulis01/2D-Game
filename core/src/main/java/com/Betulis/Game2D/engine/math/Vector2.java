package com.Betulis.Game2D.engine.math;

public final class Vector2 {

    public float x;
    public float y;

    public Vector2() {
        this(0, 0);
    }

    public Vector2(float x, float y) {
        this.x = x;
        this.y = y;
    }

    public Vector2 set(float x, float y) {
        this.x = x;
        this.y = y;
        return this;
    }

    public Vector2 set(Vector2 other) {
        this.x = other.x;
        this.y = other.y;
        return this;
    }

    public Vector2 add(Vector2 other) {
        x += other.x;
        y += other.y;
        return this;
    }

    public Vector2 scale(float s) {
        x *= s;
        y *= s;
        return this;
    }

    public float length() {
        return (float) Math.sqrt(x * x + y * y);
    }

    public Vector2 normalize() {
        float len = length();
        if (len != 0) {
            x /= len;
            y /= len;
        }
        return this;
    }

    public Vector2 copy() {
        return new Vector2(x, y);
    }

    
}
