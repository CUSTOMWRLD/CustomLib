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
        CustomLib customLib = CustomLib.getCustomLib();
        if(customLib.getNpcs().isEmpty()) return;
        for(NPC npc : customLib.getNpcs()) {
            for (Player player : Bukkit.getOnlinePlayers()) {
                if(customLib.getServerUsers().contains(player.getUniqueId())) {
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