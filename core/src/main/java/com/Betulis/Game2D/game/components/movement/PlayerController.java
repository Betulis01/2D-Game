package com.Betulis.Game2D.game.components.movement;

import com.Betulis.Game2D.game.input.InputBindings;
import com.Betulis.Game2D.game.input.InputBindings.Action;


public class PlayerController extends Movement {
    
    protected InputBindings input;

    @Override
    public void start() {
        super.start();
        input = getScene().getGame().getInput();
    }

    @Override
    public void updateMovement(float dt) {
        direction.set(0, 0);

        if (input.isHeld(Action.MOVE_UP)) direction.y += 1;
        if (input.isHeld(Action.MOVE_DOWN)) direction.y -= 1;
        if (input.isHeld(Action.MOVE_LEFT)) direction.x -= 1;
        if (input.isHeld(Action.MOVE_RIGHT)) direction.x += 1;

        moving = (direction.x != 0 || direction.y != 0);
        if (!moving) return;
        
        direction.normalize(); // Normalize is crucial here so the player doesn't move faster diagonally than cardinally 
        
    }
}
