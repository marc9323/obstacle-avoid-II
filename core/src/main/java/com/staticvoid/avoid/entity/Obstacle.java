package com.staticvoid.avoid.entity;

import com.badlogic.gdx.math.Circle;
import com.badlogic.gdx.math.Intersector;
import com.badlogic.gdx.utils.Pool;
import com.staticvoid.avoid.config.GameConfig;

public class Obstacle extends GameObjectBase implements Pool.Poolable {



    // defaults to medium as per enum
    private float ySpeed = GameConfig.MEDIUM_OBSTACLE_SPEED;
    private boolean hit;

    // no args constructor - which is required for ReflectionPool
    // ( as well as many other reflection heavy Java libraries )
    public Obstacle() {
        super(GameConfig.OBSTACLE_BOUNDS_RADIUS);
        setSize(GameConfig.OBSTACLE_SIZE, GameConfig.OBSTACLE_SIZE);
    }

    public void update() {
        setY(getY() - ySpeed);
    }

    public boolean isPlayerColliding(Player player) {
        Circle playerBounds = player.getBounds();
        // player bounds overlaps obstacle bounds?
        boolean overlaps = Intersector.overlaps(playerBounds, getBounds());
        hit = overlaps; // > elegant than conditional
        return overlaps;
    }

    public boolean isHit() {
        return hit;
    }

    public boolean isNotHit() {
        return !hit;
    }

    public void setYSpeed(float ySpeed) {
        this.ySpeed = ySpeed;
    }

//    public float getWidth() {
//        return SIZE;
//    }
//
//    public float getHeight() {
//        return SIZE;
//    }

    @Override
    public void reset() {
        hit = false;
    }
}
