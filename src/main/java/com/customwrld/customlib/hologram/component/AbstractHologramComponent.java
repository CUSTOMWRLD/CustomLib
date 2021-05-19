package com.customwrld.customlib.hologram.component;

import com.customwrld.customlib.hologram.Hologram;
import lombok.Getter;
import lombok.Setter;
import org.bukkit.Location;
import org.bukkit.entity.Player;

import java.util.List;

public abstract class AbstractHologramComponent {

    @Getter protected Location location;
    @Getter @Setter private Hologram parent;

    public abstract void create(Location offset, List<Player> viewers);

    public abstract void clear(Player player);

    public abstract void update(Player player);

    public abstract double getHeight();

    public abstract void setLocation(Location location);

    public abstract AbstractHologramComponent clone(Hologram newParent);

}
