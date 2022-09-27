package com.staticvoid.avoid.config;

public class GameConfig {

    public static final float WIDTH = 480f; // pixel
    public static final float HEIGHT = 800f; // pixel

    // think in world units despite the world unit
    // pixel ratio being one to one here
    public static final float HUD_WIDTH = 480f; // world units
    public static final float HUD_HEIGHT = 800f; // world units

    // 6 x 10
    public static final float WORLD_WIDTH =  6.0f; // world units
    public static final float WORLD_HEIGHT = 10.0f; // world unit

    public static final float WORLD_CENTER_X = WORLD_WIDTH / 2f; // world units
    public static final float WORLD_CENTER_Y = WORLD_HEIGHT / 2f; // world units

    public static final float MAX_PLAYER_X_SPEED = 0.25f; // max player speed

    public static final float OBSTACLE_SPAWN_TIME = 0.25f; // spawn interval

    public static final float SCORE_MAX_TIME = 1.25f; // update score interval
    public static final int LIVES_START = 3; // starting lives

    public static final float EASY_OBSTACLE_SPEED = 0.1f;
    public static final float MEDIUM_OBSTACLE_SPEED = .15f;
    public static final float HARD_OBSTACLE_SPEED = .18f;

    // all world units
    public static final float PLAYER_BOUNDS_RADIUS = 0.4f; // world units
    public static final float PLAYER_SIZE = 2 * PLAYER_BOUNDS_RADIUS; // world units

    public static final float OBSTACLE_BOUNDS_RADIUS = 0.3f; // world units
    public static final float OBSTACLE_SIZE = 2 * OBSTACLE_BOUNDS_RADIUS;

    private GameConfig() {
        // private constructor - don't instantiate
    }
}
