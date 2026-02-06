package com.Betulis.Game2D.game.scenes;

import com.Betulis.Game2D.engine.camera.Camera;
import com.Betulis.Game2D.engine.system.GameObject;
import com.Betulis.Game2D.engine.system.Scene;
import com.Betulis.Game2D.engine.system.Transform;
import com.Betulis.Game2D.game.prefabs.camera.CameraPrefab;
import com.Betulis.Game2D.game.prefabs.mobs.SlimePrefab;
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
        GameObject playerObj = playerPrefab.create(100, 100,getGame().getAssets().getTexture("player/orc8.png"));
        addObject(playerObj);
        
        //Slime
        SlimePrefab slimePrefab = new SlimePrefab();
        GameObject slimeObj0 = slimePrefab.create(200,200,getGame().getAssets().getTexture("mob/slime.png"));
        addObject(slimeObj0);
        GameObject slimeObj1 = slimePrefab.create(200,200,getGame().getAssets().getTexture("mob/slime.png"));
        addObject(slimeObj1);
        GameObject slimeObj2 = slimePrefab.create(200,200,getGame().getAssets().getTexture("mob/slime.png"));
        addObject(slimeObj2);
        GameObject slimeObj3 = slimePrefab.create(200,200,getGame().getAssets().getTexture("mob/slime.png"));
        addObject(slimeObj3);
        GameObject slimeObj4 = slimePrefab.create(200,200,getGame().getAssets().getTexture("mob/slime.png"));
        addObject(slimeObj4);
        GameObject slimeObj5 = slimePrefab.create(200,200,getGame().getAssets().getTexture("mob/slime.png"));
        addObject(slimeObj5);
        GameObject slimeObj6 = slimePrefab.create(200,200,getGame().getAssets().getTexture("mob/slime.png"));
        addObject(slimeObj6);

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
