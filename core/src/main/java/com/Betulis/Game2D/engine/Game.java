package com.Betulis.Game2D.engine;

import com.Betulis.Game2D.game.DeathValley;
import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.utils.ScreenUtils;

/** {@link com.badlogic.gdx.ApplicationListener} implementation shared by all platforms. */
public class Game extends ApplicationAdapter {
    private SpriteBatch batch;
    private Scene scene;
    private BitmapFont font;

    // fps
    private int fps;

    @Override
    public void create() {
        System.out.println("Starting game...");
        batch = new SpriteBatch();
        font = new BitmapFont();
        scene = new DeathValley();
        scene.load();
    }

    @Override
    public void render() {
        ScreenUtils.clear(0, 0, 0, 1);
        fps = Gdx.graphics.getFramesPerSecond();
        float dt = Gdx.graphics.getDeltaTime();

        if (Gdx.input.isButtonPressed(Input.Buttons.LEFT)) {
            System.out.println("Left Mouse");
        }


        ShapeRenderer sr = new ShapeRenderer();
        sr.begin(ShapeRenderer.ShapeType.Filled);
        sr.rect(100, 100, 50, 50);
        sr.end();

        batch.begin();
        scene.update(dt);
        scene.render(batch);

        font.draw(batch, "FPS: " + fps, 10, 20);

        batch.end();
    }
    
    @Override public void resize(int width, int height) {}
    @Override public void pause() {}
    @Override public void resume() {}
    @Override public void dispose() { batch.dispose(); }
}
