package com.staticvoid.avoid.screen.menu;

import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.ButtonGroup;
import com.badlogic.gdx.scenes.scene2d.ui.CheckBox;
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
import com.staticvoid.avoid.config.DifficultyLevel;

public class OptionsScreen extends MenuScreenBase {
    private static final Logger log =
            new Logger(OptionsScreen.class.getName(), Logger.DEBUG);

    // manages a group of buttons to enforce minimum/maximum num checkboxes
    // enables radio button functionality
    private ButtonGroup<CheckBox> checkBoxGroup;
    private CheckBox easy;
    private CheckBox medium;
    private CheckBox hard;

    public OptionsScreen(ObstacleAvoidGame game) {
        super(game);
    }

    @Override
    protected Actor createUi() {
        // position buttons manually inside a table
        Table table = new Table();
        table.defaults().pad(15);

        TextureAtlas gamePlayAtlas =
                assetManager.get(AssetDescriptors.GAME_PLAY);
        Skin uiSkin = assetManager.get(AssetDescriptors.UI_SKIN);

        TextureRegion backgroundRegion = gamePlayAtlas.findRegion(RegionNames.BACKGROUND);
        table.setBackground(new TextureRegionDrawable(backgroundRegion));

        // == label ==
        Label label = new Label("DIFFICULTY", uiSkin);

        // whatever is possible with Tables can be accomplished with Checkboxes
       // easy = new CheckBox(DifficultyLevel.EASY.name(), uiSkin);
        easy = checkBox(DifficultyLevel.EASY.name(), uiSkin);
       // easy.setDebug(true); // first cell typically image cell and label cell
        // table color == blue, cells == red, actors == green

        medium = checkBox(DifficultyLevel.MEDIUM.name(), uiSkin);
        hard =  checkBox(DifficultyLevel.HARD.name(), uiSkin);

        checkBoxGroup = new ButtonGroup<CheckBox>(easy, medium, hard);

        DifficultyLevel difficultyLevel = GameManager.INSTANCE.getDifficultyLevel();

        checkBoxGroup.setChecked(difficultyLevel.name());

        TextButton backButton = new TextButton("BACK", uiSkin);
        backButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                back();
            }
        });

        ChangeListener listener = new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                difficultyChanged();
            }
        };

        easy.addListener(listener);
        medium.addListener(listener);
        hard.addListener(listener);

        // == set up table
        Table contentTable = new Table(uiSkin);
        contentTable.defaults().pad(10);
        contentTable.setBackground(RegionNames.PANEL);

        contentTable.add(label).row();
        contentTable.add(easy).row();
        contentTable.add(medium).row();
        contentTable.add(hard).row();
        contentTable.add(backButton);

        table.add(contentTable);
        table.center();
        table.setFillParent(true); // fill screen

        return table;
    }

    private static CheckBox checkBox(String text, Skin skin) { // factory function
        CheckBox checkBox = new CheckBox(text, skin);
        checkBox.left().pad(8); // align left, world units
        checkBox.getLabelCell().pad(8);
        return checkBox;
    }

    private void difficultyChanged() {
        log.debug("Difficulty Changed");
        CheckBox checked = checkBoxGroup.getChecked();

        if (checked == easy) {
            log.debug("easy");
            GameManager.INSTANCE.updateDifficulty(DifficultyLevel.EASY);
        } else if (checked == medium) {
            log.debug("medium");
            GameManager.INSTANCE.updateDifficulty(DifficultyLevel.MEDIUM);
        } else if (checked == hard) {
            log.debug("hard");
            GameManager.INSTANCE.updateDifficulty(DifficultyLevel.HARD);
        }
    }

    private void back() {
        log.debug("back() to MenuScreen from OptionsScreen");
        game.setScreen(new MenuScreen(game));
    }

//    private static ImageButton createButton(TextureAtlas atlas, String regionName) {
//        TextureRegion region = atlas.findRegion(regionName);
//
//        return new ImageButton(new TextureRegionDrawable(region));
//    }
}
