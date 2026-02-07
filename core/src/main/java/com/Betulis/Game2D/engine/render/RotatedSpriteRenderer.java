package com.Betulis.Game2D.engine.render;

import com.Betulis.Game2D.engine.animation.AnimationClip;
import com.Betulis.Game2D.engine.animation.AnimationDirector;
import com.Betulis.Game2D.engine.animation.AnimationUpdater;
import com.Betulis.Game2D.engine.camera.Camera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class RotatedSpriteRenderer extends SpriteRenderer {
    private AnimationDirector director;
    private AnimationUpdater updater;
    
    private final float width;
    private final float height;

    public RotatedSpriteRenderer(float width, float height) {
        super(width, height);
        this.width = width;
        this.height = height;
    }

    @Override
    public void start() {
        director = getGameObject().getComponent(AnimationDirector.class);
        updater = getGameObject().getComponent(AnimationUpdater.class);
    }

    @Override
    public void render(SpriteBatch batch) {
        if (director == null || updater == null) return;
        AnimationClip clip = director.getCurrentClip();
        if (clip == null) return;  
        TextureRegion img = clip.getFrame(updater.getFrameIndex());
        if (img == null) return;

        Camera cam = getScene().getCamera();
        float zoom = cam.getZoom();

        // Calculate screen coordinates and scaled size
        float sx = cam.worldToScreenX(transform.getWorldX());
        float sy = cam.worldToScreenY(transform.getWorldY());
        float sizeW = width * zoom;
        float sizeH = height * zoom;

        // LibGDX Draw Method for Rotation:
        // batch.draw(region, x, y, originX, originY, width, height, scaleX, scaleY, rotation)
        batch.draw(
            img, 
            sx - sizeW * 0.5f,    // x (bottom-left corner)
            sy - sizeH * 0.5f,    // y (bottom-left corner)
            sizeW * 0.5f,         // originX (pivot point relative to x)
            sizeH * 0.5f,         // originY (pivot point relative to y)
            sizeW,                // width
            sizeH,                // height
            1.0f,                 // scaleX
            1.0f,                 // scaleY
            transform.getWorldRotation() // rotation in degrees
        );
    }
}
