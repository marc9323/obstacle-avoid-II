package com.staticvoid.avoid.system;

import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IteratingSystem;
import com.badlogic.gdx.utils.Logger;
import com.staticvoid.avoid.common.Mappers;
import com.staticvoid.avoid.component.MovementComponent;
import com.staticvoid.avoid.component.PositionComponent;

public class MovementSystem extends IteratingSystem {

    private static final Logger log =
            new Logger(MovementSystem.class.getName(),
                    Logger.DEBUG);

    // we need to change position based on Movement (speed)
    private static final Family FAMILY =
            Family.all(
                    PositionComponent.class,
                    MovementComponent.class
            ).get();

    public MovementSystem() {
        super(FAMILY);
    }

    @Override
    protected void processEntity(Entity entity, float deltaTime) {
        // update position based on speed
        PositionComponent position = Mappers.POSITION.get(entity);
        MovementComponent movement = Mappers.MOVEMENT.get(entity);

        // Logic:  change position based on speed
        position.x += movement.xSpeed;
        position.y += movement.ySpeed;

    //    log.debug("processEntity position x = " + position.x + " , " + position.y);


    }
}
