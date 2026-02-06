package com.Betulis.Game2D.engine.animation;


import java.util.HashMap;
import java.util.Map;

import com.Betulis.Game2D.engine.system.Component;


public final class AnimationDirector extends Component {

    private AnimationUpdater updater;

    private final Map<String, AnimationClip> animations = new HashMap<>();
    private AnimationClip current;

    private boolean loop = true;
    private boolean finished;

    @Override
    public void start() {
        updater = getGameObject().getComponent(AnimationUpdater.class);
    }

    public void add(String name, AnimationClip clip) {
        animations.put(name, clip);

        if (current == null) {
            current = clip;
            finished = false;
        }
    }

    public void play(String name) {
        play(name, true);
    }

    public void play(String name, boolean loop) {
        AnimationClip next = animations.get(name);
        if (next == null) return;

        if (next == current && finished) return;

        this.loop = loop;
        this.current = next;
        this.finished = false;

        if (updater != null) {
            updater.reset();
        }
    }


    public void stop() {
        finished = true;
        loop = false;
    }

    public AnimationClip getCurrent() {
        return current;
    }

    public boolean isLooping() {
        return loop;
    }

    public boolean isFinished() {
        return finished;
    }
}


