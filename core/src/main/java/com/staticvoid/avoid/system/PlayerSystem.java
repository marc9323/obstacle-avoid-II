package com.staticvoid.avoid.system;

import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IteratingSystem;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.utils.Logger;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.staticvoid.avoid.common.Mappers;
import com.staticvoid.avoid.component.BoundsComponent;
import com.staticvoid.avoid.component.MovementComponent;
import com.staticvoid.avoid.component.PlayerComponent;
import com.staticvoid.avoid.config.GameConfig;

public class PlayerSystem extends IteratingSystem {

    private static final Logger log =
            new Logger(PlayerSystem.class.getName(), Logger.DEBUG);

    private static final Family FAMILY = Family.all(
            PlayerComponent.class,
            MovementComponent.class
          //  BoundsComponent.class
    ).get();

    public PlayerSystem() {
        super(FAMILY);
    }

    @Override
    protected void processEntity(Entity entity, float deltaTime) {
        // get the movement component for this entity -
        // in this case, just Player as there will be just a
        // single entity tagged with interface: PlayerComponent
        MovementComponent movement = Mappers.MOVEMENT.get(entity);

        movement.xSpeed = 0;

        if(Gdx.input.isKeyPressed(Input.Keys.RIGHT)) {
            movement.xSpeed = GameConfig.MAX_PLAYER_X_SPEED;
        } else if(Gdx.input.isKeyPressed(Input.Keys.LEFT)) {
            movement.xSpeed = -GameConfig.MAX_PLAYER_X_SPEED;
        }

        // TODO:  OPTIONAL CODE FOR VERTICAL MOVEMENT, UP DOWN KEYS
        // test code -->
        movement.ySpeed = 0;
        if(Gdx.input.isKeyPressed(Input.Keys.UP)) {
            movement.ySpeed = GameConfig.MAX_PLAYER_X_SPEED;
        } else if(Gdx.input.isKeyPressed(Input.Keys.DOWN)) {
            movement.ySpeed = -GameConfig.MAX_PLAYER_X_SPEED;
        }
        // TODO:  END OPTIONAL CODE FOR VERTICAL MOVEMENT

//        BoundsComponent bounds = Mappers.BOUNDS.get(entity);
//
//        log.debug("processEntity() xSpeed --> " + movement.xSpeed);
//        log.debug("processEntity() bounds" + bounds.bounds);
    }
}
