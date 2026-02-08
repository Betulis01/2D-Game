package com.Betulis.Game2D.engine.render;

import com.Betulis.Game2D.engine.camera.Camera;
import com.Betulis.Game2D.engine.math.AABB;
import com.Betulis.Game2D.engine.system.GameObject;
import com.Betulis.Game2D.engine.system.Scene;
import com.Betulis.Game2D.engine.system.Transform;
import com.Betulis.Game2D.game.components.AABB.Hitbox;
import com.Betulis.Game2D.game.components.AABB.Hurtbox;
import com.Betulis.Game2D.game.input.InputBindings;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;


/**
 * LibGDX equivalent of the JavaFX DebugRender using the same logic:
 * - FPS/UPS text always shown
 * - Debug text + AABB only when DEBUG is held
 * - Rendering is done inside a SpriteBatch (no ShapeRenderer)
 *
 * Requirements:
 * - A 1x1 white TextureRegion ("pixel") to draw rectangle outlines.
 * - A BitmapFont for text.
 * - Scene/Camera provide same data as JavaFX version (worldToScreenX/Y and zoom).
 */
public final class DebugRender {

    private final BitmapFont font;
    private final TextureRegion pixel; // 1x1 white region

    // Line thickness in screen pixels (works because we draw in screen space)
    private float lineThickness = 1f;

    public DebugRender(BitmapFont font, TextureRegion pixel) {
        this.font = font;
        this.pixel = pixel;
    }

    public void render(Scene scene, SpriteBatch batch) {
        renderFPSandUPS(scene, batch);
        renderDebugAABB(scene, batch);
        renderDebugText(scene, batch);
    }

    public void renderFPSandUPS(Scene scene, SpriteBatch batch) {
        font.setColor(Color.WHITE);
        font.draw(batch, "FPS: " + scene.getGame().getFps(), 10, 20);
    }

    public void renderDebugText(Scene scene, SpriteBatch batch) {
        if (!scene.getGame().getInput().isHeld(InputBindings.Action.DEBUG)) return;

        GameObject player = scene.getGameObjectByName("Player");
        if (player == null) return;

        Transform t = player.getTransform();

        double worldX = t.getWorldX();
        double worldY = t.getWorldY();

        int tileSize = scene.getMap().getTileSize();
        int tileX = (int) Math.floor(worldX / tileSize);
        int tileY = (int) Math.floor(worldY / tileSize);

        font.setColor(Color.WHITE);

        float x = 10f;
        float y = 100f;
        float line = 16f;

        font.draw(batch, String.format("World X: %.2f", worldX), x, y);
        y += line;
        font.draw(batch, String.format("World Y: %.2f", worldY), x, y);
        y += line;

        font.draw(batch, "Tile X: " + tileX, x, y);
        y += line;
        font.draw(batch, "Tile Y: " + tileY, x, y);
    }

    public void renderDebugAABB(Scene scene, SpriteBatch batch) {
        if (!scene.getGame().getInput().isHeld(InputBindings.Action.DEBUG)) return;

        Camera cam = scene.getCamera();

        for (GameObject go : scene.getObjects()) {

            // Hurtbox (green)
            Hurtbox hb = go.getComponent(Hurtbox.class);
            if (hb != null) {
                AABB wb = hb.getWorldBounds();
                if (wb != null) {
                    drawRectOutlineScreen(
                        batch,
                        Color.GREEN,
                        (float) cam.worldToScreenX(wb.x),
                        (float) cam.worldToScreenY(wb.y),
                        (float) (wb.width * cam.getZoom()),
                        (float) (wb.height * cam.getZoom())
                    );
                }
            }

            // Hitbox (red)
            Hitbox hi = go.getComponent(Hitbox.class);
            if (hi != null) {
                AABB wb = hi.getWorldBounds();
                if (wb != null) {
                    drawRectOutlineScreen(
                        batch,
                        Color.RED,
                        (float) cam.worldToScreenX(wb.x),
                        (float) cam.worldToScreenY(wb.y),
                        (float) (wb.width * cam.getZoom()),
                        (float) (wb.height * cam.getZoom())
                    );
                }
            }
        }

        // Reset tint so it doesn't leak into other draws
        batch.setColor(Color.WHITE);
    }

    /**
     * Draws a rectangle OUTLINE using SpriteBatch + 1x1 pixel in SCREEN SPACE.
     * x,y,width,height are already screen-space values (same as JavaFX version).
     */
    private void drawRectOutlineScreen(SpriteBatch batch, Color color,
                                       float x, float y, float width, float height) {

        batch.setColor(color);

        float t = lineThickness;

        // bottom
        batch.draw(pixel, x, y, width, t);
        // top
        batch.draw(pixel, x, y + height - t, width, t);
        // left
        batch.draw(pixel, x, y, t, height);
        // right
        batch.draw(pixel, x + width - t, y, t, height);
    }

    public void setLineThickness(float lineThickness) {
        this.lineThickness = Math.max(1f, lineThickness);
    }
}

