package com.mygdx.game.core;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetDescriptor;
import com.badlogic.gdx.assets.AssetErrorListener;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Pixmap.Format;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureAtlas.AtlasRegion;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.utils.Disposable;
import com.mygdx.game.util.Constants;

public class Assets implements Disposable, AssetErrorListener {
    public static final String TAG = Assets.class.getName();
    public static final Assets instance = new Assets();
    private static final float FONT_SCREEN_WIDTH_FRACTION = 1.0f / 25.f;
    private static final float FONT_SCREEN_WIDTH_FRACTION_FOR_SCORE = 1.0f / 15.0f;

    //    public Texture circle;
//    public Texture square;
//    public Texture money;
    public FileHandle effectFile;
    public FileHandle imagesDir;
    public TextureAtlas ballsAtlas;
    public BitmapFont fontSmall;
    public BitmapFont fontBig;
    public BitmapFont titleFont;
    private AssetManager assetManager;


    public AssetCircle assetCircle;
    public AssetSquare assetSquare;
    public AssetMoenyItem assetMoenyItem;


    private Assets() {
    }

    public void init(AssetManager assetManager) {
        this.assetManager = assetManager;
        assetManager.setErrorListener(this);
        assetManager.load(Constants.TEXTURE_ATLAS_OBJECT, TextureAtlas.class);
//        assetManager.load(Constants.TEXTURE_ATLAS_NUMBER, TextureAtlas.class);
        assetManager.finishLoading();
        Gdx.app.debug(TAG, "# of assets loaded: " + assetManager.getAssetNames() + " - " + assetManager.getAssetNames().size);
        assetManager.getAssetNames();

        for (String a : assetManager.getAssetNames())
            Gdx.app.debug(TAG, "asset: " + a);

        TextureAtlas atlas = assetManager.get(Constants.TEXTURE_ATLAS_OBJECT);

//        TextureAtlas atlasNumber = assetManager.get(Constants.TEXTURE_ATLAS_NUMBER);

        //enable texture filtering for pixel smoothing
//        for (Texture t : atlas.getTextures()) {
//            t.setFilter(TextureFilter.Linear, TextureFilter.Linear);
//        }


        // create game resource object
//        bunny = new AssetBunny(atlas);
//        rock = new AssetRock(atlas);
        assetCircle = new AssetCircle(atlas);
        assetSquare = new AssetSquare(atlas);
        assetMoenyItem = new AssetMoenyItem(atlas);

//        circle = createCircleTexture();
//        square = createSquareTexture();
//        money = createMoneyTexture();


        //File handler
        effectFile = Gdx.files.internal("square5.party");
        imagesDir = Gdx.files.internal("");

        ballsAtlas = new TextureAtlas(Gdx.files.internal("circleeffect.atlas"));

        fontSmall = createFont(FONT_SCREEN_WIDTH_FRACTION);
        fontBig = createFont(FONT_SCREEN_WIDTH_FRACTION_FOR_SCORE);
        titleFont = createFont(1.0f/10.0f);
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
    }

    private Texture createCircleTexture() {
        Pixmap pixmap = new Pixmap(800, 800, Format.RGBA8888);
        pixmap.setColor(Color.WHITE);
        pixmap.fillCircle(pixmap.getWidth() / 2, pixmap.getHeight() / 2, pixmap.getWidth() / 2);
        Texture circle = new Texture(pixmap);
        pixmap.dispose();
        return circle;
    }

    private Texture createSquareTexture() {
        Pixmap pixmap = new Pixmap(2000, 2000, Format.RGBA8888);
        pixmap.setColor(Color.WHITE);
        pixmap.fillRectangle(0, 0, 2000, 2000);
        Texture square = new Texture(pixmap);
        pixmap.dispose();
        return square;
    }

    private Texture createMoneyTexture() {
        Pixmap pixmap = new Pixmap(500, 500, Format.RGBA8888);
        pixmap.setColor(Color.GREEN);
        pixmap.drawCircle(pixmap.getWidth() / 2, pixmap.getHeight() / 2, pixmap.getWidth() / 2);
        pixmap.fillCircle(pixmap.getWidth() / 2, pixmap.getHeight() / 2, pixmap.getWidth() / 2);
        pixmap.setColor(Color.BLACK);
        pixmap.fillCircle(pixmap.getWidth() / 2, pixmap.getHeight() / 2, pixmap.getWidth() * 3 / 8);
        Texture money = new Texture(pixmap);
        pixmap.dispose();
        return money;
    }

    private BitmapFont createFont(float fraction) {
        BitmapFont font;

//        FreeTypeFontGenerator generator = new FreeTypeFontGenerator(Gdx.files.internal("CaviarDreams.ttf"));
        FreeTypeFontGenerator generator = new FreeTypeFontGenerator(Gdx.files.internal("DroidSerif-Bold.ttf"));
        FreeTypeFontGenerator.FreeTypeFontParameter parameter = new FreeTypeFontGenerator.FreeTypeFontParameter();

        parameter.size = (int) (fraction * Gdx.graphics.getWidth());
        parameter.color = Color.WHITE;
        font = generator.generateFont(parameter);
        font.setUseIntegerPositions(false);
        font.getData().setScale(Constants.VIEWPORT_WIDTH / Gdx.graphics.getWidth());

        generator.dispose();
        return font;
    }

    public class AssetCircle {
        public final AtlasRegion circle;

        public AssetCircle(TextureAtlas textureAtlas) {
            this.circle = textureAtlas.findRegion("circle85");
        }
    }

    public class AssetSquare {
        public final AtlasRegion square;

        public AssetSquare(TextureAtlas textureAtlas) {
            this.square = textureAtlas.findRegion("square13");
        }
    }

    public class AssetMoenyItem {
        public final AtlasRegion moneyItem;

        public AssetMoenyItem(TextureAtlas textureAtlas) {
            this.moneyItem = textureAtlas.findRegion("moneyItem1");
        }
    }
}
