package com.Betulis.Game2D.game.input;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputAdapter;
import java.util.EnumMap;
import java.util.Map;

public final class InputBindings extends InputAdapter {

    public enum Action {
        DEBUG, MOVE_LEFT, MOVE_RIGHT, MOVE_UP, MOVE_DOWN, LIGHTNING_BOLT, FIREBALL
    }

    private final Map<Action, Integer> keyBindings = new EnumMap<>(Action.class);
    private final Map<Action, Integer> mouseBindings = new EnumMap<>(Action.class);

    // State tracking for event-based input
    private float scrollDelta = 0;
    private boolean isLeftMouseClicked = false;
    private boolean isRightMouseClicked = false;

    public InputBindings() {
        // Default Key Bindings
        keyBindings.put(Action.DEBUG,      Input.Keys.SPACE);
        keyBindings.put(Action.MOVE_LEFT,  Input.Keys.A);
        keyBindings.put(Action.MOVE_RIGHT, Input.Keys.D);
        keyBindings.put(Action.MOVE_UP,    Input.Keys.W);
        keyBindings.put(Action.MOVE_DOWN,  Input.Keys.S);

        // Default Mouse Bindings
        mouseBindings.put(Action.LIGHTNING_BOLT, Input.Buttons.RIGHT);
        mouseBindings.put(Action.FIREBALL,       Input.Buttons.LEFT);
    }

    // --- POLLING LOGIC ---

    /** Checks if a key or button is currently held down. */
    public boolean isHeld(Action action) {
        if (keyBindings.containsKey(action)) {
            return Gdx.input.isKeyPressed(keyBindings.get(action));
        }
        if (mouseBindings.containsKey(action)) {
            return Gdx.input.isButtonPressed(mouseBindings.get(action));
        }
        return false;
    }

    /** Returns the scroll amount and resets it for the next frame. */
    public float getZoomDelta() {
        float delta =- scrollDelta;
        scrollDelta = 0; // Consume the event
        return delta;
    }

    // --- INPUT PROCESSOR OVERRIDES ---

    @Override
    public boolean scrolled(float amountX, float amountY) {
        // In LibGDX, amountY is the vertical scroll. 
        // Positive usually means scrolling down (zoom out).
        this.scrollDelta = amountY;
        return true; 
    }

    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {
        // This is called exactly ONCE when the mouse is clicked
        if (button == Input.Buttons.LEFT) isLeftMouseClicked = true;
        if (button == Input.Buttons.RIGHT) isRightMouseClicked = true;
        return false; 
    }

    /** * Use this for actions like "Fireball" if you only want 
     * one shot per click, even if the button is held.
     */
    public boolean wasMouseClicked(int button) {
        if (button == Input.Buttons.LEFT && isLeftMouseClicked) {
            isLeftMouseClicked = false;
            return true;
        }
        if (button == Input.Buttons.RIGHT && isRightMouseClicked) {
            isRightMouseClicked = false;
            return true;
        }
        return false;
    }

    // --- BINDING MANAGEMENT ---

    public void rebindKey(Action action, int newLibgdxKey) {
        keyBindings.put(action, newLibgdxKey);
    }

    public void rebindMouse(Action action, int newLibgdxButton) {
        mouseBindings.put(action, newLibgdxButton);
    }
}