package com.staticvoid.avoid.util;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.utils.Logger;
import com.badlogic.gdx.utils.viewport.Viewport;

public class ViewportUtils {

    private static final Logger log = new Logger(ViewportUtils.class.getName(), Logger.DEBUG);

    private static final int DEFAULT_CELL_SIZE = 1;

    private ViewportUtils() {
        // private constructor
    }

    public static void drawGrid(Viewport viewport, ShapeRenderer renderer) {
        drawGrid(viewport, renderer, DEFAULT_CELL_SIZE);
    }

    // cell size by default one world unit
    public static void drawGrid(Viewport viewport, ShapeRenderer renderer, int cellSize) {
        // validate params
        if(viewport == null) {
            throw new IllegalArgumentException("Viewport param required");
        }

        if(renderer == null) {
            throw new IllegalArgumentException("Renderer param required");
        }

        if(cellSize < DEFAULT_CELL_SIZE) {
            cellSize = DEFAULT_CELL_SIZE;
        }

        // copy old color from renderer
        Color oldColor = new Color(renderer.getColor());

        int worldWidth = (int)viewport.getWorldWidth();
        int worldHeight = (int)viewport.getWorldHeight();
        int doubleWorldWidth = worldWidth * 2;
        int doubleWorldHeight = worldHeight * 2;

        renderer.setProjectionMatrix(viewport.getCamera().combined);
        renderer.begin(ShapeRenderer.ShapeType.Line);
        renderer.setColor(Color.WHITE);

        // draw vertical lines
        for(int x = -doubleWorldWidth; x < doubleWorldWidth; x += cellSize) {
            renderer.line(x, -doubleWorldHeight, x, doubleWorldHeight);
        }

        // draw horizontal lines
        for(int y = -doubleWorldHeight; y < doubleWorldHeight; y += cellSize) {
            renderer.line(-doubleWorldWidth, y, doubleWorldHeight, y);
        }

        // render x and y axis lines, red
        renderer.setColor(Color.RED);
        renderer.line(0, -doubleWorldHeight, 0, doubleWorldHeight);
        renderer.line(-doubleWorldWidth, 0, doubleWorldWidth, 0);

        // world bounds -- visible on zoom out
        renderer.setColor(Color.GREEN);
        renderer.line(0, worldHeight, worldWidth, worldHeight);
        renderer.line(worldWidth, 0, worldWidth, worldHeight);
        
        renderer.end();

        renderer.setColor(oldColor);

    }

    public static void debugPixelPerUnit(Viewport viewport) {
        if(viewport == null) {
            throw new IllegalArgumentException("Viewport param required");
        }

        float screenWidth = viewport.getScreenWidth();
        float screenHeight = viewport.getScreenHeight();

        float worldWidth = viewport.getWorldWidth();
        float worldHeight = viewport.getWorldHeight();

        // pixels per world unit
        float xPPU = screenWidth / worldWidth;
        float yPPU = screenHeight / worldHeight;

//        log.debug("=========================");
//        log.debug("screenWidth: " + screenWidth);
//        log.debug("screenHeight: " + screenHeight);
//        log.debug("worldWidth: " + worldWidth);
//        log.debug("worldHeight: " + worldHeight);
//
//        log.debug("===========================");
//        log.debug("xPPU: " + xPPU + " , yPPU: " + yPPU);
    }
}
