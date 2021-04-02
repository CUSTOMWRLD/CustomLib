package com.customwrld.customlib.hologram;

import com.customwrld.customlib.hologram.component.AbstractHologramComponent;
import lombok.Getter;
import org.bukkit.Location;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Hologram {

    @Getter private final List<AbstractHologramComponent> children;
    public Location location;
    public List<Player> viewers;

    public Hologram(Location location) {
        this.children = new ArrayList<>();
        this.viewers = new ArrayList<>();
        this.location = location;
        render();
    }

    public void delete(Player player) {
        viewers.remove(player);
        for(AbstractHologramComponent component : children) {
            component.clear(player);
        }
    }

    public Hologram append(AbstractHologramComponent component) {
        children.add(component);
        return this;
    }

    public void render() {
        Location origin = location.clone();
        for (AbstractHologramComponent component : children) {
            origin.subtract(0, component.create(origin, viewers), 0);
        }
    }

    public void render(Player player) {
        viewers.add(player);
        Location origin = location.clone();
        for (AbstractHologramComponent component : children) {
            origin.subtract(0, component.create(origin, Collections.singletonList(player)), 0);
        }
    }
}