package com.Betulis.Game2D.game.input;

import java.util.EnumMap;
import java.util.Map;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;

public final class InputBindings {

    // LibGDX uses int for Keys and Buttons, not Objects
    private final Map<Action, Integer> keyBindings;
    private final Map<Action, Integer> mouseBindings;

    public enum Action {
        DEBUG,
        MOVE_LEFT,
        MOVE_RIGHT,
        MOVE_UP,
        MOVE_DOWN,
        LIGHTNING_BOLT,
        FIREBALL
    }

    public InputBindings() {
        this.keyBindings = new EnumMap<>(Action.class);
        this.mouseBindings = new EnumMap<>(Action.class);

        // Default bindings using LibGDX constants
        keyBindings.put(Action.DEBUG,       Input.Keys.SPACE);
        keyBindings.put(Action.MOVE_LEFT,   Input.Keys.A);
        keyBindings.put(Action.MOVE_RIGHT,  Input.Keys.D);
        keyBindings.put(Action.MOVE_UP,     Input.Keys.W);
        keyBindings.put(Action.MOVE_DOWN,   Input.Keys.S);

        // LibGDX Buttons: LEFT (Primary), RIGHT (Secondary), MIDDLE, etc.
        mouseBindings.put(Action.LIGHTNING_BOLT, Input.Buttons.RIGHT);
        mouseBindings.put(Action.FIREBALL,       Input.Buttons.LEFT);
    }

    // "Pressed" usually implies a single trigger (Just Pressed)
    public boolean isPressed(Action action) {
        if (keyBindings.containsKey(action)) {
            return Gdx.input.isKeyJustPressed(keyBindings.get(action));
        }
        if (mouseBindings.containsKey(action)) {
            return Gdx.input.isButtonJustPressed(mouseBindings.get(action));
        }
        return false;
    }

    // "Held" implies continuous state
    public boolean isHeld(Action action) {
        if (keyBindings.containsKey(action)) {
            return Gdx.input.isKeyPressed(keyBindings.get(action));
        }
        if (mouseBindings.containsKey(action)) {
            return Gdx.input.isButtonPressed(mouseBindings.get(action));
        }
        return false;
    }

    public void bind(Action action, int key) {
        keyBindings.put(action, key);
    }

    // NOTE: LibGDX does not allow static polling for scroll delta. 
    // You must implement an InputProcessor to capture scroll events.
    // For now, this returns 0 to prevent compilation errors.
    public double getZoomDelta() {
        return 0; 
    }

    public int getBinding(Action action) {
        return keyBindings.getOrDefault(action, -1);
    }
}