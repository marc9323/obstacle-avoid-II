package com.staticvoid.avoid.component;

import com.badlogic.ashley.core.Component;
import com.badlogic.gdx.utils.Pool;

public class ObstacleComponent implements Component,
        Pool.Poolable {

    public boolean hit;  // defaults to false anyway

    // this method is automatically called when object is
    // returned to Pool
    @Override
    public void reset() {
        hit = false;
    }
}

//  NOTE:  we needn't worry about pooling.
// engine is doing pooling for us and engine automatically
// returns Components and Entities to the Pool when entities
// are removed
