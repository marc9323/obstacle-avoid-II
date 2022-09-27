package com.staticvoid.avoid.entity;

import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Circle;

public abstract class GameObjectBase {

    private float x;
    private float y;

    private float width = 1;
    private float height = 1;

    private Circle bounds;

    public GameObjectBase(float boundsRadius) {
        this.bounds = new Circle(x, y, boundsRadius);
    }

    public void drawDebug(ShapeRenderer renderer) {
        renderer.x(bounds.x, bounds.y, 0.1f);
        renderer.circle(bounds.x, bounds.y, bounds.radius, 30);
    }

    // takes world units
    public void setPosition(float x, float y) {
        this.x = x;
        this.y = y;

        // keep bounding circle in sync
        updateBounds();
    }

    public float getX() {
        return x;
    }

    public float getY() {
        return y;
    }

    // whenever we setx or x or position we MUST
    // not forget to call updateBounds()
    public void setX(float x) {
        this.x = x;
        updateBounds();
    }

    public void setY(float y) {
        this.y = y;
        updateBounds();
    }

    public void setSize(float width, float height) {
        this.width = width;
        this.height = height;
        updateBounds();
    }

    public float getWidth() {
        return width;
    }

    public float getHeight() {
        return height;
    }

    public void updateBounds() {
        // remember we are shifting right and up to account
        // for the mismatched circle and texture origin points
        float halfWidth = width / 2f;
        float halfHeight = height / 2f;

        bounds.setPosition(x + halfWidth, y + halfHeight);
    }

    public Circle getBounds() {
        return bounds;
    }

}
