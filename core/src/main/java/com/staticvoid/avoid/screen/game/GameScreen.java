package com.staticvoid.avoid.screen.game;

import com.badlogic.ashley.core.PooledEngine;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.utils.Logger;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.staticvoid.avoid.ObstacleAvoidGame;
import com.staticvoid.avoid.assets.AssetDescriptors;
import com.staticvoid.avoid.common.EntityFactory;
import com.staticvoid.avoid.common.GameManager;
import com.staticvoid.avoid.config.GameConfig;
import com.staticvoid.avoid.screen.menu.MenuScreen;
import com.staticvoid.avoid.system.BoundsSystem;
import com.staticvoid.avoid.system.CleanUpSystem;
import com.staticvoid.avoid.system.HudRenderSystem;
import com.staticvoid.avoid.system.MovementSystem;
import com.staticvoid.avoid.system.ObstacleSpawnSystem;
import com.staticvoid.avoid.system.PlayerSystem;
import com.staticvoid.avoid.system.RenderSystem;
import com.staticvoid.avoid.system.ScoreSystem;
import com.staticvoid.avoid.system.WorldWrapSystem;
import com.staticvoid.avoid.system.collision.CollisionListener;
import com.staticvoid.avoid.system.collision.CollisionSystem;
import com.staticvoid.avoid.system.debug.DebugCameraSystem;
import com.staticvoid.avoid.system.debug.DebugRenderSystem;
import com.staticvoid.avoid.system.debug.GridRenderSystem;
import com.staticvoid.avoid.util.GdxUtils;


// Rendering and game logic are neatly separated

public class GameScreen implements Screen {

    private static final Logger log = new Logger(GameScreen.class.getName(), Logger.DEBUG);

    private static boolean DEBUG = true;

    private final ObstacleAvoidGame game;
    private final AssetManager assetManager;

    private OrthographicCamera camera;
    private Viewport viewport;
    private Viewport hudViewport; // again, HUD and game have different ports
    private ShapeRenderer renderer;
    // private SpriteBatch batch; // for the HUD
    // PooledEngine pools entities and components
    // so we no longer need explicit pools - does it for us
    private PooledEngine engine;
    private EntityFactory factory;
    private Sound hit;

    private boolean reset;

    public GameScreen(ObstacleAvoidGame game) {
        this.game = game;
        assetManager = game.getAssetManager();

    }

    @Override
    public void show() {
        log.debug("show()");
        camera = new OrthographicCamera();
        viewport = new FitViewport(GameConfig.WORLD_WIDTH,
                GameConfig.WORLD_HEIGHT,
                camera);
        hudViewport = new FitViewport(
                GameConfig.HUD_WIDTH,
                GameConfig.HUD_HEIGHT
        ); // no parameter is passed so camera internally generated
        renderer = new ShapeRenderer();
        // batch = new SpriteBatch(); // for HUD
        engine = new PooledEngine();
        factory = new EntityFactory(engine, assetManager);

        // BitmapFont for the HUD
        BitmapFont font = assetManager.get(AssetDescriptors.FONT);
        hit = assetManager.get(AssetDescriptors.HIT_SOUND);

        // called when there is a collision
        // the collision system has a listener and calls this code
        // when a collision is detected so that proper game logic
        // executes
        CollisionListener listener = new CollisionListener() {
            @Override
            public void hitObstacle() {
                // reset player to start
                // remove all obstacles
                // restart to main if lives depleted
                GameManager.INSTANCE.decrementLives();
                hit.play();

                if (GameManager.INSTANCE.isGameOver()) {
                    GameManager.INSTANCE.updateHighScore();
                } else {
                    // typically entities are removed
                    // when the requesting system updates
                    engine.removeAllEntities();
                    //   factory.addPlayer();
                    reset = true;

                    // TODO: remove reset flag, if stmt in render and addEntities method
                }
            }
        };

        // add systems and Player to engine
        engine.addSystem(
                new DebugCameraSystem(camera,
                        GameConfig.WORLD_CENTER_X,
                        GameConfig.WORLD_CENTER_Y)
        );


        // add DebugRenderSystem, PlayerSystem
        // NOTE:  systems must be in logical order

        engine.addSystem(new PlayerSystem()); // handling input
        engine.addSystem(new MovementSystem()); // changing position based on speed
        engine.addSystem(new WorldWrapSystem(viewport));
        engine.addSystem(new BoundsSystem());
        engine.addSystem(new ObstacleSpawnSystem(factory));

        engine.addSystem(new CleanUpSystem());
        engine.addSystem(new CollisionSystem(listener));
        engine.addSystem(new ScoreSystem());

        engine.addSystem(new RenderSystem(viewport, game.getBatch()));
        engine.addSystem(new HudRenderSystem(
                hudViewport,
                game.getBatch(),
                font
                // so we just needed to create the hudViewport and font
        ));

        if(DEBUG) {
            engine.addSystem(
                    new GridRenderSystem(viewport, renderer)
            );
            engine.addSystem(new DebugRenderSystem(viewport, renderer));

            engine.addSystem(new DebugCameraSystem(camera,
                    GameConfig.WORLD_CENTER_X, GameConfig.WORLD_CENTER_Y));
        }
        // HUD needs to overlay everything so add it last
        // the game and the HUD use different viewports
        // take care to add the right one!


        // builds player adds to engine
        // TODO: revert to uncomment out factory.addPlayer()
        //      factory.addPlayer();
        addEntities();
        // system added last is executed last
    }

    @Override
    public void render(float delta) {
        GdxUtils.clearScreen();
        engine.update(delta);

        if (GameManager.INSTANCE.isGameOver()) {
            GameManager.INSTANCE.reset();
            game.setScreen(new MenuScreen(game));
        }

        if (reset) {
            reset = false;
            addEntities();
        }
        //   log.debug("entities size = " + engine.getEntities().size());
    }

    private void addEntities() {
        factory.addBackground(); //  order is important for rendering!
        factory.addPlayer();
    }

    @Override
    public void resize(int width, int height) {
        viewport.update(width, height, true);
        hudViewport.update(width, height, true);
    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {
        log.debug("hide");
        dispose();
    }

    @Override
    public void dispose() {
        renderer.dispose();
        // batch.dispose();
    }
}

/*
SpriteBatch renders from bottom left corner
whereas renderer renders circle from the center

How to listen for components added / removed

Lots of this code is re-usable:
RenderSystem
Debug systems
MovementSystem
ScoreSystem
WorldWrapSystem
BoundsSystem
 */


/*
TODO:  make a life capsule fall from the sky at 5 second intervals
 */