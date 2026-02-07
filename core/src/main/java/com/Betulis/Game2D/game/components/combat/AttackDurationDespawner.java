package com.Betulis.Game2D.game.components.combat;

import com.Betulis.Game2D.engine.animation.AnimationDirector;
import com.Betulis.Game2D.engine.system.Component;

public class AttackDurationDespawner extends Component {
    private double duration;
    private double despawnTimer;

    public AttackDurationDespawner(double duration) {
        this.duration = duration;
    }
    
    @Override
    public void update(float dt) {
        duration -= dt;
        if (duration <= 0) {
            despawnAnimation(dt);
        }
    }

    public void despawnAnimation(double dt) {
        despawnTimer += dt;
        if (despawnTimer > 0.5) {
            getGameObject().destroy();
            despawnTimer = 0;
        } else {
            AnimationDirector director = getGameObject().getComponent(AnimationDirector.class);
            director.play("noHit",false);
        }
    }
}
