package com.Betulis.Game2D.engine.animation;


import java.util.HashMap;
import java.util.Map;

import com.Betulis.Game2D.engine.system.Component;


public final class AnimationDirector extends Component {

    private AnimationUpdater updater;

    private final Map<String, AnimationClip> animations = new HashMap<>();
    private AnimationClip currentClip;

    private boolean loop = true;
    private boolean finished;

    @Override
    public void start() {
        updater = getGameObject().getComponent(AnimationUpdater.class);
    }

    public void add(String name, AnimationClip clip) {
        animations.put(name, clip);

        if (currentClip == null) {
            currentClip = clip;
            finished = false;
        }
    }

    public void play(String name) {
        play(name, true);
    }

    public void play(String name, boolean loop) {
        AnimationClip next = animations.get(name);
        if (next == null) return;

        if (next == currentClip && !finished) {
            this.loop = loop; // Update loop status just in case
            return;
        }

        this.loop = loop;
        this.currentClip = next;
        this.finished = false;

        if (updater != null) {
            updater.reset();
        }
    }


    public void stop() {
        finished = true;
        loop = false;
    }

    public AnimationClip getCurrentClip() {
        return currentClip;
    }

    public boolean isLooping() {
        return loop;
    }

    public boolean isFinished() {
        return finished;
    }
}


