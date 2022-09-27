package com.staticvoid.avoid.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Logger;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.staticvoid.avoid.assets.AssetPaths;
import com.staticvoid.avoid.config.DifficultyLevel;
import com.staticvoid.avoid.config.GameConfig;
import com.staticvoid.avoid.entity.Obstacle;
import com.staticvoid.avoid.entity.Player;
import com.staticvoid.avoid.util.GdxUtils;
import com.staticvoid.avoid.util.ViewportUtils;
import com.staticvoid.avoid.util.debug.DebugCameraController;

@Deprecated
public class GameScreenOld implements Screen {

    private static final Logger log = new Logger(GameScreenOld.class.getName(), Logger.DEBUG);

    // game
    private OrthographicCamera camera;
    private Viewport viewport;
    private ShapeRenderer renderer;

    // hud requires another camera and another viewport
    private OrthographicCamera hudCamera;
    private Viewport hudViewport;
    private SpriteBatch batch;
    private BitmapFont font;
    private final GlyphLayout layout = new GlyphLayout();

    private Player player;
    private Array<Obstacle> obstacles = new Array<Obstacle>();
    private float obstacleTimer;
    private float scoreTimer;

    private int lives = GameConfig.LIVES_START;
    private int score;
    private int displayScore;

    private DifficultyLevel difficultyLevel = DifficultyLevel.MEDIUM;

    private DebugCameraController debugCameraController;

    // like create, initialize
    @Override
    public void show() {
        // Game Camera and FitViewport
        camera = new OrthographicCamera();
        viewport = new FitViewport(GameConfig.WORLD_WIDTH, GameConfig.WORLD_HEIGHT, camera);
        renderer = new ShapeRenderer();

        // HUD Camera
        hudCamera = new OrthographicCamera();
        hudViewport = new FitViewport(GameConfig.HUD_WIDTH, GameConfig.HUD_HEIGHT, hudCamera);
        batch = new SpriteBatch();
        font = new BitmapFont(Gdx.files.internal(AssetPaths.UI_FONT));


        // create player and FitViewport
        player = new Player();

        // calculate position, WORLD_WIDTH -> game units
        float startPlayerX = GameConfig.WORLD_WIDTH / 2f;
        float startPlayerY = 1;

        // position player
        player.setPosition(startPlayerX, startPlayerY);

        // create DebugCameraController
        debugCameraController = new DebugCameraController();
        debugCameraController.setStartPosition(GameConfig.WORLD_CENTER_X, GameConfig.WORLD_CENTER_Y);

    }

    @Override
    public void render(float deltaTime) {
        // purposely not wrapped inside alive, camera accessible while paused
        debugCameraController.handleDebugInput(deltaTime);
        debugCameraController.applyTo(camera);

        // update world
        update(deltaTime);

        // clear screen
        GdxUtils.clearScreen();

        // render ui/HUD
        renderUi();

        // drawDebug();
        renderDebug();
    }

    private void renderUi() {
        batch.setProjectionMatrix(hudCamera.combined);
        batch.begin();

        String livesText = "LIVES: " + lives;
        layout.setText(font, livesText); // GlyphLayout

        font.draw(batch, livesText,
                20,
                GameConfig.HUD_HEIGHT - layout.height
        );

        String scoreText = "SCORE: " + displayScore;
        layout.setText(font, scoreText); // reuse the GlyphLayout

        // layout.width --> width of scoreText
        // 20 --> some padding
        font.draw(batch, scoreText,
                GameConfig.HUD_WIDTH - layout.width - 20,
                GameConfig.HUD_HEIGHT - layout.height
        );

        batch.end();
    }

    @Deprecated
    private void update(float deltaTime) {
        if (isGameOver()) {
            log.debug("Game Over!");
            return;
        }

        updatePlayer();
        updateObstacles(deltaTime);
        updateScore(deltaTime);
        updateDisplayScore(deltaTime);

        if (isPlayerCollidingWithObstacle()) {
            log.debug("Collision Detected!  BAM!");
            lives--;
        }
    }

    private boolean isGameOver() {
        return lives <= 0;
    }

