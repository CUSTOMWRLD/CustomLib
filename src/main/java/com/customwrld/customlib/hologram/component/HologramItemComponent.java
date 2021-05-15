package com.customwrld.customlib.hologram.component;

import com.customwrld.customlib.util.NMSUtil;
import lombok.Getter;
import net.minecraft.server.v1_16_R3.*;
import org.bukkit.Location;
import org.bukkit.craftbukkit.v1_16_R3.CraftWorld;
import org.bukkit.craftbukkit.v1_16_R3.inventory.CraftItemStack;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.List;

public class HologramItemComponent extends AbstractHologramComponent {

    @Getter private ItemStack itemStack;
    @Getter private EntityArmorStand armorStand;
    @Getter private EntityItem entityItem;

    public HologramItemComponent(ItemStack itemStack) {
        this.itemStack = itemStack;
    }

    @Override
    public void create(Location location, List<Player> viewers) {
        this.armorStand = new EntityArmorStand(((CraftWorld)location.getWorld()).getHandle(), location.getX(), location.getY() - 0.05, location.getZ());
        this.entityItem = new EntityItem(((CraftWorld)location.getWorld()).getHandle(), location.getX(), location.getY() - 0.05, location.getZ());

        entityItem.setItemStack(CraftItemStack.asNMSCopy(itemStack));
        entityItem.setPickupDelay(Integer.MAX_VALUE);

        viewers.forEach(player -> {
            NMSUtil.sendPacket(player, new PacketPlayOutSpawnEntityLiving(armorStand));
            NMSUtil.sendPacket(player, new PacketPlayOutSpawnEntity(entityItem));

            entityItem.setNoGravity(true);
            entityItem.startRiding(armorStand);
            armorStand.setNoGravity(true);
            armorStand.setMarker(true);
            armorStand.setInvisible(true);

            NMSUtil.sendPacket(player, new PacketPlayOutEntityMetadata(armorStand.getId(), armorStand.getDataWatcher(), true));
            NMSUtil.sendPacket(player, new PacketPlayOutMount(armorStand));
        });
    }

    @Override
    public void clear(Player player) {
        NMSUtil.sendPacket(player, new PacketPlayOutEntityDestroy(armorStand.getId()));
        NMSUtil.sendPacket(player, new PacketPlayOutEntityDestroy(entityItem.getId()));
    }

    @Override
    public void update(Player player) {
        setItemStack(itemStack, player);
    }

    @Override
    public double getHeight() {
        return 0.65;
    }

    @Override
    public void setLocation(Location location) {
        armorStand.setLocation(location.getX(), location.getY(), location.getZ(), location.getYaw(), location.getPitch());
        this.getParent().viewers.forEach(player -> NMSUtil.sendPacket(player, new PacketPlayOutEntityTeleport(armorStand)));
    }

    public void setItemStack(ItemStack itemStack) {
        this.itemStack = itemStack;
        this.getParent().viewers.forEach(player -> setItemStack(itemStack, player));
    }

    public void setItemStack(ItemStack itemStack, Player player) {
        entityItem.setItemStack(CraftItemStack.asNMSCopy(itemStack));

        NMSUtil.sendPacket(player, new PacketPlayOutEntityMetadata(entityItem.getId(), entityItem.getDataWatcher(), true));
    }

    public void setItemStack(ItemStack itemStack, List<Player> players) {
        players.forEach(player -> setItemStack(itemStack, player));
    }

}
