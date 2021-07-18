package com.customwrld.customlib.events;

import com.customwrld.commonlib.modules.profile.GamemodeStorage;
import com.customwrld.commonlib.modules.profile.Profile;
import lombok.Getter;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;
import org.bukkit.plugin.Plugin;
import org.bukkit.scheduler.BukkitScheduler;

import java.util.concurrent.Callable;

/**
 * Any {@linkplain GamemodeStorage} should be 'ensured' within this event. <br>
 * <br>
 * The event is always asynchronous so use <br>
 * {@linkplain BukkitScheduler#scheduleSyncDelayedTask(Plugin, Runnable)} or <br>
 * {@linkplain BukkitScheduler#callSyncMethod(Plugin, Callable)} <br>
 * to call anafe methods from the Bukkit API.
 */
@Getter
public class AsyncProfileLoadEvent extends Event {
    private static final HandlerList handlers = new HandlerList();

    private final Profile profile;

    public AsyncProfileLoadEvent(Profile profile) {
        super(true);
        this.profile = profile;
    }

    @Override
    public HandlerList getHandlers() {
        return handlers;
    }

    public static HandlerList getHandlerList() {
        return handlers;
    }
}
