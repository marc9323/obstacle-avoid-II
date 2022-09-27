package com.staticvoid.avoid.screen.menu;

import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.Logger;
import com.staticvoid.avoid.ObstacleAvoidGame;
import com.staticvoid.avoid.assets.AssetDescriptors;
import com.staticvoid.avoid.assets.RegionNames;
import com.staticvoid.avoid.common.GameManager;

public class HighScoreScreen extends MenuScreenBase {

    private static final Logger log =
            new Logger(HighScoreScreen.class.getName(), Logger.DEBUG);


    public HighScoreScreen(ObstacleAvoidGame game) {
        super(game);
    }

    @Override
    protected Actor createUi() {
        // create ui with tables
        Table table = new Table();

        // get atlases
        TextureAtlas gamePlayAtlas =
                assetManager.get(AssetDescriptors.GAME_PLAY);

        Skin uiskin = assetManager.get(AssetDescriptors.UI_SKIN);

        // regions for background and back button
        TextureRegion backgroundRegion =
                gamePlayAtlas.findRegion(RegionNames.BACKGROUND);

        // background
        table.setBackground(new TextureRegionDrawable(backgroundRegion));

        // high score text
        Label highScoreText = new Label("HIGHSCORE", uiskin);
        // high score label
        String highScoreString = GameManager.INSTANCE.getHighScoreString();
        Label highScoreLabel = new Label(highScoreString, uiskin);

        // back button
        TextButton backButton = new TextButton("BACK", uiskin);
        backButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                back();
            }
        });

        // setup tables
        Table contentTable = new Table(uiskin); // must pass skin
        contentTable.defaults().pad(20);
        contentTable.setBackground(RegionNames.PANEL);
        contentTable.add(highScoreText).row();
        contentTable.add(highScoreLabel).row();
        contentTable.add(backButton);

        contentTable.center();

        table.add(contentTable);
        table.center();
        table.setFillParent(true);

        table.pack();

        // superclass adds table returned from createUi to stage
        return table;
    }

    private void back() {
        // highscore screen back to menu screen
        log.debug("back()");
        game.setScreen(new MenuScreen(game));
    }

//    @Override
//    public void render(float delta) {
//        GdxUtils.clearScreen();
//
//        stage.act();
//        stage.draw();
//    }
//
//    @Override
//    public void resize(int width, int height) {
//        viewport.update(width, height, true);
//    }
//
//    @Override
//    public void hide() {
//        dispose();
//    }
//
//    @Override
//    public void dispose() {
//        stage.dispose();
//    }

}
