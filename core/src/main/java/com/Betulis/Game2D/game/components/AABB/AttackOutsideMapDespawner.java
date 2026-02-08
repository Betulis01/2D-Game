package com.Betulis.Game2D.game.components.AABB;

import com.Betulis.Game2D.engine.math.AABB;
import com.Betulis.Game2D.engine.system.Component;

public class AttackOutsideMapDespawner extends Component {
    @Override
    public void update(float dt) {
        Hitbox hitbox = getGameObject().getComponent(Hitbox.class);
        if (hitbox == null) return;

        AABB hb = hitbox.getWorldBounds();
        if (hb == null) return;

        AABB mapBounds = getScene().getMapBounds();
        if (mapBounds == null) return;

        if (!mapBounds.intersects(hb)) {
            getGameObject().destroy();
        }
    }
}
