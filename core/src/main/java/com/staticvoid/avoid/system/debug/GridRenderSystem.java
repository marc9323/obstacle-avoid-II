package com.staticvoid.avoid.system.debug;

import com.badlogic.ashley.core.EntitySystem;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.utils.Logger;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.staticvoid.avoid.util.ViewportUtils;

public class GridRenderSystem extends EntitySystem {

    // == constants ==
    private static final Logger log =
            new Logger(GridRenderSystem.class.getName(),
                    Logger.DEBUG);

    // == attributes
    private final Viewport viewport;
    private final ShapeRenderer renderer;

    public GridRenderSystem(Viewport viewport, ShapeRenderer renderer) {
        // set priority, the higher priority, the later it kicks in
        // super()
        this.viewport = viewport;
        this.renderer = renderer;
    }

    // == update ==

    @Override
    public void update(float deltaTime) {
    //    log.debug("GridRenderSystem: update()");
        viewport.apply();
        ViewportUtils.drawGrid(viewport, renderer);
    }
}
/*
Systems not touching entities or doing anything with entity data
are called 'Void Systems' or 'Utility Systems'
may be re-used in any game
 */