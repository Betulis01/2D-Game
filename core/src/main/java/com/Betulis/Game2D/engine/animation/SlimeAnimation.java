package com.Betulis.Game2D.engine.animation;

import com.Betulis.Game2D.engine.utils.SpriteSheetSlicer;
import com.Betulis.Game2D.game.components.movement.Movement;
import com.Betulis.Game2D.engine.math.Vector2;
import com.Betulis.Game2D.engine.system.Component;

public class SlimeAnimation extends Component {

    private Movement movement;
    private AnimationDirector director;

    @Override
    public void start() {
        movement = getGameObject().getComponent(Movement.class);
        director = getGameObject().getComponent(AnimationDirector.class);
    }

    @Override
    public void update(float dt) {
        if (movement == null || director == null) return;

        if (movement.isMoving()) {
            director.play("jump");
        } else {
            director.play("idle");
        }
    }
}
