package com.customwrld.customlib.hologram;

import com.customwrld.customlib.CustomLib;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

public class HologramTask extends BukkitRunnable {

    public HologramTask(final CustomLib customLib) {
        this.runTaskTimerAsynchronously(customLib, 0L, 1L);
    }

    @Override
    public void run() {
        CustomLib instance = CustomLib.getInstance();
        if(instance.getHolograms().isEmpty()) return;
        for(Hologram hologram : instance.getHolograms()) {
            for (Player player : Bukkit.getOnlinePlayers()) {
                if (instance.getServerUsers().contains(player.getUniqueId())) {
                    boolean canSeeHologram = player.getWorld() == hologram.location.getWorld() && player.getLocation().distance(hologram.location) <= 32;
                    if (hologram.viewers.contains(player) && !canSeeHologram) {
                        hologram.delete(player);
                    } else if (canSeeHologram) {
                        if (!hologram.viewers.contains(player)) {
                            hologram.render(player);
                        }
                        hologram.getChildren().forEach(component -> component.update(player));
                    }
                }
            }
        }
    }
}