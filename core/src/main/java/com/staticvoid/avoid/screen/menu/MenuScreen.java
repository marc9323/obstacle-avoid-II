package com.staticvoid.avoid.screen.menu;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.Logger;
import com.staticvoid.avoid.ObstacleAvoidGame;
import com.staticvoid.avoid.assets.AssetDescriptors;
import com.staticvoid.avoid.assets.RegionNames;
import com.staticvoid.avoid.screen.game.GameScreen;

public class MenuScreen extends MenuScreenBase {

    private static final Logger log = new Logger(MenuScreen.class.getName(), Logger.DEBUG);

    public MenuScreen(ObstacleAvoidGame game) {
        super(game);
    }

    @Override
    protected Actor createUi() { // returns an Actor, Table
        Table table = new Table();

        TextureAtlas gamePlayAtlas = assetManager.get(AssetDescriptors.GAME_PLAY);
        Skin uiskin = assetManager.get(AssetDescriptors.UI_SKIN);

        TextureRegion backgroundRegion = gamePlayAtlas.findRegion(RegionNames.BACKGROUND);
        table.setBackground(new TextureRegionDrawable(backgroundRegion));

        // play button
        TextButton playButton = new TextButton("PLAY", uiskin);
        playButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                play();
            }
        });

        // high score button
        TextButton highScoreButton = new TextButton("HIGHSCORE", uiskin);
        highScoreButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                showHighScore();
            }
        });

        // options button
        TextButton optionsButton = new TextButton("OPTIONS", uiskin);
        optionsButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                showOptions();
            }
        });

        // quit button
        TextButton quitButton = new TextButton("QUIT", uiskin);
        quitButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                quit();
            }
        });

        // setup table
        Table buttonTable = new Table(uiskin); // must pass skin to Table()
        buttonTable.defaults().pad(20);
        buttonTable.setBackground(RegionNames.PANEL);

        buttonTable.add(playButton).row();
        buttonTable.add(highScoreButton).row();
        buttonTable.add(optionsButton).row();
        buttonTable.add(quitButton);

        buttonTable.center();

        table.add(buttonTable);
        table.center();
        table.setFillParent(true);
        table.pack();

        // stage.addActor(table);
        return table;
    }

    private void quit() {
        log.debug("QUIT GAME");
        Gdx.app.exit();
    }

    private void play() {
        log.debug("play()");
        game.setScreen(new GameScreen(game));
    }

    private void showHighScore() {
        log.debug("showHighScore()");
        game.setScreen(new HighScoreScreen(game));
    }

    private void showOptions() {
        log.debug("showOptions() from MenuScreen");
        game.setScreen(new OptionsScreen(game));
    }

//    private static ImageButton createButton(TextureAtlas atlas, String upRegionName, String downRegionName) {
//        TextureRegion upRegion = atlas.findRegion(upRegionName);
//        TextureRegion downRegion = atlas.findRegion(downRegionName);
//
//        return new ImageButton(
//                new TextureRegionDrawable(upRegion),
//                new TextureRegionDrawable(downRegion)
//        );
//    }
}


/*
NOTE:  Best to have a single instance of Batch for the entire game
 */