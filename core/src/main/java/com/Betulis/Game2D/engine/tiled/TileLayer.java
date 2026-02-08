package com.Betulis.Game2D.engine.tiled;

public class TileLayer {
    public final String name;
    public final int width;
    public final int height;
    public final int[] gids;
    public final float opacity;
    public final boolean visible;

    public TileLayer(String name, int width, int height,
                     int[] gids, float opacity, boolean visible) {
        this.name = name;
        this.width = width;
        this.height = height;
        this.gids = gids;
        this.opacity = opacity;
        this.visible = visible;
    }
}
