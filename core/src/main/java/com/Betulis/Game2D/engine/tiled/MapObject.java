package com.Betulis.Game2D.engine.tiled;

import java.util.Map;

public class MapObject {
    public final String name;
    public final String type;
    public final float x, y, width, height;
    public int renderYOffset; 
    public Integer gid;

    public final Map<String, String> properties;

    public MapObject(String name, String type,
                     float x, float y,
                     float width, float height, Integer gid,
                     Map<String, String> properties) {
        this.name = name;
        this.type = type;
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.gid = gid;
        this.properties = properties;
        this.renderYOffset = Integer.parseInt(properties.getOrDefault("renderYOffset", "0"));
    }

    public float getX() {
        return x;
    }

    public float getY() {
        return y;
    }

    public float getWidth() {
        return width;
    }

    public float getHeight() {
        return height;
    }
    public Integer getGid() {
        return gid;
    }

    public Map<String, String> getProperties() {
        return properties;
    }

    
}
