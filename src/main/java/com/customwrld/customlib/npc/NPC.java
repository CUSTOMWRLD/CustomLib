package com.customwrld.customlib.npc;

import com.customwrld.customlib.CustomLib;
import com.customwrld.customlib.cache.ClazzCache;
import com.customwrld.customlib.hologram.Hologram;
import com.customwrld.customlib.hologram.component.AbstractHologramComponent;
import com.customwrld.customlib.util.CC;
import com.customwrld.customlib.util.NMSUtil;
import com.mojang.authlib.GameProfile;
import com.mojang.authlib.properties.Property;
import net.minecraft.server.v1_16_R3.*;
import org.apache.commons.lang.RandomStringUtils;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.craftbukkit.v1_16_R3.CraftServer;
import org.bukkit.craftbukkit.v1_16_R3.CraftWorld;
import org.bukkit.entity.Player;

import java.util.Collections;
import java.util.HashSet;
import java.util.UUID;

public class NPC {

    public String server;
    public Location location;
    public Hologram hologram;
    public EntityPlayer entityNPC;
    public HashSet<Player> viewers;
    public GameProfile gameProfile;
    public int entityId;

    public NPC(String server, Location location, String skin, String signature, Hologram hologram) {
        this.server = server;
        this.location = location;
        this.viewers = new HashSet<>();

        if(hologram != null) {
            this.hologram = hologram;
        }

        MinecraftServer nmsServer = ((CraftServer) Bukkit.getServer()).getServer();
        WorldServer nmsWorld = ((CraftWorld) location.getWorld()).getHandle();

        gameProfile = new GameProfile(UUID.randomUUID(), CC.translate("&8[NPC] " + generateRandom()));
        gameProfile.getProperties().put("textures", new Property("textures", skin, signature));
        entityNPC = new EntityPlayer(nmsServer, nmsWorld, gameProfile, new PlayerInteractManager(nmsWorld));

        entityId = entityNPC.getId();
    }

    private String generateRandom() {
        return RandomStringUtils.randomAlphanumeric(8);
    }

    public void setLocation(Location location) {
        entityNPC.setLocation(location.getX(), location.getY(), location.getZ(), location.getYaw(), location.getPitch());

        if (hologram != null) {
            for(AbstractHologramComponent component : hologram.getChildren()) {
                component.setLocation(location);
            }
        }

        updateLoc();
    }

    public void updateLoc() {
        Bukkit.getOnlinePlayers().forEach(player -> NMSUtil.sendPacket(player, new PacketPlayOutEntityTeleport(entityNPC)));
    }

    public void lookAt(Player target) {
        Location direction = this.location.clone().setDirection(target.getLocation().subtract(this.location.clone()).toVector());
        for (Player player : Bukkit.getOnlinePlayers()) {
            if (target != player) continue;
            NMSUtil.sendPacket(player, new PacketPlayOutEntity.PacketPlayOutEntityLook(entityId, (byte) (direction.getYaw() % (!direction.equals(this.location) ? 360 : 0) * 256 / 360), (byte) (direction.getPitch() % (!direction.equals(this.location) ? 360. : 0) * 256 / 360), false));
            NMSUtil.sendPacket(player, new PacketPlayOutEntityHeadRotation(entityNPC, (byte) (((direction.getYaw()) * 256.0F) / 360.0F)));
        }
    }

    public void updateSkin() {
        try {
            ClazzCache.GAME_PROFILE_FIELD.getCacheField().set(entityNPC, gameProfile);
        } catch (IllegalAccessException illegalAccessException) {
            illegalAccessException.printStackTrace();
        }
    }

    public void spawn(Player player) throws Exception {
        this.viewers.add(player);

        if(hologram != null) {
            CustomLib.getCustomLib().getHolograms().add(this.hologram);
            hologram.render(player);
        }

        setLocation(this.location);

        NMSUtil.sendPacket(player, new PacketPlayOutPlayerInfo(PacketPlayOutPlayerInfo.EnumPlayerInfoAction.ADD_PLAYER, entityNPC));
        NMSUtil.sendPacket(player, new PacketPlayOutNamedEntitySpawn(entityNPC));

        DataWatcher watcher = entityNPC.getDataWatcher();
        watcher.set(new DataWatcherObject<>(16, DataWatcherRegistry.a), (byte) 127);

        NMSUtil.sendPacket(player, new PacketPlayOutEntityMetadata(entityId, watcher, true));

        PacketPlayOutScoreboardTeam packetPlayOutScoreboardTeam = new PacketPlayOutScoreboardTeam();
        NMSUtil.setValue(packetPlayOutScoreboardTeam, "a", generateRandom());
        NMSUtil.setValue(packetPlayOutScoreboardTeam, "e", "never");
        NMSUtil.setValue(packetPlayOutScoreboardTeam, "h", Collections.singletonList(gameProfile.getName()));
        NMSUtil.setValue(packetPlayOutScoreboardTeam, "i", 0);
        NMSUtil.setValue(packetPlayOutScoreboardTeam, "j", 0);

        NMSUtil.sendPacket(player, packetPlayOutScoreboardTeam);

        lookAt(player);

        Bukkit.getServer().getScheduler().scheduleSyncDelayedTask(CustomLib.getCustomLib(), () -> NMSUtil.sendPacket(player, new PacketPlayOutPlayerInfo(PacketPlayOutPlayerInfo.EnumPlayerInfoAction.REMOVE_PLAYER, entityNPC)), 20 * (2));
    }

    public void delete(Player player) {
        this.viewers.remove(player);
        CustomLib.getCustomLib().getHolograms().remove(this.hologram);

        NMSUtil.sendPacket(player, new PacketPlayOutEntityDestroy(entityId));
        hologram.delete(player);
    }
}