package com.Betulis.Game2D.engine.utils;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Texture;

public class Assets {
    private final AssetManager manager = new AssetManager();

    // Define constants for paths to avoid typos
    public static final String orc_sheet = "player/orc8.png";
    public static final String slime_sheet = "mob/slime.png";

    public void load() {
        manager.load(orc_sheet, Texture.class);
        manager.load(slime_sheet, Texture.class);
        // Add more assets here
        
        manager.finishLoading(); // Blocks until everything is loaded
    }

    public Texture getTexture(String path) {
        return manager.get(path, Texture.class);
    }

    public void dispose() {
        manager.dispose();
    }
}
