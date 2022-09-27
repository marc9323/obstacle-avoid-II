package com.staticvoid.avoid.util.debug;

import com.badlogic.gdx.Application;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Logger;

/*
* use to control camera or to debug in any game
 */
public class DebugCameraController {
    private static final Logger log = new Logger(DebugCameraController.class.getName(), Application.LOG_DEBUG);

    // -- attributes --
    private Vector2 position = new Vector2();
    private Vector2 startPosition = new Vector2();

    // default zoom level
    private float zoom = 1.0f;
    private DebugCameraConfig config;

    public DebugCameraController() {
        config = new DebugCameraConfig();
//        log.info("cameraConfig= " + config);
    }

    // public api
    public void setStartPosition(float x, float y) {
        startPosition.set(x, y);
        position.set(x, y);
    }

    // apply position of controller to the camera
    public void applyTo(OrthographicCamera camera) {
        camera.position.set(position, 0); // zero is for z axis
        camera.zoom = zoom;
        camera.update();
    }

    public void handleDebugInput(float deltaTime) {
        // System.out.println("handleDebugInput");
//        if (Gdx.app.getType() != Application.ApplicationType.Desktop) {
//            return; // don't handle any input on non desktop devices
//        }

        float moveSpeed = config.getMoveSpeed() * deltaTime;
        float zoomSpeed = config.getZoomSpeed() * deltaTime;

        // input polling for move controls
        if (config.isLeftPressed()) {
            moveleft(moveSpeed);
        } else if (config.isRightPressed()) {
            moveRight(moveSpeed);
        } else if (config.isUpPressed()) {
            moveUp(moveSpeed);
        } else if (config.isDownPressed()) {
            moveDown(moveSpeed);
        }

        // zoom controls
        if (config.isZoomInPressed()) {
            zoomIn(zoomSpeed);
        } else if (config.isZoomOutPressed()) {
            zoomOut(zoomSpeed);
        }

        // reset controls
        if (config.isResetPressed()) {
            reset();
        }

        // log controls
        if (config.isLogPressed()) {
            logDebug();
        }
    }

    private void logDebug() {
        log.debug("position= " + position + " , zoom level= " + zoom);
    }

    private void reset() {
        position.set(startPosition);
        setZoom(1.0f);
    }

    private void zoomOut(float zoomSpeed) {
        setZoom(zoom - zoomSpeed);
    }

    private void zoomIn(float zoomSpeed) {
        setZoom(zoom + zoomSpeed);
    }

    // == private methods
    private void setPosition(float x, float y) {
        position.set(x, y);
    }

    private void setZoom(float value) {
        zoom = MathUtils.clamp(value, config.getMaxZoomIn(), config.getMaxZoomOut());
    }

    private void moveCamera(float xSpeed, float ySpeed) {
        setPosition(position.x + xSpeed, position.y + ySpeed);
    }

    private void moveDown(float moveSpeed) {
        moveCamera(0, -moveSpeed);
    }

    private void moveUp(float moveSpeed) {
        moveCamera(0, moveSpeed);
    }

    private void moveRight(float moveSpeed) {
        moveCamera(moveSpeed, 0);
    }

    private void moveleft(float moveSpeed) {
      //  System.out.println("MoveLeftMethod2: " + moveSpeed);
        moveCamera(-moveSpeed, 0);
    }
}
