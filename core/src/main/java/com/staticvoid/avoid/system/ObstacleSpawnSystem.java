package com.staticvoid.avoid.system;

import com.badlogic.ashley.systems.IntervalSystem;
import com.badlogic.gdx.math.MathUtils;
import com.staticvoid.avoid.common.EntityFactory;
import com.staticvoid.avoid.config.GameConfig;

// executes logic every interval - not every frame
// doesn't care about any entities or any components
// it's just a utility that spawns an obstacle at regular intervals
public class ObstacleSpawnSystem extends IntervalSystem {

    private final EntityFactory factory;

    public ObstacleSpawnSystem(EntityFactory factory) {
        super(GameConfig.OBSTACLE_SPAWN_TIME);
        this.factory = factory;
    }

    @Override
    protected void updateInterval() {
        float min = 0;
        float max = GameConfig.WORLD_WIDTH - GameConfig.OBSTACLE_SIZE;

        float obstacleX = MathUtils.random(min, max);
        float obstacleY = GameConfig.WORLD_HEIGHT;

        factory.addObstacle(obstacleX, obstacleY);
    }
}
