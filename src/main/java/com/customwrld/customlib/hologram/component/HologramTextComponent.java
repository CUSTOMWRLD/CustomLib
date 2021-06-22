package com.customwrld.customlib.hologram.component;

import com.customwrld.customlib.hologram.Hologram;
import com.customwrld.customlib.util.MC;
import com.customwrld.customlib.util.NMSUtil;
import lombok.Getter;
import net.minecraft.server.v1_16_R3.*;
import org.bukkit.Location;
import org.bukkit.craftbukkit.v1_16_R3.CraftWorld;
import org.bukkit.craftbukkit.v1_16_R3.util.CraftChatMessage;
import org.bukkit.entity.Player;

import java.util.List;

public class HologramTextComponent extends AbstractHologramComponent {

    @Getter private String text;
    @Getter private EntityArmorStand armorStand;

    public HologramTextComponent(String text) {
        this.text = text;
    }

    @Override
    public void create(Location location, List<Player> viewers) {
        this.armorStand = new EntityArmorStand(((CraftWorld)location.getWorld()).getHandle(), location.getX(), location.getY() - 0.4, location.getZ());

        armorStand.setCustomNameVisible(text.length() >= 1);
        armorStand.setCustomName(CraftChatMessage.fromStringOrNull(MC.Style.translatePAPI(null, text)));

        armorStand.setNoGravity(true);
        armorStand.setMarker(true);
        armorStand.setInvisible(true);

        viewers.forEach(player -> {
            NMSUtil.sendPacket(player, new PacketPlayOutSpawnEntityLiving(armorStand));
            NMSUtil.sendPacket(player, new PacketPlayOutEntityMetadata(armorStand.getId(), armorStand.getDataWatcher(), true));
        });
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
    public double getHeight() {
        return 0.27;
    }

    @Override
    public void setLocation(Location location) {
        armorStand.setLocation(location.getX(), location.getY() - 0.4, location.getZ(), location.getYaw(), location.getPitch());
        this.getParent().viewers.forEach(player -> NMSUtil.sendPacket(player, new PacketPlayOutEntityTeleport(armorStand)));
    }

    @Override
    public AbstractHologramComponent clone(Hologram newParent) {
        HologramTextComponent clone = new HologramTextComponent(text);
        if(this.location != null) { clone.location = this.location.clone(); }
        clone.armorStand = this.armorStand;
        clone.setParent(newParent);
        return clone;
    }

    public void setText(String text) {
        this.text = text;
        this.getParent().viewers.forEach(player -> setText(text, player));
    }

    public void setText(String text, Player player) {
        armorStand.setCustomName(new ChatMessage(MC.Style.translatePAPI(player, text)));

        NMSUtil.sendPacket(player, new PacketPlayOutEntityMetadata(armorStand.getId(), armorStand.getDataWatcher(), true));
    }

    public void setText(String text, List<Player> players) {
        players.forEach(player -> setText(text, player));
    }
}
