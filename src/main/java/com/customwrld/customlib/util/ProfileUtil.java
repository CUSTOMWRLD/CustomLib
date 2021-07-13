package com.customwrld.customlib.util;

import com.customwrld.commonlib.modules.profile.Profile;
import com.customwrld.customlib.CustomLib;
import lombok.Getter;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.metadata.FixedMetadataValue;

import java.util.UUID;
import java.util.concurrent.CompletableFuture;

public class ProfileUtil {
    private ProfileUtil() {}

    @Getter private static String METADATA_KEY = "profile";

    /**
     * Gets player's profile from their metadata. <br>
     * If it's not found, it loads the profile externally (unless {@code onlyMetadata} is {@code true}). <br>
     * <br>
     * Optionally it can automatically associate externally loaded profile with the player if {@code autoAssociate} is {@code true} (Uses {@linkplain ProfileUtil#associateProfile(Player, Profile)}) internally). <br>
     * <br>
     * For synchronous operations it is generally recommended to set {@code onlyMetadata} to {@code true} and use {@linkplain CompletableFuture#getNow(Object)}.
     * @param player The player to look for a profile for.
     * @param onlyMetadata If set to {@code true}, it skips trying to load the profile externally. <br>
     *                     {@linkplain CompletableFuture} returns {@code null} if it can't find a profile in the metadata. <br>
     *                     <br>
     *                     This comes with a downside of the profile being null if it hasn't been associated yet, <br>
     *                     though a chance of that happening is <strong>very</strong> slim due to the profile being associated when a player joins.
     * @param autoAssociate Automatically associate an externally loaded profile to the player (as metadata).
     *                      {@code onlyMetadata} needs to be set to {@code false} for this to take effect.
     */
    public static CompletableFuture<Profile> getAssociatedProfile(Player player, boolean onlyMetadata, boolean autoAssociate) {
        Profile profile = MetadataUtil.getValue(player, METADATA_KEY, CustomLib.getCustomLib(), null, Profile.class, false);
        if(profile == null && !onlyMetadata) {
            var future = Profile.getProfile(player.getName(), player.getUniqueId());
            if(autoAssociate)
                future.thenAccept(loadedProfile -> associateProfile(player, loadedProfile));

            return future;
        }

        return CompletableFuture.completedFuture(profile);
    }

    public static CompletableFuture<Profile> getProfile(String name, UUID uuid, boolean onlyMetadata, boolean autoAssociate) {
        Player player = Bukkit.getPlayer(uuid);
        if(player != null) {
            return getAssociatedProfile(player, onlyMetadata, autoAssociate);
        } else {
            return Profile.getProfile(name, uuid);
        }
    }

    public static CompletableFuture<Profile> getProfile(UUID uuid, boolean onlyMetadata, boolean autoAssociate) {
        Player player = Bukkit.getPlayer(uuid);
        if(player != null) {
            return getAssociatedProfile(player, onlyMetadata, autoAssociate);
        } else {
            return Profile.getProfile(uuid);
        }
    }

    public static CompletableFuture<Profile> getProfile(String name, boolean onlyMetadata, boolean autoAssociate) {
        Player player = Bukkit.getPlayer(name);
        if(player != null) {
            return getAssociatedProfile(player, onlyMetadata, autoAssociate);
        } else {
            return Profile.getProfile(name);
        }
    }

    /**
     * Calls {@linkplain ProfileUtil#getAssociatedProfile(Player, boolean, boolean)} with two last parameters being {@code false} and {@code true}. <br>
     * These are considered the default settings for the aforementioned function. <br>
     * <br>
     * If you wish to use a {@linkplain CompletableFuture#getNow(Object)} instantaneously inside synchronous code, <br>
     * see the documentation for {@linkplain ProfileUtil#getAssociatedProfile(Player, boolean, boolean)}.
     */
    public static CompletableFuture<Profile> getAssociatedProfile(Player player) {
        return getAssociatedProfile(player, false, true);
    }

    public static CompletableFuture<Profile> getProfile(String name, UUID uuid) {
        return getProfile(name, uuid, false, true);
    }

    public static CompletableFuture<Profile> getProfile(UUID uuid) {
        return getProfile(uuid, false, true);
    }

    public static CompletableFuture<Profile> getProfile(String name) {
        return getProfile(name, false, true);
    }

    /**
     * Associates a profile to a player (assigns the profile as player's metadata).
     */
    public static void associateProfile(Player player, Profile profile) {
        player.setMetadata(METADATA_KEY, new FixedMetadataValue(CustomLib.getCustomLib(), profile));
    }

    public static boolean isAnyProfileAssociated(Player player) {
        return player.hasMetadata(METADATA_KEY);
    }

    /**
     * Usage of this is discouraged. Metadata is automatically removed after a player leaves. <br>
     * <br>
     * Use {@linkplain ProfileUtil#associateProfile(Player, Profile)} or {@linkplain ProfileUtil#getAssociatedProfile(Player, boolean, boolean)} <br>
     * (with two last parameters set to {@code true}) to override player's profile with another/newer one.
     */
    public static void disassociateProfile(Player player) {
        player.removeMetadata(METADATA_KEY, CustomLib.getCustomLib());
    }

}
