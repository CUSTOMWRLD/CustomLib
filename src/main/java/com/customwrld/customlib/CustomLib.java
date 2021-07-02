package com.customwrld.customlib;

import com.customwrld.commonlib.CommonLib;
import com.customwrld.commonlib.util.Util;
import com.customwrld.customlib.cache.ClassLoadException;
import com.customwrld.customlib.cache.ClazzCache;
import com.customwrld.customlib.configuration.type.ConfigurationFile;
import com.customwrld.customlib.hologram.Hologram;
import com.customwrld.customlib.hologram.HologramTask;
import com.customwrld.customlib.misc.GlowEnchantment;
import com.customwrld.customlib.npc.NPC;
import com.customwrld.customlib.npc.NPCTask;
import com.customwrld.customlib.smartinvs.InventoryManager;
import com.customwrld.commonlib.util.MC;
import com.customwrld.customlib.util.NMSUtil;
import com.customwrld.pigeon.Pigeon;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.mongodb.MongoClient;
import com.mongodb.client.MongoDatabase;
import lombok.Getter;
import net.kyori.adventure.text.Component;
import net.minecraft.server.v1_16_R3.ChatComponentText;
import net.minecraft.server.v1_16_R3.EnumChatFormat;
import net.minecraft.server.v1_16_R3.PacketPlayOutScoreboardTeam;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.*;
import java.util.concurrent.CopyOnWriteArrayList;

@Getter
public class CustomLib extends JavaPlugin implements Listener {

    @Getter private static CustomLib customLib;

    private CommonLib commonLib;
    private Pigeon pigeon;
    private Gson prettyGson;
    private Gson gson;
    private ConfigurationFile mainConfig;
    private MongoClient mongoClient;
    private MongoDatabase mongoDatabase;
    private InventoryManager invManager;
    private List<NPC> npcs;
    private List<Hologram> holograms;
    private List<UUID> serverUsers;
    private PacketPlayOutScoreboardTeam scoreboardTeam;

    @Override
    public void onEnable() {
        customLib = this;

        this.prettyGson = new GsonBuilder().setPrettyPrinting().create();
        this.gson = new GsonBuilder().create();

        this.mainConfig = new ConfigurationFile(this, "config");

        for (ClazzCache clazzCache : ClazzCache.values()) {
            try {
                clazzCache.load();
            } catch (ClassLoadException e) {
                e.printStackTrace();
            }
        }

        this.loadPigeon();
        this.loadMongo();

        this.npcs = new CopyOnWriteArrayList<>();
        this.holograms = new CopyOnWriteArrayList<>();
        this.serverUsers = new ArrayList<>();

        new NPCTask(this).run();
        new HologramTask(this).run();

        this.invManager = new InventoryManager(this);
        this.invManager.init();

        this.getServer().getPluginManager().registerEvents(this, this);

        GlowEnchantment.register();

        this.pigeon.getConvertersRegistry()
                .registerConvertersInPackage("com.customwrld.customlib.pigeon.converters");

        this.pigeon.getPayloadsRegistry()
                .registerPayloadsInPackage("com.customwrld.customlib.pigeon.payloads");

        this.pigeon.setupDefaultUpdater();

        this.commonLib = new CommonLib(this.mongoClient, this.mongoDatabase, this.pigeon);

        new BukkitRunnable() {
            @Override
            public void run() {
                pigeon.acceptDelivery();
            }
        }.runTaskLater(this, 1);

        PacketPlayOutScoreboardTeam scoreboardTeam = new PacketPlayOutScoreboardTeam();

        try {
            NMSUtil.setValue(scoreboardTeam, "a", "NPC-TEAM");
            NMSUtil.setValue(scoreboardTeam, "c", new ChatComponentText(MC.Style.toLegacy(Component.text("[NPC] ", MC.CC.DARK_GRAY.getTextColor()))));
            NMSUtil.setValue(scoreboardTeam, "e", "never");
            NMSUtil.setValue(scoreboardTeam, "g", EnumChatFormat.DARK_GRAY);
        } catch (Exception e) {
            e.printStackTrace();
        }

        this.scoreboardTeam = scoreboardTeam;
    }

    @Override
    public void onDisable() {
        if(this.pigeon != null) { this.pigeon.getPostOffice().disconnect(); }
    }

    @EventHandler
    public void onPlayerJoinEvent(PlayerJoinEvent event) {
        Player player = event.getPlayer();
        CustomLib customLib = CustomLib.getCustomLib();

        try {
            NMSUtil.setValue(this.scoreboardTeam, "i", 0);
        } catch (Exception e) {
            e.printStackTrace();
        }

        NMSUtil.sendPacket(player, this.scoreboardTeam);

        new BukkitRunnable() {
            @Override
            public void run() {
                customLib.getServerUsers().add(player.getUniqueId());
            }
        }.runTaskLater(customLib, 60);
    }

    @EventHandler
    public void onPlayerQuitEvent(PlayerQuitEvent event) {
        Player player = event.getPlayer();

        this.serverUsers.remove(player.getUniqueId());
        this.npcs.forEach(npc -> npc.delete(player));
        this.holograms.forEach(hologram -> hologram.delete(player));
    }

    private void loadMongo() {
        this.mongoClient = new MongoClient(this.mainConfig.getString("MONGO.HOST"), this.mainConfig.getInt("MONGO.PORT"));
        this.mongoDatabase = this.mongoClient.getDatabase(this.mainConfig.getString("MONGO.DATABASE"));
    }

    private void loadPigeon() {
        String host = this.mainConfig.getString("RABBITMQ.HOST");
        int port = this.mainConfig.getInt("RABBITMQ.PORT");

        this.pigeon = new Pigeon();
        this.pigeon.initialize(host, port, "customwrld", "minecraft-server-" + Util.generateId());
    }
}
