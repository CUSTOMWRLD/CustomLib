package com.customwrld.customlib.assemble;

import com.customwrld.customlib.assemble.events.AssembleBoardCreateEvent;
import lombok.Getter;
import lombok.Setter;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.HandlerList;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

@Getter @Setter
public class Assemble {

	private JavaPlugin plugin;

	private AssembleAdapter adapter;
	private AssembleThread thread;
	private AssembleListener listeners;
	private AssembleStyle assembleStyle = AssembleStyle.MODERN;

	private Map<UUID, AssembleBoard> boards;

	private long ticks = 2;
	private boolean hook = false, debugMode = true;

	public Assemble(JavaPlugin plugin, AssembleAdapter adapter) {
		this.plugin = plugin;
		this.adapter = adapter;
		this.boards = new ConcurrentHashMap<>();

		this.setup();
	}

	public void setup() {
		this.listeners = new AssembleListener(this);
		this.plugin.getServer().getPluginManager().registerEvents(listeners, this.plugin);

		if (this.thread != null) {
			this.thread.stop();
			this.thread = null;
		}

		for (Player player : Bukkit.getOnlinePlayers()) {
			AssembleBoardCreateEvent createEvent = new AssembleBoardCreateEvent(player);

			Bukkit.getPluginManager().callEvent(createEvent);
			if (createEvent.isCancelled()) {
				return;
			}

			getBoards().putIfAbsent(player.getUniqueId(), new AssembleBoard(player, this));
		}

		this.thread = new AssembleThread(this);
	}

	public void cleanup() {
		if (this.thread != null) {
			this.thread.stop();
			this.thread = null;
		}

		if (listeners != null) {
			HandlerList.unregisterAll(listeners);
			listeners = null;
		}

		for (UUID uuid : getBoards().keySet()) {
			Player player = Bukkit.getPlayer(uuid);

			if (player == null || !player.isOnline()) {
				continue;
			}

			getBoards().remove(uuid);
			player.setScoreboard(Bukkit.getScoreboardManager().getNewScoreboard());
		}
	}

}
