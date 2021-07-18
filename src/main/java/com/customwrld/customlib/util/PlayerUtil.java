package com.customwrld.customlib.util;

import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;

import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.UUID;
import java.util.stream.Collectors;

public class PlayerUtil {

    public static List<String> getNames(List<Player> players) {
        return players.stream().map(Player::getName).collect(Collectors.toList());
    }

    public static List<String> getNamesOffline(List<OfflinePlayer> players) {
        return players.stream().map(OfflinePlayer::getName).collect(Collectors.toList());
    }

    public static List<UUID> getUUIDs(List<Player> players) {
        return players.stream().map(Player::getUniqueId).collect(Collectors.toList());
    }

    public static List<UUID> getUUIDsOffline(List<OfflinePlayer> players) {
        return players.stream().map(OfflinePlayer::getUniqueId).collect(Collectors.toList());
    }

    public static Map<UUID, String> getAll(List<Player> players) {
        return players.stream().collect(Collectors.toMap(Player::getUniqueId, Player::getName));
    }

    public static Map<UUID, String> getAllOffline(List<OfflinePlayer> players) {
        return players.stream().collect(Collectors.toMap(OfflinePlayer::getUniqueId,
                player -> Objects.requireNonNullElse(player.getName(), "")));
    }

}
