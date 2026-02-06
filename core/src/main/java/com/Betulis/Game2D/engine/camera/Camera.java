package com.Betulis.Game2D.engine.camera;

import com.Betulis.Game2D.engine.math.AABB;
import com.Betulis.Game2D.engine.system.Component;
import com.Betulis.Game2D.engine.system.Transform;

public class Camera extends Component {

    private Transform target;

    private final float screenWidth;
    private final float screenHeight;
    private float zoom = 1.0f;

    // Optional: world bounds (map size)
    private float worldWidth;
    private float worldHeight;
    private boolean clampToWorld = false;

    public Camera(float screenWidth, float screenHeight) {
        this.screenWidth = screenWidth;
        this.screenHeight = screenHeight;
    }

    /* FOLLOW */

    public void follow(Transform target) {
        this.target = target;
    }

    /* WORLD CLAMP */

    public void setWorldBounds(float width, float height) {
        this.worldWidth = width;
        this.worldHeight = height;
        this.clampToWorld = true;
    }

    /* ZOOM */
    public void zoomBy(float delta) {
        setZoom(zoom + delta / 100);
    }

    public void zoomTowards(float targetZoom) {
        setZoom(zoom + (targetZoom - zoom));
    }


    public void setZoom(float zoom) {
        this.zoom = Math.max(0.25f, Math.min(zoom, 4.0f));
    }

    public float getZoom() {
        return zoom;
    }

    /* UPDATE */

    @Override
    public void update(float dt) {
        if (target == null) return;

        float viewW = screenWidth / zoom;
        float viewH = screenHeight / zoom;

        // Camera wants to center on target
        float camX = target.getWorldX();
        float camY = target.getWorldY();

        if (clampToWorld) {
            float halfW = viewW * 0.5f;
            float halfH = viewH * 0.5f;

            camX = clamp(camX, halfW, worldWidth  - halfW);
            camY = clamp(camY, halfH, worldHeight - halfH);
        }

        transform.setPosition(camX, camY);
    }


    /* VIEW RECT */

    private final AABB viewBounds = new AABB(0,0,0,0);

    public AABB getViewBounds() {
        float viewW = screenWidth / zoom;
        float viewH = screenHeight / zoom;

        viewBounds.x = transform.getWorldX() - viewW * 0.5f;
        viewBounds.y = transform.getWorldY() - viewH * 0.5f;
        viewBounds.width  = viewW;
        viewBounds.height = viewH;

        return viewBounds;
    }


    /* COORD CONVERSION */

    public float worldToScreenX(float worldX) {
        return (worldX - (transform.getWorldX() - screenWidth / (2 * zoom))) * zoom;
    }

    public float worldToScreenY(float worldY) {
        return (worldY - (transform.getWorldY() - screenHeight / (2 * zoom))) * zoom;
    }

    public float screenToWorldX(float screenX) {
        return screenX / zoom + (transform.getWorldX() - screenWidth / (2 * zoom));
    }

    public float screenToWorldY(float screenY) {
        return screenY / zoom + (transform.getWorldY() - screenHeight / (2 * zoom));
    }

    /* UTIL */

    private static float clamp(float v, float min, float max) {
        return Math.max(min, Math.min(v, max));
    }
}
