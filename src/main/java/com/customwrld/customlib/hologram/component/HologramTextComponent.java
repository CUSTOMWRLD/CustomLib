package com.customwrld.customlib.hologram.component;

import com.customwrld.customlib.util.CC;
import com.customwrld.customlib.util.NMSUtil;
import lombok.Getter;
import net.minecraft.server.v1_16_R3.*;
import org.apache.commons.lang.RandomStringUtils;
import org.bukkit.Location;
import org.bukkit.craftbukkit.v1_16_R3.CraftWorld;
import org.bukkit.craftbukkit.v1_16_R3.util.CraftChatMessage;
import org.bukkit.entity.Player;

import java.util.Collections;
import java.util.List;

public class HologramTextComponent extends AbstractHologramComponent {

    @Getter private String text;
    @Getter private EntityArmorStand armorStand;

    public HologramTextComponent(String text) {
        this.text = text;
    }

    @Override
    public double create(Location location, List<Player> viewers) {
        this.armorStand = new EntityArmorStand(((CraftWorld)location.getWorld()).getHandle(), location.getX(), location.getY() - 0.4, location.getZ());

        armorStand.setCustomNameVisible(text.length() >= 1);
        armorStand.setCustomName(CraftChatMessage.fromStringOrNull(CC.translate(null, text)));

//        // "net.minecraft.server.v1_10_R1.EntityArmorStand#c(NBTTagCompound)" is a 1.10 method. Find the correct name for the version you are using.
//        NBTTagCompound tag = armorStand.getHandle;
//        tag.setInt("DisabledSlot", 4144959);

        armorStand.disabledSlots = 4144959;
        armorStand.setNoGravity(true);
        armorStand.setMarker(true);
        armorStand.setInvisible(true);

        viewers.forEach(player -> {
            NMSUtil.sendPacket(player, new PacketPlayOutSpawnEntityLiving(armorStand));
            NMSUtil.sendPacket(player, new PacketPlayOutEntityMetadata(armorStand.getId(), armorStand.getDataWatcher(), true));
        });
        return 0.27;
    }

    @Override
    public void clear(Player player) {
        NMSUtil.sendPacket(player, new PacketPlayOutEntityDestroy(armorStand.getId()));
    }

    @Override
    public void update(Player player) {
        setText(text, player);
    }

    @Override
    public void setLocation(Location location) {
        armorStand.setLocation(location.getX(), location.getY(), location.getZ(), location.getYaw(), location.getPitch());
        this.getParent().viewers.forEach(player -> NMSUtil.sendPacket(player, new PacketPlayOutEntityTeleport(armorStand)));
    }

    public void setText(String text) {
        this.text = text;
        this.getParent().viewers.forEach(player -> setText(text, player));
    }

    public void setText(String text, Player player) {
        armorStand.setCustomName(new ChatMessage(CC.translate(player, text)));

        NMSUtil.sendPacket(player, new PacketPlayOutEntityMetadata(armorStand.getId(), armorStand.getDataWatcher(), true));
    }

    public void setText(String text, List<Player> players) {
        players.forEach(player -> setText(text, player));
    }
}
