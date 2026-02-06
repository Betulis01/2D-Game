package com.Betulis.Game2D.engine.animation;

import com.Betulis.Game2D.engine.system.Component;

public final class AnimationUpdater extends Component {
    private AnimationDirector director;

    private float timer;
    private int frameIndex;

    @Override
    public void start() {
        director = getGameObject().getComponent(AnimationDirector.class);
    }

    @Override
    public void update(float dt) {
        if (director == null) return;

        AnimationClip current = director.getCurrentClip();
        if (current == null) return;

        timer += dt;

        if (timer < current.getFrameDuration()) return;
        timer -= current.getFrameDuration();

        frameIndex++;

        if (frameIndex >= current.getLength()) {
            if (director.isLooping()) {
                frameIndex = 0;
            } else {
                frameIndex = current.getLength() - 1;
                director.stop();
            }
        }
    }


    public void reset() {
        timer = 0;
        frameIndex = 0;
    }

    public int getFrameIndex() {
        return frameIndex;
    }
}
