package com.mygdx.game.util;

public interface Constants {
    float VIEWPORT_WIDTH = 7.8f;
    float VIEWPORT_HEIGHT = 10.0f;
    String TEXTURE_ATLAS_OBJECT = "resources730";

    String HIT_SOUND_PATH = "music/glass1.wav";
    String LEVEL_SOUND_PATH = "music/levelup.wav";

    //Pixcel per meter
    int PPM = 1000;

    short BALL_PHYSIC = 0x01;
    short WORLD_PHYSIC = 0x02;

    int SQUARE_WIDTH = 1;
    int SQUARE_HEIGHT = 1;

    float BALL_WIDTH = 0.4f;
    float BALL_HEIGHT = 0.4f;

    float SPACE_BETWEEN_SQUARE = 0.1f;

    // In main game, this is a ratio for next row to generate multiple value
    int RATIO_TO_DUPLICATE_VALUE = 50;

    // speed of player
    float SPEED = 17.0f;
//    float SPEED = 200.0f;

    // value of degree will be decrease if refection vector is PERPENDICULAR or between (LOWER_LIMIT, UPPER_LIMIT)
    // with RIGHT WALL.
    float DEGREE_DECREASE = 5;
    float LOWER_LIMIT = 178;
    float UPPER_LIMIT = 190;
}
