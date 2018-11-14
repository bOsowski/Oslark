package com.bosowski.oslark.utils;

import java.util.Random;

public class Util {

    public static int randomInt(Random random, int minInclusive, int maxInclusive){
        return random.nextInt((maxInclusive-minInclusive)+1)+minInclusive;
    }
}
