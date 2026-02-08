package com.Betulis.Game2D.engine.render;

import com.Betulis.Game2D.engine.camera.Camera;
import com.Betulis.Game2D.engine.math.AABB;
import com.Betulis.Game2D.engine.system.Component;
import com.Betulis.Game2D.engine.tiled.TileData;
import com.Betulis.Game2D.engine.tiled.TileLayer;
import com.Betulis.Game2D.engine.tiled.TiledMap;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;


/**
 * LibGDX version of the JavaFX TileMapRenderer.
 *
 * Keeps the same logic:
 * - iterate visible layers
 * - cull tiles against camera view bounds
 * - draw tiles with camera world->screen conversion and zoom scaling
 *
 * Requires:
 * - TileData holds TextureRegion instead of javafx Image
 * - Render is called with a SpriteBatch (begun/ended by your render pipeline)
 */
public class TileMapRenderer extends Component {

    private final TiledMap map;

    public TileMapRenderer(TiledMap map) {
        this.map = map;
    }

    /**
     * Call from your render loop with an active SpriteBatch.
     * (Typically: batch.setProjectionMatrix(...); batch.begin(); renderer.render(batch); batch.end();)
     */
    @Override
    public void render(SpriteBatch batch) {
        Camera camera = getScene().getCamera();
        if (camera == null) return;

        AABB view = camera.getViewBounds();

        for (TileLayer layer : map.getTileLayers()) {
            if (!layer.visible) continue;

            int tileW = map.tileWidth;
            int tileH = map.tileHeight;

            int startX = Math.max(0, (int) (view.getMinX() / tileW));
            int startY = Math.max(0, (int) (view.getMinY() / tileH));
            int endX   = Math.min(layer.width,  (int) Math.ceil(view.getMaxX() / tileW));
            int endY   = Math.min(layer.height, (int) Math.ceil(view.getMaxY() / tileH));

            for (int y = startY; y < endY; y++) {
                for (int x = startX; x < endX; x++) {

                    int gid = layer.gids[y * layer.width + x];
                    if (gid == 0) continue;

                    TileData tile = map.getTileData(gid);
                    if (tile == null) continue;

                    float worldX = x * tileW;
                    float worldY = y * tileH;

                    // Keep your existing camera conversions (screen-space pipeline)
                    float drawX = (float) camera.worldToScreenX(worldX);
                    float drawY = (float) camera.worldToScreenY(worldY);

                    float drawW = (float) (map.tileWidth  * camera.getZoom());
                    float drawH = (float) (map.tileHeight * camera.getZoom());

                    TextureRegion image = tile.image;
                    if (image == null) continue;

                    batch.draw(image, drawX, drawY, drawW, drawH);
                }
            }
        }
    }

    public TiledMap getMap() {
        return map;
    }
}
