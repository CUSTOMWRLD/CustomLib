package com.customwrld.customlib.util;

import com.customwrld.customlib.CustomLib;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.IOException;

public class BungeeUtil {

    public static void sendPlayer(String player, String server) {
        try (
                ByteArrayOutputStream b = new ByteArrayOutputStream();
                DataOutputStream d = new DataOutputStream(b)
        ){
            d.writeUTF("ConnectOther");
            d.writeUTF(player);
            d.writeUTF(server);
            Bukkit.getServer().sendPluginMessage(CustomLib.getInstance(), "BungeeCord", b.toByteArray());
        } catch (IOException e){
            e.printStackTrace();
        }
    }

    public static void sendPlayer(Player player, String server){
        try (
                ByteArrayOutputStream b = new ByteArrayOutputStream();
                DataOutputStream d = new DataOutputStream(b)
        ){
            d.writeUTF("Connect");
            d.writeUTF(server);
            player.sendPluginMessage(CustomLib.getInstance(), "BungeeCord", b.toByteArray());
        } catch (IOException e){
            e.printStackTrace();
        }
    }
}
