package com.Betulis.Game2D.engine.animation;

import com.Betulis.Game2D.engine.system.Component;

public class AnimationAutoDespawner extends Component {

    private AnimationDirector director;

    @Override
    public void start() {
        director = getGameObject().getComponent(AnimationDirector.class);
    }

    @Override
    public void update(float dt) {
        if (director == null) return;

        if (director.isFinished()) {
            getGameObject().destroy();
        }
    }
}
