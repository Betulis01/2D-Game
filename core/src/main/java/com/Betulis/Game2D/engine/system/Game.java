package com.Betulis.Game2D.engine.system;

import com.Betulis.Game2D.engine.utils.Assets;
import com.Betulis.Game2D.game.input.InputBindings;
import com.Betulis.Game2D.game.scenes.DeathValley;
import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.ScreenUtils;

/** {@link com.badlogic.gdx.ApplicationListener} implementation shared by all platforms. */
public class Game extends ApplicationAdapter {
    private SpriteBatch batch;
    private Scene scene;
    private InputBindings input;
    private BitmapFont font;
    private Assets assets;

    //screen
    private int screenWidth, screenHeight;

    // fps
    private int fps;

    @Override
    public void create() {
        System.out.println("Starting game...");
        batch = new SpriteBatch();
        font = new BitmapFont();

        initWindow();
        initInput();
        initSystems();


        scene = new DeathValley();
        scene.load(this);
    }

    public void initWindow() {
        screenWidth = Gdx.graphics.getWidth();
        screenHeight = Gdx.graphics.getHeight();
    }

    public void initInput() {
        input = new InputBindings();
        Gdx.input.setInputProcessor(input);
    }

    public void initSystems() {
        assets = new Assets();
        assets.load();
    }


    @Override
    public void render() {
        ScreenUtils.clear(0, 0, 0, 1);
        fps = Gdx.graphics.getFramesPerSecond();
        float dt = Gdx.graphics.getDeltaTime();
        
        scene.update(dt); //update
        
        batch.begin();
        scene.render(batch); //render
        font.draw(batch, "FPS: " + fps, 10, 20);
        batch.end();
    }
    
    @Override public void resize(int width, int height) {}
    @Override public void pause() {}
    @Override public void resume() {}
    @Override public void dispose() { batch.dispose(); }


    public InputBindings getInput() {
        return input;
    }

    public Assets getAssets() {
        return assets;
    }

    public int getScreenWidth() {
        return screenWidth;
    }

    public int getScreenHeight() {
        return screenHeight;
    }

}
