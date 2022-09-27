package com.staticvoid.avoid.assets;

import com.badlogic.gdx.assets.AssetDescriptor;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;

/*
Describes an asset to be loaded by its filename and type.
 */
public class AssetDescriptors {

    public static final AssetDescriptor<BitmapFont> FONT =
            new AssetDescriptor<BitmapFont>(AssetPaths.UI_FONT, BitmapFont.class);

    public static final AssetDescriptor<TextureAtlas> GAME_PLAY =
            new AssetDescriptor<TextureAtlas>(AssetPaths.GAME_PLAY, TextureAtlas.class);

    public static final AssetDescriptor<TextureAtlas> UI =
            new AssetDescriptor<TextureAtlas>(AssetPaths.UI, TextureAtlas.class);

    public static final AssetDescriptor<Skin> UI_SKIN =
            new AssetDescriptor<Skin>(AssetPaths.UI_SKIN, Skin.class);

    public static final AssetDescriptor<Sound> HIT_SOUND =
            new AssetDescriptor<Sound>(AssetPaths.HIT, Sound.class);
    // superseded by TextureAtlas
//    public static final AssetDescriptor<Texture> BACKGROUND =
//            new AssetDescriptor<Texture>(AssetPaths.BACKGROUND, Texture.class);
//
//    public static final AssetDescriptor<Texture> OBSTACLE =
//            new AssetDescriptor<Texture>(AssetPaths.OBSTACLE, Texture.class);
//
//    public static final AssetDescriptor<Texture> PLAYER =
//            new AssetDescriptor<Texture>(AssetPaths.PLAYER, Texture.class);

    private AssetDescriptors() {
    }


}
