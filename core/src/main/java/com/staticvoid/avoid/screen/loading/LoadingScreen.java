package com.staticvoid.avoid.screen.loading;

import com.badlogic.gdx.Application;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.utils.Logger;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.staticvoid.avoid.ObstacleAvoidGame;
import com.staticvoid.avoid.assets.AssetDescriptors;
import com.staticvoid.avoid.config.GameConfig;
import com.staticvoid.avoid.screen.menu.MenuScreen;
import com.staticvoid.avoid.util.GdxUtils;

public class LoadingScreen extends ScreenAdapter {

    // == constants
    private static final Logger log =
            new Logger(LoadingScreen.class.getName(), Application.LOG_DEBUG);

    // world units
    private static final float PROGRESS_BAR_WIDTH =
            GameConfig.HUD_WIDTH / 2f;
    private static final float PROGRESS_BAR_HEIGHT = 60;

    // == attributes
    private OrthographicCamera camera;
    private Viewport viewport;
    private ShapeRenderer renderer;

    private float progress;

    // interval to pass after all assets are loaded
    private float waitTime = 0.75f;
    private boolean changeScreen;

    private final ObstacleAvoidGame game;
    private final AssetManager assetManager;

    // == constructors ==
    public LoadingScreen(ObstacleAvoidGame game) {
        this.game = game;
        assetManager = game.getAssetManager();
    }

    // == public methods ==

    @Override
    public void show() {
        // initialize
        camera = new OrthographicCamera();
        // world units
        viewport = new FitViewport(GameConfig.HUD_WIDTH, GameConfig.HUD_HEIGHT, camera);
        renderer = new ShapeRenderer();

        // load assets
        assetManager.load(AssetDescriptors.FONT);
        assetManager.load(AssetDescriptors.GAME_PLAY); // atlas
        assetManager.load(AssetDescriptors.UI);
        assetManager.load(AssetDescriptors.UI_SKIN);
        assetManager.load(AssetDescriptors.HIT_SOUND);
    }

    @Override
    public void render(float deltaTime) {
        update(deltaTime);

        GdxUtils.clearScreen();

        // tells open gl we want to use the viewport for all drawing
        // always apply the viewport here, prior to setting projection
        // matrix and the batch begin and end drawing calls
        viewport.apply();
        renderer.setProjectionMatrix(camera.combined);
        renderer.begin(ShapeRenderer.ShapeType.Filled);

        draw();

        renderer.end();

        if (changeScreen) {
            game.setScreen(new MenuScreen(game));
        }
    }

    // draw progress bar
    private void draw() {
        // TODO:  optimize by making these fields
        float progressBarX = (GameConfig.HUD_WIDTH -
                PROGRESS_BAR_WIDTH) / 2f;
        float progressBarY = (GameConfig.HUD_HEIGHT -
                PROGRESS_BAR_HEIGHT) / 2f;

        renderer.rect(progressBarX, progressBarY,
                progress * PROGRESS_BAR_WIDTH, PROGRESS_BAR_HEIGHT
        );

    }

    private void update(float deltaTime) {
        // progress is tween 0 and 1
        progress = assetManager.getProgress(); // 0 to 1

        // true if loading is complete
        if (assetManager.update()) {
            waitTime -= deltaTime;
            // here we avoid the pitfall of attempting to change screens
            // in the midst of a batch.draw call

            // * do not dispose the renderer in the midst of an upate *
            if (waitTime <= 0) {
                changeScreen = true;
            }
        }

    }

    @Override
    public void resize(int width, int height) {
        viewport.update(width, height, true);
    }

    @Override
    public void hide() {
        // screens don't dispose automatically - they get hidden
        // so call dispose on hide
        dispose();
    }

    @Override
    public void dispose() {
        renderer.dispose();
    }
}


/*
Whenever a new screen is called method hide is called on the old
screen and then show is called on the new screen.

Skin class stores resources for the UI and widgets can use those.
Skin is a container for TextureRegions Fonts Colors etc.
 */