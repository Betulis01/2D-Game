package com.Betulis.Game2D.engine.config;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.utils.Json;

public final class ConfigLoader {
    private static final Json JSON = new Json();

    public EntityConfig load(String path) {
        String normalized = path.startsWith("/") ? path.substring(1) : path;

        if (!Gdx.files.internal(normalized).exists()) {
            throw new IllegalArgumentException("No json at: " + normalized);
        }

        return JSON.fromJson(EntityConfig.class, Gdx.files.internal(normalized));
    }
}

