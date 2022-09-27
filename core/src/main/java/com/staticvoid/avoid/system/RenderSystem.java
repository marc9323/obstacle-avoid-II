package com.staticvoid.avoid.system;

import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.EntitySystem;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.utils.ImmutableArray;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.staticvoid.avoid.common.Mappers;
import com.staticvoid.avoid.component.DimensionComponent;
import com.staticvoid.avoid.component.PositionComponent;
import com.staticvoid.avoid.component.TextureComponent;

// needs Texture, Position, Dimension
public class RenderSystem extends EntitySystem {

    private static final Family FAMILY = Family.all(
            TextureComponent.class,
            PositionComponent.class,
            DimensionComponent.class
    ).get();

    private final Viewport viewport;
    private final SpriteBatch batch;

    private Array<Entity> renderQueue = new Array<Entity>();

    public RenderSystem(Viewport viewport, SpriteBatch batch) {
        this.viewport = viewport;
        this.batch = batch;
    }

    @Override
    public void update(float deltaTime) {
        ImmutableArray<Entity> entities = getEngine().getEntitiesFor(FAMILY);

        // try explicit for loop with any needed casting as per:
        // https://github.com/libgdx/ashley/issues/224
       //  TODO:this!
       for (Entity entity : entities) {
           renderQueue.add(entity);
       }
       // renderQueue.addAll(entities.toArray());

        viewport.apply();
        batch.setProjectionMatrix(viewport.getCamera().combined);
        batch.begin();

        draw();

        batch.end();

        renderQueue.clear();
    }

    private void draw() {
        for (Entity entity : renderQueue) {
            PositionComponent position = Mappers.POSITION.get(entity);
            DimensionComponent dimension = Mappers.DIMENSION.get(entity);
            TextureComponent texture = Mappers.TEXTURE.get(entity);

            batch.draw(texture.region,
                    position.x, position.y,
                    dimension.width, dimension.height);
        }
    }
}
