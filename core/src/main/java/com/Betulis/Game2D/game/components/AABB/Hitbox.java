package com.Betulis.Game2D.game.components.AABB;

import com.Betulis.Game2D.engine.math.AABB;
import com.Betulis.Game2D.engine.system.Component;
import com.Betulis.Game2D.engine.system.Transform;

public final class Hitbox extends Component {

    private final AABB localBounds;
    private final AABB worldBounds = new AABB(0, 0, 0, 0);

    public Hitbox(float width, float height, float offsetX, float offsetY) {
        this.localBounds = new AABB(offsetX, offsetY, width, height);
    }

    @Override
    public void start() {
        updateWorldBounds();
    }

    @Override
    public void update(float dt) {
        updateWorldBounds();
    }

    private void updateWorldBounds() {
        Transform t = getGameObject().getTransform();

        float halfW = localBounds.width  * 0.5f;
        float halfH = localBounds.height * 0.5f;

        worldBounds.x = t.getWorldX() + localBounds.x - halfW;
        worldBounds.y = t.getWorldY() + localBounds.y - halfH;
        worldBounds.width  = localBounds.width;
        worldBounds.height = localBounds.height;
    }

    public AABB getWorldBounds() {
        return worldBounds;
    }
}

