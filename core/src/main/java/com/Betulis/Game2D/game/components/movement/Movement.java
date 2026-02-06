package com.Betulis.Game2D.game.components.movement;

import com.Betulis.Game2D.engine.math.Vector2;
import com.Betulis.Game2D.engine.system.Component;

public abstract class Movement extends Component {

    protected Vector2 direction = new Vector2();
    protected boolean moving;

    /** Compute desired movement direction */
    public abstract void updateMovement(double dt);

    public Vector2 getDirection() {
        return direction;
    }

    public boolean isMoving() {
        return moving;
    }
}
