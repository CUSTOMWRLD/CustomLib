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
import com.mongodb.MongoClient;
import com.mongodb.client.MongoDatabase;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import lombok.Getter;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.requests.GatewayIntent;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;

import javax.security.auth.login.LoginException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;


public class CustomLib extends JavaPlugin implements Listener {

    public static final Gson GSON = new Gson();
    public static final Type LIST_STRING_TYPE = new TypeToken<List<String>>() {}.getType();

    @Getter private static CustomLib instance;
    @Getter private ConfigurationFile mainConfig;
    @Getter private Connection rabbitMqConnection;
    @Getter private MongoClient mongoClient;
    @Getter private MongoDatabase mongoDatabase;
    @Getter private String serverName;
    @Getter private InventoryManager invManager;
    @Getter private JDA jda;
    @Getter private List<NPC> npcs;
    @Getter private List<Hologram> holograms;
    @Getter private List<UUID> serverUsers;

    @Override
    public void onEnable() {
        instance = this;
        mainConfig = new ConfigurationFile(this, "config");
        serverName = mainConfig.getString("SERVER_NAME");

        for (ClazzCache clazzCache : ClazzCache.values()) {
            try {
                clazzCache.load();
            } catch (ClassLoadException e) {
                e.printStackTrace();
            }
        }

        loadJDA();
        connectAQMPClient();
        loadMongo();

        npcs = new ArrayList<>();
        holograms = new ArrayList<>();
        serverUsers = new ArrayList<>();

        new NPCTask(instance).run();
        new HologramTask(instance).run();

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
        CustomLib customLib = CustomLib.getInstance();

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

        npcs.forEach(npc -> npc.delete(player));
        holograms.forEach(hologram -> hologram.delete(player));
    }

    public void loadJDA() {
        JDABuilder builder = JDABuilder.create(mainConfig.getString("DISCORD.TOKEN"), GatewayIntent.GUILD_MESSAGES, GatewayIntent.GUILD_MEMBERS);

        try {
            jda = builder.build();
        } catch (LoginException exception) {
            exception.printStackTrace();
        }
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
