package com.staticvoid.avoid.system.debug;

import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IteratingSystem;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.utils.Logger;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.staticvoid.avoid.common.Mappers;
import com.staticvoid.avoid.component.BoundsComponent;

// renders the debugging graphics
// iterates over each entity and calls processEntity()
// iterate over family of entities
public class DebugRenderSystem extends IteratingSystem {

    private static final Logger log =
            new Logger(DebugRenderSystem.class.getName(),
                    Logger.DEBUG);

    // we are only interested  in entities that have BoundsComponent
    private static final Family FAMILY =
            Family.all(BoundsComponent.class).get();

    // NOTE:  we get a camera with viewport.getCamera()
    private final Viewport viewport;
    private final ShapeRenderer renderer;

    // no need to pass Family into constructor - we declared it above
    //  just pass to super()
    public DebugRenderSystem(Viewport viewport, ShapeRenderer renderer) {
        super(FAMILY);
        this.viewport = viewport;
        this.renderer = renderer;
    }

    @Override
    public void update(float deltaTime) {
//        ImmutableArray<Entity> entities = getEngine().getEntitiesFor(FAMILY);
//        for(Entity entity : entities){
//            processEntity(entity, deltaTime);
//        }
        // will loop through all entities that have BoundsComponent
        // and for each entity call processEntity

      //  log.debug("DebugRenderSystem - update ()");

        Color oldColor = renderer.getColor().cpy();
        viewport.apply();
        renderer.setProjectionMatrix(viewport.getCamera().combined);

        renderer.begin(ShapeRenderer.ShapeType.Line);
        renderer.setColor(Color.RED);
        // loops through all in family calling processEntity()
        super.update(deltaTime); // **loops through entities - called processEntity
        // super above is equivalent to commented code below:
//        ImmutableArray<Entity> entities = getEngine().getEntitiesFor(FAMILY)
//        for(Entity entity : entities) {
//            processEntity(entity, deltaTime);
//        }
        renderer.end();
        renderer.setColor(oldColor);

    }

    // called as many times as there are entities that contain BoundsComponent
    @Override
    protected void processEntity(Entity entity, float deltaTime) {
      //  log.debug("processEntity = " + entity);

        // get the bounds component belonging to specified entity
        BoundsComponent bc = Mappers.BOUNDS.get(entity);
        renderer.circle(bc.bounds.x, bc.bounds.y, bc.bounds.radius, 30);


        // NOTE: call begin and end in update not HERE
    }
}
