package com.customwrld.customlib.util;

import com.google.common.base.Strings;
import org.bukkit.ChatColor;

public class MathUtil {

    public static boolean isIntegral(double x) { return x % 1 == 0; }
    public static boolean isIntegral(float x) { return x % 1 == 0; }

    public static int min(int... values) {
        int x = Integer.MAX_VALUE;
        for(int value : values) { if(value < x) { x = value; } }
        return x;
    }

    public static double min(double... values) {
        double x = Double.MAX_VALUE;
        for(double value : values) { if(value < x) { x = value; } }
        return x;
    }

    public static int max(int... values) {
        int x = Integer.MIN_VALUE;
        for(int value : values) { if(value > x) { x = value; } }
        return x;
    }

    public static double max(double... values) {
        double x = Double.MIN_VALUE;
        for(double value : values) { if(value > x) { x = value; } }
        return x;
    }

    public static int gcd(int... values) {
        if(values.length < 2) {
            if(values.length == 1) { return values[0]; } else { return 0; }
        }

        int gcd = values[0];
        for(int i = 1; i < values.length; i++){
            int other = values[i];

            if (gcd == 0 || other == 0) {
                gcd = gcd + other;
            } else {
                int biggerVal = Math.max(Math.abs(gcd), Math.abs(other));
                int smallerVal = Math.min(Math.abs(gcd), Math.abs(other));

                gcd = gcd(biggerVal % smallerVal, smallerVal);
            }
        }

        return gcd;
    }

    public static int lcm(int... values) {
        if(values.length < 2) { return 1; }

        int gcd = gcd(values);

        int lcm = values[0];
        for(int i = 1; i < values.length; i++) {
            int other = values[i];

            if (lcm == 0 || other == 0) {
                return 0;
            } else {
                lcm = Math.abs(lcm * other) / gcd;
            }
        }

        return lcm;
    }

    public static double square(double val) {
        return val * val;
    }

    public static String getProgressBar(int current, int max, int totalBars, char symbol, ChatColor completedColor, ChatColor notCompletedColor) {
        float percent = (float) current / max;
        int progressBars = (int) (totalBars * percent);

        return Strings.repeat("" + completedColor + symbol, progressBars) + Strings.repeat("" + notCompletedColor + symbol, totalBars - progressBars);
    }

}
