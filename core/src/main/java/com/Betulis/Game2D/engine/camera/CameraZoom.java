package com.Betulis.Game2D.engine.camera;

import com.Betulis.Game2D.engine.system.Component;
import com.Betulis.Game2D.game.input.InputBindings;

public class CameraZoom extends Component {

    private InputBindings input;
    private Camera camera;

    private float zoomSpeed = 0.1f; // tweak to taste

    @Override
    public void start() {
        input = getScene().getGame().getInput();
        camera = getScene().getCamera(); // Camera might be on same object or fetched from scene
    }

    @Override
    public void update(float dt) {
        float scroll = input.getZoomDelta();
        if (scroll != 0) {
            camera.zoomBy(scroll * zoomSpeed);
        }
    }
}
