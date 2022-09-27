package com.staticvoid.avoid.system;

import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IteratingSystem;
import com.staticvoid.avoid.common.Mappers;
import com.staticvoid.avoid.component.CleanUpComponent;
import com.staticvoid.avoid.component.PositionComponent;
import com.staticvoid.avoid.config.GameConfig;

public class CleanUpSystem extends IteratingSystem {

    private static Family FAMILY =
            Family.all(
                    PositionComponent.class,
                    CleanUpComponent.class
            ).get();

    public CleanUpSystem() {
        super(FAMILY);
    }

    @Override
    protected void processEntity(Entity entity, float deltaTime) {
        PositionComponent position = Mappers.POSITION.get(entity);

        // remove entity from engine
        if (position.y < -GameConfig.OBSTACLE_SIZE) {
            // just queues for removal, schedules for removal
            // when entity is returned to Pool so are all
            // components it had
            getEngine().removeEntity(entity);
        }
    }
}
