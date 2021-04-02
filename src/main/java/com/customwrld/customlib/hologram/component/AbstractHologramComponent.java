package com.customwrld.customlib.hologram.component;

import com.customwrld.customlib.hologram.Hologram;
import lombok.Getter;
import org.bukkit.Location;
import org.bukkit.entity.Player;

import java.util.List;

public abstract class AbstractHologramComponent {

    @Getter protected Location location;
    @Getter private Hologram parent;

    public abstract double create(Location offset, List<Player> viewers);

    public abstract void clear(Player player);

    public abstract void update(Player player);

    public abstract void setLocation(Location location);

}
