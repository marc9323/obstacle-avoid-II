package com.staticvoid.avoid.common;

import com.badlogic.ashley.core.ComponentMapper;
import com.staticvoid.avoid.component.BoundsComponent;
import com.staticvoid.avoid.component.DimensionComponent;
import com.staticvoid.avoid.component.MovementComponent;
import com.staticvoid.avoid.component.ObstacleComponent;
import com.staticvoid.avoid.component.PositionComponent;
import com.staticvoid.avoid.component.TextureComponent;

// similat to AssetDescriptors
// ComponentMapper instances can be shared anywhere
public class Mappers {

    public static final ComponentMapper<BoundsComponent> BOUNDS =
            ComponentMapper.getFor(BoundsComponent.class);

    public static final ComponentMapper<MovementComponent> MOVEMENT =
            ComponentMapper.getFor(MovementComponent.class);

    public static final ComponentMapper<PositionComponent> POSITION =
            ComponentMapper.getFor(PositionComponent.class);

    public static final ComponentMapper<ObstacleComponent> OBSTACLE =
            ComponentMapper.getFor(ObstacleComponent.class);

    public static final ComponentMapper<DimensionComponent> DIMENSION =
            ComponentMapper.getFor(DimensionComponent.class);

    public static final ComponentMapper<TextureComponent> TEXTURE =
            ComponentMapper.getFor(TextureComponent.class);

    // no need for a PlayComponent Mapper - PlayerComponent has no data
    // remember, it is just a marker interface

    private Mappers() {
        // nothing here ...
    }
}
