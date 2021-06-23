package com.customwrld.customlib.hologram.component;

import com.customwrld.customlib.hologram.Hologram;
import com.customwrld.customlib.util.MC;
import java.util.Collection;
import com.customwrld.customlib.util.CC;
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
    @Getter private Location location;
    @Getter private EntityArmorStand armorStand;

    /**
     * If you wish to create an empty hologram, use {@link HologramTextComponent#empty()}.
     */
    public HologramTextComponent(String text) {
        if(text.isEmpty()) { text = " "; }
        this.text = text;
    }

    @Override
    public void create(@Nullable Location location, List<Player> viewers) {
        if(location == null) { location = this.location; } else { this.location = location; }

        if(this.armorStand == null) {
            this.armorStand = new EntityArmorStand(((CraftWorld) location.getWorld()).getHandle(), location.getX(), location.getY() - 0.4, location.getZ());

            this.armorStand.setCustomNameVisible(this.text.length() >= 1);
            this.armorStand.setCustomName(CraftChatMessage.fromStringOrNull(MC.Style.translatePAPI(null, this.text)));

            this.armorStand.setNoGravity(true);
            this.armorStand.setMarker(true);
            this.armorStand.setInvisible(true);
        }

        viewers.forEach(player -> {
            NMSUtil.sendPacket(player, new PacketPlayOutSpawnEntityLiving(this.armorStand));
            NMSUtil.sendPacket(player, new PacketPlayOutEntityMetadata(this.armorStand.getId(), this.armorStand.getDataWatcher(), true));
        });
    }

    @Override
    public void clear(Player player) {
        NMSUtil.sendPacket(player, new PacketPlayOutEntityDestroy(this.armorStand.getId()));
    }

    @Override
    public void update(Player player) {
        setText(this.text, player);
    }

    @Override
    public double getHeight() {
        return 0.27;
    }

    @Override
    public void setLocation(Location location) {
        this.location = location;

        if(this.armorStand != null) {
            this.armorStand.setLocation(location.getX(), location.getY() - 0.4, location.getZ(), location.getYaw(), location.getPitch());
            this.getParent().viewers.forEach(player -> NMSUtil.sendPacket(player, new PacketPlayOutEntityTeleport(this.armorStand)));
        }
    }

    @Override
    public AbstractHologramComponent clone(Hologram newParent) {
        var clone = new HologramTextComponent(this.text);
        if(this.location != null) { clone.location = this.location.clone(); }
        clone.armorStand = this.armorStand;
        clone.setParent(newParent);
        return clone;
    }

    public void setText(String text) {
        this.text = text;
        this.getParent().viewers.forEach(player -> setText(text, player));
    }

    /**
     * @param text Text to set
     * @param player Player to set the text for, if null it calls {@link HologramTextComponent#setText(String)}
     */
    public void setText(String text, @Nullable Player player) {
        if(player == null) {
            setText(text);
            return;
        }

        this.armorStand.setCustomName(new ChatMessage(MC.Style.translatePAPI(player, text)));
        NMSUtil.sendPacket(player, new PacketPlayOutEntityMetadata(this.armorStand.getId(), this.armorStand.getDataWatcher(), true));
    }

    /**
     * @param text Text to set
     * @param players Players to set the text for, if null it calls {@link HologramTextComponent#setText(String)}
     */
    public void setText(String text, @Nullable Collection<Player> players) {
        if(players == null) {
            setText(text);
            return;
        }

        players.forEach(player -> setText(text, player));
    }

    /**
     * This was made to avoid confusion when creating an empty hologram. <br>
     * If you try to create an armorstand with an empty text using packets, it won't spawn. <br>
     * This can happen often if you want to create holograms that are to be modified later. <br>
     * A solution to this is to use a placeholder text for the armorstand and this is what this function does. <br>
     * <br>
     * If you wish to create a hologram with any text, use {@link HologramTextComponent#HologramTextComponent(String)}.
     * @return An empty {@link HologramTextComponent}.
     */
    public static HologramTextComponent empty() {
        return new HologramTextComponent(" ");
    }

}
