package com.Betulis.Game2D.engine.render;

import com.Betulis.Game2D.engine.camera.Camera;
import com.Betulis.Game2D.engine.system.Component;
import com.Betulis.Game2D.engine.tiled.MapObject;
import com.Betulis.Game2D.engine.tiled.ObjectLayer;
import com.Betulis.Game2D.engine.tiled.TileData;
import com.Betulis.Game2D.engine.tiled.TiledMap;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;



/**
 * LibGDX version of the JavaFX ObjectLayerRenderer.
 *
 * Keeps the same logic:
 * - Find object layer named "Objects"
 * - Render only tile objects (gid != null)
 * - Apply Tiled rule: object x/y is bottom-left of the tile graphic
 * - Use camera world->screen conversion and zoom scaling
 *
 * Requires TileData.region (TextureRegion) instead of JavaFX Image.
 */
public class ObjectLayerRenderer extends Component {

    private final TiledMap map;

    public ObjectLayerRenderer(TiledMap map) {
        this.map = map;
    }

    public void render(SpriteBatch batch) {
        Camera cam = getScene().getCamera();
        if (cam == null) return;

        float zoom = (float) cam.getZoom();

        ObjectLayer layer = map.getObjectLayerByName("Objects");
        if (layer == null) return;

        for (MapObject obj : layer.getObjects()) {

            // Only render tile objects
            Integer gid = obj.getGid();
            if (gid == null) continue;

            TileData tile = map.getTileData(gid);
            if (tile == null || tile.image == null) continue;

            TextureRegion image = tile.image;

            // Region dimensions in pixels
            float w = image.getRegionWidth();
            float h = image.getRegionHeight();

            // --- Tiled rule ---
            // Object x/y = bottom-left of tile image
            float worldX = obj.getX();
            float worldY = obj.getY() - h;

            float screenX = (float) cam.worldToScreenX(worldX);
            float screenY = (float) cam.worldToScreenY(worldY);

            batch.draw(
                image,
                screenX,
                screenY,
                w * zoom,
                h * zoom
            );
        }
    }

    public TiledMap getMap() {
        return map;
    }
}
