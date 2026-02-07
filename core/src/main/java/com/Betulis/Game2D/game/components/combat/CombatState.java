package com.Betulis.Game2D.game.components.combat;

import com.Betulis.Game2D.engine.system.Component;

public final class CombatState extends Component {

    private boolean inCombat = false;
    private double lastCombatTime = 0;

    public boolean isInCombat() {
        return inCombat;
    }

    public void enterCombat() {
        inCombat = true;
        lastCombatTime = getScene().getGame().getTime();
    }

    public void exitCombat() {
        inCombat = false;
    }

    public void markCombatEvent() {
        enterCombat();
    }

    @Override
    public void update(float dt) {
        if (inCombat && getScene().getGame().getTime() - lastCombatTime > 10.0) {
            exitCombat();
        }
    }
}
