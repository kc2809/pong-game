package com.mygdx.game.object;

import com.badlogic.gdx.graphics.Color;

import java.util.Random;

public class MaterialColor {
    public static Color[] water = {Color.ROYAL, Color.BLUE, Color.CYAN, Color.SKY};
    public static Color[] fire = {Color.FIREBRICK, Color.RED, Color.SCARLET, Color.CORAL};
    public static Color[] tree = {Color.GREEN, Color.CHARTREUSE, Color.LIME, Color.FOREST};
    public static Color[] ground = {Color.BROWN, Color.PURPLE, Color.VIOLET, Color.MAROON, Color.MAGENTA};
    public static Color[] gold = {Color.YELLOW, Color.GOLD, Color.GOLDENROD, Color.ORANGE};
    public static Random r = new Random();


    public static Color[][] materialColors = {
            {Color.ROYAL, Color.BLUE, Color.CYAN, Color.SKY},
            {Color.FIREBRICK, Color.RED, Color.SCARLET, Color.CORAL},
            {Color.GREEN, Color.CHARTREUSE, Color.LIME, Color.FOREST},
            {Color.BROWN, Color.PURPLE, Color.VIOLET, Color.MAROON, Color.MAGENTA},
            {Color.YELLOW, Color.GOLD, Color.GOLDENROD, Color.ORANGE}
    };

    /*
    0: water
    1: fire
    2: tree
    3: ground
    4: gold
     */

    public static Color getMaterialColors(int type) {
        return materialColors[type][r.nextInt(materialColors[type].length)];
    }
}
