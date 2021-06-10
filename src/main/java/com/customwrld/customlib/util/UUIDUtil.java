package com.customwrld.customlib.util;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import lombok.Getter;
import lombok.Setter;
import org.bukkit.Bukkit;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class UUIDUtil {

    static ExecutorService executorService = Executors.newFixedThreadPool(1);
    private static final JsonParser parser = new JsonParser();
    private static final String USER_AGENT = "Mozilla/5.0";

    public static CompletableFuture<UUIDPair> getUUID(String player) {
        UUIDPair uuidPair = new UUIDPair();

        if (Bukkit.getPlayer(player) != null) {
            uuidPair.setName(Bukkit.getPlayer(player).getName());
            uuidPair.setUuid(Bukkit.getPlayer(player).getUniqueId());
            return CompletableFuture.completedFuture(uuidPair);
        }

        if (Bukkit.getOfflinePlayer(player) != null && Bukkit.getOfflinePlayer(player).hasPlayedBefore()) {
            uuidPair.setName(Bukkit.getOfflinePlayer(player).getName());
            uuidPair.setUuid(Bukkit.getOfflinePlayer(player).getUniqueId());
            return CompletableFuture.completedFuture(uuidPair);
        }

        return CompletableFuture.supplyAsync(() -> {
            try {
                String url = "https://api.ashcon.app/mojang/v2/user/" + player;
                URL obj = new URL(url);
                HttpURLConnection con = (HttpURLConnection) obj.openConnection();
                con.setRequestProperty("User-Agent", USER_AGENT);

                BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
                String inputLine;
                StringBuffer response = new StringBuffer();
                while ((inputLine = in.readLine()) != null) {
                    response.append(inputLine);
                }
                in.close();
                JsonElement object = parser.parse(response.toString());
                JsonObject parsedObject = object.getAsJsonObject();
                uuidPair.setUuid(UUID.fromString(parsedObject.get("uuid").getAsString()));
                uuidPair.setName(parsedObject.get("username").getAsString());
                return uuidPair;
            } catch (Exception e) {
                return null;
            }
        }, executorService);
    }

    public static CompletableFuture<UUIDPair> getName(UUID uuid) {
        UUIDPair uuidPair = new UUIDPair();

        if (Bukkit.getPlayer(uuid) != null) {
            uuidPair.setName(Bukkit.getPlayer(uuid).getName());
            uuidPair.setUuid(uuid);
            return CompletableFuture.completedFuture(uuidPair);
        }

        if (Bukkit.getOfflinePlayer(uuid) != null && Bukkit.getOfflinePlayer(uuid).hasPlayedBefore()) {
            uuidPair.setName(Bukkit.getOfflinePlayer(uuid).getName());
            uuidPair.setUuid(uuid);
            return CompletableFuture.completedFuture(uuidPair);
        }

        return CompletableFuture.supplyAsync(() -> {
            try {
                String url = "https://api.ashcon.app/mojang/v2/user/" + uuid;
                URL obj = new URL(url);
                HttpURLConnection con = (HttpURLConnection) obj.openConnection();
                con.setRequestProperty("User-Agent", USER_AGENT);

                BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
                String inputLine;
                StringBuffer response = new StringBuffer();
                while ((inputLine = in.readLine()) != null) {
                    response.append(inputLine);
                }
                in.close();
                JsonElement object = parser.parse(response.toString());
                JsonObject parsedObject = object.getAsJsonObject();
                uuidPair.setUuid(uuid);
                uuidPair.setName(parsedObject.get("username").getAsString());
                return uuidPair;
            } catch (Exception e) {
                return null;
            }
        }, executorService);
    }

    @Getter @Setter
    public static class UUIDPair {

        private UUID uuid;
        private String name;

    }

}