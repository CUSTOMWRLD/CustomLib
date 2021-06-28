package com.customwrld.customlib.hologram;

import com.customwrld.customlib.hologram.component.AbstractHologramComponent;
import lombok.Getter;
import org.bukkit.Location;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

public class Hologram {

    @Getter private final CopyOnWriteArrayList<AbstractHologramComponent> children;
    public Location location;
    public CopyOnWriteArrayList<Player> viewers;

    public boolean direction;

    public Hologram(Location location, boolean direction) {
        this.children = new CopyOnWriteArrayList<>();
        this.location = location;
        this.viewers = new CopyOnWriteArrayList<>();

        this.direction = direction;
    }

    public Hologram append(AbstractHologramComponent component) {
        component.setParent(this);
        children.add(component);
        return this;
    }

    public void render() {
        Location origin = location.clone();
        if(direction) {
            for(AbstractHologramComponent component : children) {
                component.create(origin.clone(), viewers);
                origin.subtract(0, component.getHeight(), 0);
            }
        } else {
            for(var i = children.size() - 1; i >= 0; i--) {
                var component = children.get(i);
                component.create(origin.clone(), viewers);
                origin.add(0, component.getHeight(), 0);
            }
        }
    }

    public void render(Player player) {
        viewers.add(player);
        Location origin = location.clone();
        if(direction) {
            for(AbstractHologramComponent component : children) {
                component.create(origin.clone(), Collections.singletonList(player));
                origin.subtract(0, component.getHeight(), 0);
            }
        } else {
            for(var i = children.size() - 1; i >= 0; i--) {
                var component = children.get(i);
                component.create(origin.clone(), Collections.singletonList(player));
                origin.add(0, component.getHeight(), 0);
            }
        }
    }

    public void delete(Player player) {
        viewers.remove(player);
        for(AbstractHologramComponent component : children) {
            component.clear(player);
        }
    }

    public void setLocation(Location location) {
        Location origin = location.clone();
//        for (AbstractHologramComponent component : children) {
//            component.setLocation(origin.clone());
//            origin.subtract(0, component.getHeight(), 0);
//        }

        if(direction) {
            for(AbstractHologramComponent component : children) {
                component.setLocation(origin.clone());
                origin.subtract(0, component.getHeight(), 0);
            }
        } else {
            for(var i = children.size() - 1; i >= 0; i--) {
                var component = children.get(i);
                component.setLocation(origin.clone());
                origin.add(0, component.getHeight(), 0);
            }
        }
    }

    public Hologram clone() {
        Hologram clone = new Hologram(location, this.direction);
        for(AbstractHologramComponent component : this.children) {
            clone.append(component.clone(clone));
        }
        clone.viewers.addAll(this.viewers);
        return clone;
    }

}