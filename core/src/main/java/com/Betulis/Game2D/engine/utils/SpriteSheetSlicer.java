package com.Betulis.Game2D.engine.utils;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public final class SpriteSheetSlicer {

    private final Texture spriteSheet;
    private final TextureRegion[][] frames;

    private final int frameCount;   // columns
    private final int rows;         // rows
    private final int frameWidth;
    private final int frameHeight;

    public SpriteSheetSlicer(String assetPath, int frameWidth, int frameHeight, int frameCount, int rows) {
        this.spriteSheet = new Texture(Gdx.files.internal(assetPath));
        this.frameWidth = frameWidth;
        this.frameHeight = frameHeight;
        this.frameCount = frameCount;
        this.rows = rows;

        this.frames = new TextureRegion[frameCount][rows];
        load();
    }

    private void load() {
        for (int row = 0; row < rows; row++) {
            for (int frame = 0; frame < frameCount; frame++) {
                frames[frame][row] = new TextureRegion(
                        spriteSheet,
                        frame * frameWidth,
                        row * frameHeight,
                        frameWidth,
                        frameHeight
                );
            }
        }
    }

    public TextureRegion[][] getFrames() {
        return frames;
    }

    public TextureRegion getFrame(int frame, int row) {
        return frames[frame][row];
    }

    public int getFrameCount() {
        return frameCount;
    }

    public int getRows() {
        return rows;
    }

    public int getFrameWidth() {
        return frameWidth;
    }

    public int getFrameHeight() {
        return frameHeight;
    }

    /** Dispose the underlying Texture when you no longer need this sheet. */
    public void dispose() {
        spriteSheet.dispose();
    }
}
