package com.customwrld.customlib.util;

import com.google.common.base.Strings;
import org.bukkit.ChatColor;

public class MathUtil {

    public static double square(double val) {
        return val * val;
    }

    public static String getProgressBar(int current, int max, int totalBars, char symbol, ChatColor completedColor, ChatColor notCompletedColor) {
        float percent = (float) current / max;
        int progressBars = (int) (totalBars * percent);

        return Strings.repeat("" + completedColor + symbol, progressBars) + Strings.repeat("" + notCompletedColor + symbol, totalBars - progressBars);
    }

}
