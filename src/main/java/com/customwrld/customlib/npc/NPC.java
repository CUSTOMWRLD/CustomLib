package com.customwrld.customlib.npc;

import com.customwrld.commonlib.util.Util;
import com.customwrld.customlib.CustomLib;
import com.customwrld.customlib.cache.ClazzCache;
import com.customwrld.customlib.hologram.Hologram;
import com.customwrld.commonlib.util.MC;
import com.customwrld.customlib.util.NMSUtil;
import com.mojang.authlib.GameProfile;
import com.mojang.authlib.properties.Property;
import net.minecraft.server.v1_16_R3.*;
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
            hologram.direction = false;
            hologram.setLocation(location.clone().add(0, 2.1, 0));
            this.hologram = hologram;
        }

        MinecraftServer nmsServer = ((CraftServer) Bukkit.getServer()).getServer();
        WorldServer nmsWorld = ((CraftWorld) location.getWorld()).getHandle();

        gameProfile = new GameProfile(UUID.randomUUID(), Util.generateId());
        gameProfile.getProperties().put("textures", new Property("textures", skin, signature));
        entityNPC = new EntityPlayer(nmsServer, nmsWorld, gameProfile, new PlayerInteractManager(nmsWorld));

        entityId = entityNPC.getId();
    }

    public void setLocation(Location location) {
        entityNPC.setLocation(location.getX(), location.getY(), location.getZ(), location.getYaw(), location.getPitch());

        if (hologram != null) {
            hologram.setLocation(location.clone().add(0, 2.1, 0));
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

    public void spawn(Player player) {
        this.viewers.add(player);

        setLocation(this.location);

        if(hologram != null) {
            var holograms = CustomLib.getCustomLib().getHolograms();
            if(!holograms.contains(this.hologram)) { holograms.add(this.hologram); }

            if(!hologram.viewers.contains(player)) { hologram.render(player); }
        }

        NMSUtil.sendPacket(player, new PacketPlayOutPlayerInfo(PacketPlayOutPlayerInfo.EnumPlayerInfoAction.ADD_PLAYER, entityNPC));
        NMSUtil.sendPacket(player, new PacketPlayOutNamedEntitySpawn(entityNPC));

        DataWatcher watcher = entityNPC.getDataWatcher();
        watcher.set(new DataWatcherObject<>(16, DataWatcherRegistry.a), (byte) 127);

        NMSUtil.sendPacket(player, new PacketPlayOutEntityMetadata(entityId, watcher, true));


        PacketPlayOutScoreboardTeam scoreboardTeam = CustomLib.getCustomLib().getScoreboardTeam();

        try {
            NMSUtil.setValue(scoreboardTeam, "i", 3);
            NMSUtil.setValue(scoreboardTeam, "h", Collections.singletonList(this.gameProfile.getName()));
        } catch (Exception e) {
            e.printStackTrace();
        }

        NMSUtil.sendPacket(player, scoreboardTeam);

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