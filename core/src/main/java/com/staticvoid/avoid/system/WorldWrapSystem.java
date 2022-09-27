package com.staticvoid.avoid.system;

import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IteratingSystem;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.staticvoid.avoid.common.Mappers;
import com.staticvoid.avoid.component.DimensionComponent;
import com.staticvoid.avoid.component.PositionComponent;
import com.staticvoid.avoid.component.WorldWrapComponent;

// if an entity is tagged with WorldWrap it cannot
// leave world bounds
public class WorldWrapSystem extends IteratingSystem {

    private static final Family FAMILY = Family.all(
            WorldWrapComponent.class,
            PositionComponent.class,
            DimensionComponent.class
    ).get();

    private final Viewport viewport;

    public WorldWrapSystem(Viewport viewport) {
        super(FAMILY);
        this.viewport = viewport;
    }

    @Override
    protected void processEntity(Entity entity, float deltaTime) {
        PositionComponent position = Mappers.POSITION.get(entity);
        DimensionComponent dimension = Mappers.DIMENSION.get(entity);

        position.x = MathUtils.clamp(position.x, 0, viewport.getWorldWidth() - dimension.width);
        position.y = MathUtils.clamp(position.y, 0, viewport.getWorldHeight() - dimension.height);
    }
}

