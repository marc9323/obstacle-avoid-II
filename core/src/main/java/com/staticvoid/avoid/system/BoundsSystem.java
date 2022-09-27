package com.staticvoid.avoid.system;

import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IteratingSystem;
import com.badlogic.gdx.utils.Logger;
import com.staticvoid.avoid.common.Mappers;
import com.staticvoid.avoid.component.BoundsComponent;
import com.staticvoid.avoid.component.DimensionComponent;
import com.staticvoid.avoid.component.PositionComponent;

public class BoundsSystem extends IteratingSystem {

    private static final Logger log =
            new Logger(BoundsSystem.class.getName(),
                    Logger.DEBUG);

    private static final Family FAMILY =
            Family.all(
                    BoundsComponent.class,
                    PositionComponent.class,
                    DimensionComponent.class
            ).get();


    public BoundsSystem() {
        super(FAMILY);
    }

    // process method will be called for every entity that matches FAMILY
    @Override
    protected void processEntity(Entity entity, float v) {
        BoundsComponent bounds = Mappers.BOUNDS.get(entity);
        PositionComponent position = Mappers.POSITION.get(entity);
        DimensionComponent dimension = Mappers.DIMENSION.get(entity);

        bounds.bounds.x = position.x + dimension.width / 2f;
        bounds.bounds.y = position.y + dimension.height / 2f;
    }
}