    // once marked as hit it no longer will count as collision
    // for overlap on next few frames
    // result:  player loses just a single life
    private boolean isPlayerCollidingWithObstacle() {
        for (Obstacle obstacle : obstacles) {
            if (!obstacle.isHit() && obstacle.isPlayerColliding(player)) {
                return true;
            }
        }
        // NOTE:  video 79 -- I like above better, interchangeable
//        if (obstacle.isNotHit() && obstacle.isPlayerColliding(player)) {
//            return true;
//        }
        return false;
    }

    private void updateObstacles(float deltaTime) {
        for (Obstacle obstacle : obstacles) {
            obstacle.update();
        }

        createNewObstacle(deltaTime);
    }

    private void createNewObstacle(float deltaTime) {
        obstacleTimer += deltaTime;

        if (obstacleTimer > GameConfig.OBSTACLE_SPAWN_TIME) {
            float min = 0f;
            float max = GameConfig.WORLD_WIDTH;
            float obstacleX = MathUtils.random(min, max);
            float obstacleY = GameConfig.WORLD_HEIGHT;

            Obstacle obstacle = new Obstacle();
            obstacle.setYSpeed(difficultyLevel.getObstacleSpeed());
            obstacle.setPosition(obstacleX, obstacleY);

            obstacles.add(obstacle);

            obstacleTimer = 0f;
        }
    }

    private void updatePlayer() {
    //    player.update(); // winds up called each frame, input polling
        blockPlayerFromLeavingTheWorld();
    }

    private void blockPlayerFromLeavingTheWorld() {
        // this line is equivalent to the following commented out lines
        // feed clamp:  value to clamp, minimum, maximum.
        float playerX = MathUtils.clamp(player.getX(),
                player.getWidth() / 2f,
                GameConfig.WORLD_WIDTH - player.getWidth() / 2f);

        player.setPosition(playerX, player.getY());
    }

    private void renderDebug() {
        renderer.setProjectionMatrix(camera.combined);

        renderer.begin(ShapeRenderer.ShapeType.Line);

        // player and obstacles drawn here
        drawDebug();

        renderer.end();

        ViewportUtils.drawGrid(viewport, renderer);
    }

    // if camera is not centered, 0 x 0 is the center, the intsersection of x and y red axis
    private void drawDebug() {
        player.drawDebug(renderer);

        for (Obstacle obstacle : obstacles) {
            obstacle.drawDebug(renderer);
        }
    }

    private void updateScore(float deltaTime) {
        // score is added to a random intervals
        // the longer player lives, the more points racked up
        scoreTimer += deltaTime;

        if (scoreTimer >= GameConfig.SCORE_MAX_TIME) {
            score += MathUtils.random(1, 5); // min 1, max 4, inclusive/exclusive
            scoreTimer = 0.0f;
        }
    }

    // increment score displayScore by 1 each frame
    private void updateDisplayScore(float deltaTime) {
        // 1/60 * 60 --> score increments by one each frame
        if (displayScore < score) {
            displayScore = Math.min(
                    score,
                    displayScore + (int) (60 * deltaTime)
            );
        }
    }

    @Override
    public void dispose() {
        renderer.dispose();
        batch.dispose();
        font.dispose();
    }

    @Override
    public void resize(int width, int height) {
        // camera center is very important
        // two viewports, two cameras, two calls to update
        viewport.update(width, height, true);
        hudViewport.update(width, height, true);

        ViewportUtils.debugPixelPerUnit(viewport);
    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {
        // hide when going from one screen to another
        dispose();
    }

}


/*
NOTES:
For HUD Camera and HUD viewport, world size is a lot bigger.
For HUD Camera and HUD Viewport world size is 800 x 480
Whereas for Game Camera and World Viewport the world size
is 6 x 10

Video # 77
1.)  Be careful to set projection matrix correctly and for each camera/viewport
2.)  Be sure to update every camera/viewport - resize() method

REVIEW:
1.)  video 79 --> isPlayerCollidingWithObstacle and usage of hit to make
the lives system work.  Issue:  lives were decremented every frame during which
player/obstacle had overlap

if (obstacle.isNotHit() && obstacle.isPlayerColliding(player)) {
                return true;
            }
 */