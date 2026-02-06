package com.Betulis.Game2D.engine.animation;

import com.Betulis.Game2D.engine.utils.SpriteSheetSlicer;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public final class AnimationClip {

    private final SpriteSheetSlicer sheet;
    private final TextureRegion[] clip;
    private final int frameBegin;
    private final int frameEnd;
    private final int rowBegin;
    private final int rowEnd;

    private final double frameDuration;
    private boolean finished;


    public AnimationClip(SpriteSheetSlicer sheet, double frameDuration, int frameBegin, int rowBegin, int frameEnd, int rowEnd) {
        this.sheet = sheet;
        this.frameDuration = frameDuration;
        this.frameBegin = frameBegin;
        this.frameEnd = frameEnd;
        this.rowBegin = rowBegin;
        this.rowEnd = rowEnd;

        int expected = (rowEnd - rowBegin + 1) * (frameEnd - frameBegin + 1);
        this.clip = new TextureRegion[expected];
        load();
    }

    public void load() {
        int index = 0;
        for (int row = rowBegin; row <= rowEnd; row++) {
            for (int frame = frameBegin; frame <= frameEnd; frame++) {
                if (index >= clip.length) {
                    return; 
                }
                clip[index] = sheet.getFrame(frame, row);
                index++;
            }
        }
    }

    public TextureRegion getFrame(int index) {
        return clip[index];
    }

    public int getLength() {
        return clip.length;
    }

    public double getFrameDuration() {
        return frameDuration;
    }
}

