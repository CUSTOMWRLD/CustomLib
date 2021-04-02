package com.customwrld.customlib.npc;

import com.customwrld.customlib.CustomLib;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

public class NPCTask extends BukkitRunnable {

    public NPCTask(final CustomLib customLib) {
        this.runTaskTimerAsynchronously(customLib, 0L, 1L);
    }

    @Override
    public void run() {
        CustomLib instance = CustomLib.getInstance();
        if(instance.getNpcs().isEmpty()) return;
        for(NPC npc : instance.getNpcs()) {
            for (Player player : Bukkit.getOnlinePlayers()) {
                if(instance.getServerUsers().contains(player.getUniqueId())) {
                    boolean canSeeNPC = player.getWorld() == npc.location.getWorld() && player.getLocation().distance(npc.location) <= 32;
                    if (npc.viewers.contains(player) && !canSeeNPC) {
                        npc.delete(player);
                    } else if (canSeeNPC) {
                        if (!npc.viewers.contains(player)) {
                            try {
                                npc.spawn(player);
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        }
                        npc.lookAt(player);
                    }
                }
            }
        }
    }
}