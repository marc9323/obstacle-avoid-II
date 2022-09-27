package com.staticvoid.avoid.common;

import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.PooledEngine;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.staticvoid.avoid.assets.AssetDescriptors;
import com.staticvoid.avoid.assets.RegionNames;
import com.staticvoid.avoid.component.BoundsComponent;
import com.staticvoid.avoid.component.CleanUpComponent;
import com.staticvoid.avoid.component.DimensionComponent;
import com.staticvoid.avoid.component.MovementComponent;
import com.staticvoid.avoid.component.ObstacleComponent;
import com.staticvoid.avoid.component.PlayerComponent;
import com.staticvoid.avoid.component.PositionComponent;
import com.staticvoid.avoid.component.TextureComponent;
import com.staticvoid.avoid.component.WorldWrapComponent;
import com.staticvoid.avoid.config.GameConfig;

public class EntityFactory {

    private final PooledEngine engine;
    private final AssetManager assetManager;
    private final TextureAtlas gamePlayAtlas;

    // adds entities to the engine
    public EntityFactory(PooledEngine engine, AssetManager assetManager) {
        this.engine = engine;
        this.assetManager = assetManager;
        gamePlayAtlas = assetManager.get(AssetDescriptors.GAME_PLAY);
    }

    public void addPlayer() {
        // center player inside screen at bottom
        float x = (GameConfig.WORLD_WIDTH - GameConfig.PLAYER_SIZE) / 2f;
        float y = 1- GameConfig.PLAYER_SIZE / 2f;

        PositionComponent position =
                engine.createComponent(PositionComponent.class);
        position.x = x;
        position.y = y;

        // engine will create it for us
        BoundsComponent bounds = engine.createComponent(
                BoundsComponent.class
        );
        bounds.bounds.set(x, y, GameConfig.PLAYER_BOUNDS_RADIUS);

        MovementComponent movement = engine.createComponent(MovementComponent.class);
        // movement.xSpeed = GameConfig.MAX_PLAYER_X_SPEED;
        //movement.ySpeed = 0;
        PlayerComponent player = engine.createComponent(PlayerComponent.class);
        WorldWrapComponent worldWrap = engine.createComponent(WorldWrapComponent.class);

        TextureComponent texture = engine.createComponent(TextureComponent.class);
        texture.region = gamePlayAtlas.findRegion(RegionNames.PLAYER);

        DimensionComponent dimension = engine.createComponent(DimensionComponent.class);
        dimension.width = GameConfig.PLAYER_SIZE;
        dimension.height = GameConfig.PLAYER_SIZE;

        Entity entity = engine.createEntity(); // create Player Entity

        entity.add(bounds); // add the Bounds component to Player Entity
        entity.add(movement);
        entity.add(player);
        entity.add(position);
        entity.add(worldWrap);
        entity.add(texture);
        entity.add(dimension);

        engine.addEntity(entity); // add player entity to engine
    }

    public void addObstacle(float x, float y) {
        BoundsComponent bounds = engine.createComponent(BoundsComponent.class);
        bounds.bounds.set(x, y, GameConfig.OBSTACLE_BOUNDS_RADIUS);

        MovementComponent movement = engine.createComponent(MovementComponent.class);
        movement.ySpeed = -GameManager.INSTANCE.getDifficultyLevel().getObstacleSpeed();

        PositionComponent position = engine.createComponent(PositionComponent.class);
        position.x = x;
        position.y = y;

        // marks for clean up
        CleanUpComponent cleanUp = engine.createComponent(CleanUpComponent.class);

        ObstacleComponent obstacle = engine.createComponent(ObstacleComponent.class);

        TextureComponent texture = engine.createComponent(TextureComponent.class);
        texture.region = gamePlayAtlas.findRegion(RegionNames.OBSTACLE);

        DimensionComponent dimension = engine.createComponent(DimensionComponent.class);
        dimension.width = GameConfig.OBSTACLE_SIZE;
        dimension.height = GameConfig.OBSTACLE_SIZE;

        // NOTE: alternative solution to collision logic error
        // better to implement Poolable inside ObstacleComponent
        //  obstacle.hit = false;

        Entity entity = engine.createEntity();

        entity.add(bounds);
        entity.add(movement);
        entity.add(position);
        entity.add(cleanUp);
        entity.add(obstacle);
        entity.add(texture);
        entity.add(dimension);

        engine.addEntity(entity);
    }

    public void addBackground() {
        TextureComponent texture = engine.createComponent(TextureComponent.class);
        texture.region = gamePlayAtlas.findRegion(RegionNames.BACKGROUND);

        PositionComponent position = engine.createComponent(PositionComponent.class);
        position.x = 0;
        position.y = 0;

        DimensionComponent dimension = engine.createComponent(DimensionComponent.class);
        dimension.width = GameConfig.WORLD_WIDTH;
        dimension.height = GameConfig.WORLD_HEIGHT;

        Entity entity = engine.createEntity();
        entity.add(texture);
        entity.add(position);
        entity.add(dimension);

        engine.addEntity(entity);
    }
}
