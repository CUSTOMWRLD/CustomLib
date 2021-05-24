package com.customwrld.customlib;

import com.customwrld.customlib.cache.ClassLoadException;
import com.customwrld.customlib.cache.ClazzCache;
import com.customwrld.customlib.configuration.type.ConfigurationFile;
import com.customwrld.customlib.hologram.Hologram;
import com.customwrld.customlib.hologram.HologramTask;
import com.customwrld.customlib.misc.GlowEnchantment;
import com.customwrld.customlib.npc.NPC;
import com.customwrld.customlib.npc.NPCTask;
import com.customwrld.customlib.smartinvs.InventoryManager;
import com.google.common.reflect.TypeToken;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.mongodb.MongoClient;
import com.mongodb.client.MongoDatabase;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import lombok.Getter;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.UUID;


public class CustomLib extends JavaPlugin implements Listener {

    public static final Type LIST_STRING_TYPE = new TypeToken<List<String>>() {}.getType();
    public static final Type MAP_STRING_TYPE = new TypeToken<Map<String, Object>>() {}.getType();

    @Getter private static CustomLib customLib;
    @Getter private Gson prettyGson;
    @Getter private Gson gson;
    @Getter private ConfigurationFile mainConfig;
    @Getter private Connection rabbitMqConnection;
    @Getter private MongoClient mongoClient;
    @Getter private MongoDatabase mongoDatabase;
    @Getter private InventoryManager invManager;
    @Getter private List<NPC> npcs;
    @Getter private List<Hologram> holograms;
    @Getter private List<UUID> serverUsers;

    @Override
    public void onEnable() {
        customLib = this;

        prettyGson = new GsonBuilder().setPrettyPrinting().create();
        gson = new GsonBuilder().create();

        mainConfig = new ConfigurationFile(this, "config");

        for (ClazzCache clazzCache : ClazzCache.values()) {
            try {
                clazzCache.load();
            } catch (ClassLoadException e) {
                e.printStackTrace();
            }
        }

        connectAQMPClient();
        loadMongo();

        npcs = new ArrayList<>();
        holograms = new ArrayList<>();
        serverUsers = new ArrayList<>();

        new NPCTask(customLib).run();
        new HologramTask(customLib).run();

        invManager = new InventoryManager(this);
        invManager.init();

        getServer().getPluginManager().registerEvents(this, this);

        GlowEnchantment.register();
    }

    @Override
    public void onDisable() {
        try {
            if(rabbitMqConnection != null && rabbitMqConnection.isOpen()) {
                rabbitMqConnection.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @EventHandler
    public void onPlayerJoinEvent(PlayerJoinEvent event) {
        Player player = event.getPlayer();
        CustomLib customLib = CustomLib.getCustomLib();

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

        serverUsers.remove(player.getUniqueId());
        npcs.forEach(npc -> npc.delete(player));
        holograms.forEach(hologram -> hologram.delete(player));
    }

    private void loadMongo() {
        mongoClient = new MongoClient(mainConfig.getString("MONGO.HOST"), mainConfig.getInt("MONGO.PORT"));
        mongoDatabase = mongoClient.getDatabase(mainConfig.getString("MONGO.DATABASE"));
    }

    private void connectAQMPClient() {
        try {
            ConnectionFactory factory = new ConnectionFactory();
            factory.setHost(mainConfig.getString("RABBITMQ.HOST"));
            factory.setPort(mainConfig.getInt("RABBITMQ.PORT"));

            rabbitMqConnection = factory.newConnection();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
