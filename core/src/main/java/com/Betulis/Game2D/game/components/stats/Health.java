package com.Betulis.Game2D.game.components.stats;

import com.Betulis.Game2D.engine.system.Component;

public class Health extends Component {
    private final double max;
    private double current;
    private boolean dead;

    private final double damageCooldown;
    private double damageTimer;


    public Health(double max, double damageCooldown) {
        this.max = max; 
        this.current = max;
        this.damageCooldown = damageCooldown;  
    }

    @Override
    public void update(float dt) {
        
        if (damageTimer > 0) {
            damageTimer -= dt;
        }

        if (current <= 0) {
            getGameObject().destroy();
        }
    }

    public void applyDamage(double dmg) {
        System.out.println(current);
        if (dead) return;
        if (damageTimer > 0) return;

        current -= dmg;
        damageTimer = damageCooldown;

        if (current <= 0) {
            current = 0;
            onDeath();
        }
    }

    public void heal(double heal) {
        if (dead) return;
        current = Math.min(max, current + heal);
    }

    public void regenerate(double dt, double regen) {
        if (dead) return;
    }

    private void onDeath() {
        dead = true;

        // Typical actions:
        // - disable hitboxes
        // - play death animation
        // - notify systems
        // - remove GameObject from scene
        getGameObject().destroy();
    }

    public boolean isAlive() { return current > 0; }
    
    public double getHealth() {
        return current;
    }

    public double getMaxHealth() {
        return max;
    }
    
    public boolean isDead() {
        return dead;
    }
}
