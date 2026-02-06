package com.Betulis.Game2D.game;

import com.Betulis.Game2D.engine.camera.Camera;
import com.Betulis.Game2D.engine.system.GameObject;
import com.Betulis.Game2D.engine.system.Scene;
import com.Betulis.Game2D.engine.system.Transform;
import com.Betulis.Game2D.game.prefabs.camera.CameraPrefab;
import com.Betulis.Game2D.game.prefabs.player.PlayerPrefab;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class DeathValley extends Scene {
    public DeathValley() {
        super(); 
    }

    @Override
    public void onLoad() {
        System.out.println("Loading Death Valley...");

        //Player 
        PlayerPrefab playerPrefab = new PlayerPrefab();
        GameObject playerObj = playerPrefab.create(100, 100);
        addObject(playerObj);

        // Camera
        CameraPrefab cameraPrefab = new CameraPrefab();
        GameObject cameraObj = cameraPrefab.create(playerObj.getComponent(Transform.class), getGame().getScreenWidth(), getGame().getScreenHeight());
        addObject(cameraObj);
        
        cameraObj.getComponent(Camera.class).setWorldBounds(100*32,100*32); //map.width * map.tileWidth,map.height * map.tileHeight);
        cameraObj.getComponent(Camera.class).setZoom(3);
        setCamera(cameraObj.getComponent(Camera.class));

    }

    @Override
    public void update(float dt) {
        super.update(dt);
    }

    @Override
    public void render(SpriteBatch batch) {
        super.render(batch);
    }
    
}
