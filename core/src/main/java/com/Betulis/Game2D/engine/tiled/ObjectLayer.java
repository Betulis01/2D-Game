package com.Betulis.Game2D.engine.tiled;

import java.util.List;

public class ObjectLayer {
    public final String name;


    public final List<MapObject> objects;

    public ObjectLayer(String name, List<MapObject> objects) {
        this.name = name;
        this.objects = objects;
    }

    public List<MapObject> getObjects() {
        return objects;
    }
    
    public String getName() {
        return name;
    }
}
