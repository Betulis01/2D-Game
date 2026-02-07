package com.Betulis.Game2D.game.input;

import com.Betulis.Game2D.engine.camera.Camera;
import com.Betulis.Game2D.engine.math.Vector2;
import com.Betulis.Game2D.engine.system.Component;
import com.Betulis.Game2D.engine.system.Transform;
import com.Betulis.Game2D.game.components.combat.AttackSpawner;
import com.badlogic.gdx.Gdx;

public final class PlayerInput extends Component {

    private InputBindings input;
    // Temp vector to avoid garbage collection during unprojection
    private final Vector2 mousePos = new Vector2(); 

    @Override
    public void start() {
        // Assuming getEngine().getInput() now returns the LibGDX InputBindings above
        input = getScene().getGame().getInput();
    }

    @Override
    public void update(float dt) {
        // Handle Lightning Bolt
        if (input.isPressed(InputBindings.Action.LIGHTNING_BOLT)) {
            castAbility("/data/config/abilities/lightning_bolt.json");
        }

        // Handle Fireball
        if (input.isPressed(InputBindings.Action.FIREBALL)) {
            castAbility("/data/config/abilities/fireball.json");
        }
    }

    private void castAbility(String configPath) {
        Camera cam = getScene().getCamera();
        
        // 1. Get raw screen coordinates
        float screenX = Gdx.input.getX();
        float screenY = Gdx.input.getY();

        // 2. Unproject: Convert Screen (Pixels) to World (Meters/Units)
        // Note: verify if your Camera wrapper has a helper for this. 
        // Standard LibGDX looks like: cam.unproject(mousePos.set(screenX, screenY, 0));
        float wx = cam.screenToWorldX(screenX);
        float wy = cam.screenToWorldY(screenY);

        Transform t = getGameObject().getTransform();
        float dx = wx - t.getWorldX();
        float dy = wy - t.getWorldY();

        //getGameObject().getComponent(AttackSpawner.class).setAttack(new ConfigLoader().load(configPath));
        
        getGameObject().getComponent(AttackSpawner.class)
            .tryAttack(dx, dy);
    }
}