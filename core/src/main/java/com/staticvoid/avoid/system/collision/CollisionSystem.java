package com.staticvoid.avoid.system.collision;

import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.EntitySystem;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.utils.ImmutableArray;
import com.badlogic.gdx.math.Intersector;
import com.badlogic.gdx.utils.Logger;
import com.staticvoid.avoid.common.Mappers;
import com.staticvoid.avoid.component.BoundsComponent;
import com.staticvoid.avoid.component.ObstacleComponent;
import com.staticvoid.avoid.component.PlayerComponent;

// has two families
public class CollisionSystem extends EntitySystem {
    private static final Logger log =
            new Logger(CollisionSystem.class.getName(),
                    Logger.DEBUG);

    // things that can collide:  Player and Obstacles
    private static final Family PLAYER_FAMILY =
            Family.all(
                    PlayerComponent.class,
                    BoundsComponent.class
            ).get();

    private static final Family OBSTACLE_FAMILY =
            Family.all(
                    ObstacleComponent.class,
                    BoundsComponent.class
            ).get();

    private final CollisionListener listener;

    public CollisionSystem(CollisionListener listener) {
        // constructor
        this.listener = listener;
    }

    @Override
    public void update(float deltaTime) {
        // get all players and obstacles
        // size of players array will always be 1
        ImmutableArray<Entity> players =
                getEngine().getEntitiesFor(PLAYER_FAMILY);
        ImmutableArray<Entity> obstacles =
                getEngine().getEntitiesFor(OBSTACLE_FAMILY);

        // loop through - use loop as later expansion might add more
        for (Entity playerEntity : players) {
            for (Entity obstacleEntity : obstacles) {
                ObstacleComponent obstacle = Mappers.OBSTACLE.get(obstacleEntity);

                if (obstacle.hit) {
                    continue; // continue processing other obstacles
                }

                if (checkCollision(playerEntity, obstacleEntity)) {
                    obstacle.hit = true;
                    log.debug("COLLISION WITH OBSTACLE");
                    listener.hitObstacle();
                }
            }
        }
    }

    private boolean checkCollision(Entity player,
                                   Entity obstacle) {

        // get the bounds components for both so we can check for intersection
        BoundsComponent playerBounds = Mappers.BOUNDS.get(player);
        BoundsComponent obstacleBounds = Mappers.BOUNDS.get(obstacle);

        return Intersector.overlaps(playerBounds.bounds,
                obstacleBounds.bounds);
    }
}
