package com.Betulis.Game2D.engine.tiled;

import java.util.Map;

import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class TileData {
    public final int gid;
    public final TextureRegion image;
    public final Map<String, String> properties;

    public TileData(int gid, TextureRegion image, Map<String, String> properties) {
        this.gid = gid;
        this.image = image;
        this.properties = properties;
    }
}
