package com.customwrld.customlib.util;

import org.apache.commons.lang.math.Range;

import java.util.Random;

public class RangeUtil {

    public static int randInt(Random rand, Range range) {
        return (int) (range.getMinimumInteger() + ((range.getMaximumInteger() - range.getMinimumInteger()) * rand.nextDouble()));
    }

    public static double randDouble(Random rand, Range range) {
        return range.getMinimumDouble() + ((range.getMaximumDouble() - range.getMinimumDouble()) * rand.nextDouble());
    }

    public static float randFloat(Random rand, Range range) {
        return range.getMinimumFloat() + ((range.getMaximumFloat() - range.getMinimumFloat()) * rand.nextFloat());
    }
}
