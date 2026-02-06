package com.Betulis.Game2D.game.components.movement;

public class SlimeMovement extends Movement {

    private double timer;

    @Override
    public void updateMovement(float dt) {
        timer -= dt;

        if (timer <= 0) {
            float randX = (float)Math.random() * 2 - 1;
            float randY = (float)Math.random() * 2 - 1;
            
            direction.set(randX, randY);
        
            direction.normalize(); 
            
            moving = true;
            timer = 2.0;
        }
    }
}
