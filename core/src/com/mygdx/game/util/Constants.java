package com.mygdx.game.util;

public interface Constants {
    float VIEWPORT_WIDTH = 7.8f;
    float VIEWPORT_HEIGHT = 10.0f;
    String TEXTURE_ATLAS_OBJECT = "canyonbunny.pack";
    String TEXTURE_ATLAS_NUMBER = "number.pack";

    //Pixcel per meter
    int PPM = 2000;

    short BALL_PHYSIC = 0x01;
    short WORLD_PHYSIC = 0x02;

    int SQUARE_WIDTH = 1;
    int SQUARE_HEIGHT = 1;

    float SPACE_BETWEEN_SQUARE = 0.1f;

    // In main game, this is a ratio for next row to generate multiple value
    int RATIO_TO_DUPLICATE_VALUE = 35;

    // speed of player
    float SPEED = 17.0f;
}
