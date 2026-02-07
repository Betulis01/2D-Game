package com.Betulis.Game2D.game.components.movement;

import com.Betulis.Game2D.engine.math.Vector2;

public final class Projectile extends Movement {

    private final Vector2 initialDir;

    public Projectile(Vector2 dir) {
        this.initialDir = dir.copy();
    }

    @Override
    public void start() {
        direction.set(initialDir).normalize();
        moving = true;
    }

    @Override
    public void updateMovement(float dt) {
        moving = true;
    }
}

