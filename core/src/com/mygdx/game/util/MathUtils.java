package com.mygdx.game.util;

import java.util.Random;

public class MathUtils {

    public static MathUtils instance = new MathUtils();
    private static Random random;

    private MathUtils() {
        random = new Random();
    }

    public boolean ratio(int ratio) {
        return random.nextInt(100) < ratio;
    }
}
