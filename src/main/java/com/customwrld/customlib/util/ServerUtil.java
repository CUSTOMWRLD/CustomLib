package com.customwrld.customlib.util;

import com.customwrld.customlib.CustomLib;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

public class ServerUtil {

    public static void hideServer(Player hiding) {
        Bukkit.getOnlinePlayers().forEach(player -> hidePlayer(player, hiding));
    }

    public static void showServer(Player hiding) {
        Bukkit.getOnlinePlayers().forEach(player -> showPlayer(player, hiding));
    }

    public static void hidePlayer(Player hiding, Player from) {
        from.hidePlayer(CustomLib.getCustomLib(), hiding);
    }

    public static void showPlayer(Player hiding, Player to) {
        to.showPlayer(CustomLib.getCustomLib(), hiding);
    }

    public static String formatTPS(double tps) {
        return (tps > 18.0D ? ChatColor.GREEN : (tps > 16.0D ? ChatColor.YELLOW : ChatColor.RED)).toString() + (tps > 20.0D ? "*" : "") + Math.min((double)Math.round(tps * 100.0D) / 100.0D, 20.0D);
    }

}
