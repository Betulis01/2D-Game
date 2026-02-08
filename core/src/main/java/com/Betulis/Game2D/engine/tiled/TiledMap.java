package com.Betulis.Game2D.engine.tiled;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TiledMap {
    public final int width;
    public final int height;
    public final int tileWidth;
    public final int tileHeight;

    private final List<TileLayer> tileLayers = new ArrayList<>();
    private final List<ObjectLayer> objectLayers = new ArrayList<>();

    // global GID → TileData
    private final Map<Integer, TileData> tiles = new HashMap<>();

    public TiledMap(int width, int height, int tileWidth, int tileHeight) {
        this.width = width;
        this.height = height;
        this.tileWidth = tileWidth;
        this.tileHeight = tileHeight;
    }

    public void addTileLayer(TileLayer layer) {
        tileLayers.add(layer);
    }

    public void addObjectLayer(ObjectLayer layer) {
        objectLayers.add(layer);
    }

    public List<TileLayer> getTileLayers() {
        return tileLayers;
    }

    public List<ObjectLayer> getObjectLayers() {
        return objectLayers;
    }

    public ObjectLayer getObjectLayerByName(String name) {
        for (ObjectLayer layer : objectLayers) {
            if (layer.getName().equals(name)) {
                return layer;
            }
        }
        return null;
    }


    public void addTileData(TileData data) {
        tiles.put(data.gid, data);
    }

    public TileData getTileData(int gid) {
        return tiles.get(gid);
    }

    public int getWidth() {
        return width * tileWidth;
    }

    public int getHeight() {
        return height * tileHeight;
    }

    public int getTileSize() {
        return tileWidth;
    }
}
