package com.staticvoid.avoid.desktop;

import com.badlogic.gdx.tools.texturepacker.TexturePacker;

// used to pack assets
public class AssetPacker {

    private static final boolean DRAW_DEBUG_OUTLINE = false;

    private static final String RAW_ASSETS_PATH = "lwjgl2/assets-raw";
    private static final String ASSETS_PATH = "assets";

    public static void main(String[] args) {
        TexturePacker.Settings settings = new TexturePacker.Settings();
        settings.debug = DRAW_DEBUG_OUTLINE;
//        settings.atlasExtension = ".pack";
//        settings.pot = false;
//        settings.maxWidth = 484; // account for padding
//        settings.maxHeight = 804;

        TexturePacker.process(settings,
                RAW_ASSETS_PATH + "/gameplay",
                ASSETS_PATH + "/gameplay",
                "gameplay"
        );

//        TexturePacker.process(settings,
//                RAW_ASSETS_PATH + "/ui",
//                ASSETS_PATH + "/ui",
//                "ui"
//        ); // extension .atlas by default

        // process assets for the options, menu, highscore
        // screen user interfaces
        TexturePacker.process(
            settings,
                RAW_ASSETS_PATH + "/skin",
                ASSETS_PATH + "/ui",
                "uiSkin"
        );
    }
    // TODO: remember for production to remove all unnecessary assets
    // (i.e. stuff that was packed into atlas format)
}
