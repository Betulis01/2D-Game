package com.Betulis.Game2D.game.prefabs.camera;

import com.Betulis.Game2D.engine.camera.Camera;
import com.Betulis.Game2D.engine.camera.CameraZoom;
import com.Betulis.Game2D.engine.system.GameObject;
import com.Betulis.Game2D.engine.system.Transform;

public class CameraPrefab {
    
    public GameObject create(Transform target, float width, float height) {
        GameObject cameraObj = new GameObject("Camera");
        Camera camera = new Camera(width,height);
        camera.follow(target); // follow e.g. player 
        cameraObj.addComponent(camera);
        cameraObj.addComponent(new CameraZoom());
        
        return cameraObj;
    }
}
