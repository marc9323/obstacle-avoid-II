package com.staticvoid.avoid.component;

import com.badlogic.ashley.core.Component;
import com.badlogic.gdx.utils.Pool;

// use this for obstacles and for player, for player y = 0
public class MovementComponent implements Component,
        Pool.Poolable {

    public float xSpeed; // only for Player
    public float ySpeed; // just for Obstacle

    @Override
    public void reset() { // set defaults
        xSpeed = 0;
        ySpeed = 0;
    }

    // TODO:  NOTE this is discussed in video 144#
    // there is a possibility (older versions?) that
    // a Player will get a MovementComponent from the Pool
    // with ySpeed or an Obstacle will get xSpeed
    // SOLUTION:  Poolable interface
}
