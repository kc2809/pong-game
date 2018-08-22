package com.mygdx.game.core;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetDescriptor;
import com.badlogic.gdx.assets.AssetErrorListener;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureAtlas.AtlasRegion;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.utils.Disposable;
import com.mygdx.game.util.Constants;

import java.util.HashMap;
import java.util.Map;

import static com.badlogic.gdx.graphics.Texture.TextureFilter.Nearest;

public class Assets implements Disposable, AssetErrorListener {

    public static String BALL = "ball";
    public static String SQUARE = "square";
    public static String MONEY_ITEM = "money_item";
    public static String PLAY_ICON = "play_btn";
    public static String VOLUMNE_ACTIVE_ICON = "sound_active";
    public static String VOLUMNE_INACTIVE_ICON = "sound_inactive";
    public static String STORE_ICON = "store";
    public static String STAR_ICON = "star";
    public static String PAUSE_ICON = "pause_btn";
    public static String EXIT_ICON = "exit_btn";
    public static String MENU_ICON = "menu";
    public static String REPLAY_ICON = "xoay";
    public static String BACK_ICON = "back";
    public static String ARROW_ICON = "white_arrow";
    public static String BOX = "textbox";

    public static final String TAG = Assets.class.getName();
    public static final Assets instance = new Assets();
    private static final float SMALL_FONT_SCREEN_WIDTH_FRACTION = 1.0f / 25.f;
    private static final float BIG_FONT_SCREEN_WIDTH_FRACTION_FOR_SCORE = 1.0f / 10.0f;
    private static final float MEDIUM_FONT_SCREEN_WIDTH_FRACTION_FOR_SCORE = 1.0f / 15.0f;
    private static final float TITLE_FONT_SCREEN_WIDTH_FRACTION_FOR_SCORE = 1.0f / 8.0f;
    private static final float BIG_BIG_FONT = 1.0f / 4.0f;

    public FileHandle effectFile;
    public FileHandle imagesDir;
    public TextureAtlas ballsAtlas;
    public BitmapFont fontSmall;
    public BitmapFont fontBig;
    public BitmapFont fontMedium;
    public BitmapFont titleFont;
    public BitmapFont bigbigFont;
    private AssetManager assetManager;

    public Map<String, AtlasRegion> resources;

    private Assets() {
        resources = new HashMap<>();
    }

    public void init(AssetManager assetManager) {
        this.assetManager = assetManager;
        assetManager.setErrorListener(this);
        assetManager.load(Constants.TEXTURE_ATLAS_OBJECT, TextureAtlas.class);
        assetManager.finishLoading();
        Gdx.app.debug(TAG, "# of assets loaded: " + assetManager.getAssetNames() + " - " + assetManager.getAssetNames().size);
        assetManager.getAssetNames();

        for (String a : assetManager.getAssetNames())
            Gdx.app.debug(TAG, "asset: " + a);

        TextureAtlas atlas = assetManager.get(Constants.TEXTURE_ATLAS_OBJECT);


        for (Texture t : atlas.getTextures()) {
            t.setFilter(Nearest, Nearest);
//            t.setFilter(Linear, Linear);
        }

        initAssets(atlas);
        //File handler
        effectFile = Gdx.files.internal("square5.party");
        imagesDir = Gdx.files.internal("");

        ballsAtlas = new TextureAtlas(Gdx.files.internal("circleeffect.atlas"));

        fontSmall = createFont(SMALL_FONT_SCREEN_WIDTH_FRACTION);
        fontBig = createFont(BIG_FONT_SCREEN_WIDTH_FRACTION_FOR_SCORE);
        fontMedium = createFont(MEDIUM_FONT_SCREEN_WIDTH_FRACTION_FOR_SCORE);
        titleFont = createFont(TITLE_FONT_SCREEN_WIDTH_FRACTION_FOR_SCORE);
        bigbigFont = createFont(BIG_BIG_FONT);
    }

    //Add resources to map
    private void initAssets(TextureAtlas atlas) {
        putAtlasRegionToMap(atlas, BALL);
        putAtlasRegionToMap(atlas, SQUARE);
        putAtlasRegionToMap(atlas, MONEY_ITEM);
        putAtlasRegionToMap(atlas, PLAY_ICON);
        putAtlasRegionToMap(atlas, VOLUMNE_ACTIVE_ICON);
        putAtlasRegionToMap(atlas, VOLUMNE_INACTIVE_ICON);
        putAtlasRegionToMap(atlas, STAR_ICON);
        putAtlasRegionToMap(atlas, STORE_ICON);
        putAtlasRegionToMap(atlas, PAUSE_ICON);
        putAtlasRegionToMap(atlas, EXIT_ICON);
        putAtlasRegionToMap(atlas, REPLAY_ICON);
        putAtlasRegionToMap(atlas, MENU_ICON);
        putAtlasRegionToMap(atlas, BACK_ICON);
        putAtlasRegionToMap(atlas, ARROW_ICON);
        putAtlasRegionToMap(atlas, BOX);
    }

    @Override
    public void error(AssetDescriptor asset, Throwable throwable) {
        Gdx.app.error(TAG, "Couldn't load asset '"
                + asset.fileName + "'", (Exception) throwable);
    }

    @Override
    public void dispose() {
        assetManager.dispose();
        fontSmall.dispose();
        fontBig.dispose();
        fontMedium.dispose();
        titleFont.dispose();
    }

    private BitmapFont createFont(float fraction) {
        BitmapFont font;

//        FreeTypeFontGenerator generator = new FreeTypeFontGenerator(Gdx.files.internal("DroidSerif-Bold.ttf"));
        FreeTypeFontGenerator generator = new FreeTypeFontGenerator(Gdx.files.internal("dancing.ttf"));
        FreeTypeFontGenerator.FreeTypeFontParameter parameter = new FreeTypeFontGenerator.FreeTypeFontParameter();

        parameter.size = (int) (fraction * Gdx.graphics.getWidth());
        parameter.color = Color.WHITE;
        font = generator.generateFont(parameter);
        font.setUseIntegerPositions(false);
        font.getData().setScale(Constants.VIEWPORT_WIDTH / Gdx.graphics.getWidth());

        generator.dispose();
        return font;
    }


    private void putAtlasRegionToMap(TextureAtlas atlas, String regionName) {
        resources.put(regionName, atlas.findRegion(regionName));
    }

    public AtlasRegion getAsset(String name) {
        return resources.get(name);
    }
}
