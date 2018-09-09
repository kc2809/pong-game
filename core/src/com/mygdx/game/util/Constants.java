package com.mygdx.game.util;

import com.badlogic.gdx.graphics.Color;

public interface Constants {
    float VIEWPORT_WIDTH = 7.8f;
    float VIEWPORT_HEIGHT = 10.0f;
    String TEXTURE_ATLAS_OBJECT = "resources827.atlas";

    String HIT_SOUND_PATH = "music/glass1.wav";
    String LEVEL_SOUND_PATH = "music/levelup.wav";

    int POWER_TIMES = 3;

    //Pixcel per meter
    int PPM = 1000;

    short BALL_PHYSIC = 0x01;
    short WORLD_PHYSIC = 0x02;

    int SQUARE_WIDTH = 1;
    int SQUARE_HEIGHT = 1;

    float BALL_WIDTH = 0.4f;
    float BALL_HEIGHT = 0.4f;

    float SPACE_BETWEEN_SQUARE = 0.1f;

    // position relative to screen's height
    float BOTTOM_WALLS_POSITION = 5.0f / 14.0f;

    // In main game, this is a ratio for next row to generate multiple value
    int RATIO_TO_DUPLICATE_VALUE = 50;

    // speed of player
    float SPEED = 15.0f;
//    float SPEED = 200.0f;

    // value of degree will be decrease if refection vector is PERPENDICULAR or between (LOWER_LIMIT, UPPER_LIMIT)
    // with RIGHT WALL.
    float DEGREE_DECREASE = 3;
    float LOWER_LIMIT = 178;
    float UPPER_LIMIT = 184;

    // money to replay
    int MONEY_FOR_REPLAY = 20;


    // ball items
    Color[] colors = {Color.BLUE, Color.LIME , Color.SCARLET, Color.YELLOW, Color.PURPLE
            , Color.GOLD, Color.ORANGE, Color.SKY
            , Color.PINK};

    Color defaultColor = Color.BLUE;
    Color customRed= Color.valueOf("ed1662");
    Color customBlue = Color.valueOf("01b29a");

    Color backgroundColor = Color.valueOf("#202020");

    // Message
    String WARNING_VALUE_MESSAGES = "Money is not enough to buy this power";
}
