package com.customwrld.customlib.cache;

import com.customwrld.customlib.util.NMSUtil;
import lombok.Getter;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;

public enum ClazzCache {

    CRAFT_PLAYER_CLASS(ClazzType.CLASS, "org.bukkit.craftbukkit." + NMSUtil.getBukkitPackage() + ".entity.CraftPlayer", null),
    GET_HANDLE_PLAYER_METHOD(ClazzType.METHOD, "getHandle", CRAFT_PLAYER_CLASS.getCacheClass()),
    ENTITY_PLAYER_CLASS(ClazzType.CLASS,  "net.minecraft.server." + NMSUtil.getBukkitPackage() + ".EntityPlayer", null),
    PLAYER_CONNECTION_FIELD(ClazzType.FIELD, "playerConnection", ENTITY_PLAYER_CLASS.getCacheClass()),
    PLAYER_CONNECTION_CLASS(ClazzType.CLASS, "net.minecraft.server." + NMSUtil.getBukkitPackage() + ".PlayerConnection", null),
    NETWORK_MANAGER_CLASS(ClazzType.CLASS, "net.minecraft.server." + NMSUtil.getBukkitPackage() + ".NetworkManager", null),
    ENTITY_HUMAN_CLASS(ClazzType.CLASS, "net.minecraft.server." + NMSUtil.getBukkitPackage() + ".EntityHuman", null),
    PACKET_PLAY_IN_USE_ENTITY_CLASS(ClazzType.CLASS, "net.minecraft.server." + NMSUtil.getBukkitPackage() + ".PacketPlayInUseEntity", null),
    GAME_PROFILE_FIELD(ClazzType.FIELD, "bJ", ENTITY_HUMAN_CLASS.getCacheClass()),
    CHANNEL_FIELD(ClazzType.FIELD, "channel", NETWORK_MANAGER_CLASS.getCacheClass()),
    NETWORK_MANAGER_FIELD(ClazzType.FIELD, "networkManager", PLAYER_CONNECTION_CLASS.getCacheClass()),
    ID_FIELD(ClazzType.FIELD, "a", PACKET_PLAY_IN_USE_ENTITY_CLASS.getCacheClass());

    @Getter private Constructor<?> cacheConstructor;
    @Getter private Method cacheMethod;
    @Getter private Field cacheField;
    private final ClazzType clazzType;
    private final String name;
    private final Class<?> clazz;
    private final Class<?>[] classes;
    private Class<?> cacheClass;

    ClazzCache(ClazzType clazzType, String name, Class<?> clazz, Class<?>... classes) {
        this.clazzType = clazzType;
        this.name = name;
        this.clazz = clazz;
        this.classes = classes;
    }

    public void load() throws ClassLoadException {
        if (this.clazzType == ClazzType.CLASS) {
            try {
                this.cacheClass = Class.forName(name);
            } catch (ClassNotFoundException e) {
                throw new ClassLoadException("Class could not be loaded: " + name(), e.getCause());
            }
        } else if (this.clazzType == ClazzType.METHOD) {
            try {
                this.cacheMethod = this.clazz.getMethod(this.name, this.classes);
            } catch (NoSuchMethodException e) {
                try {
                    this.cacheMethod = this.clazz.getDeclaredClasses()[0].getMethod(this.name, this.classes);
                } catch (NoSuchMethodException noSuchMethodException) {
                    throw new ClassLoadException("Method could not be loaded: " + name(), e.getCause());
                }
            }
        } else if (this.clazzType == ClazzType.FIELD) {
            if (this.name != null && this.name.length() > 0) {
                try {
                    this.cacheField = this.clazz.getField(this.name);
                } catch (NoSuchFieldException e) {
                    try {
                        this.cacheField = this.clazz.getDeclaredField(this.name);
                        this.cacheField.setAccessible(true);
                    } catch (NoSuchFieldException noSuchFieldException) {
                        throw new ClassLoadException("Field could not be loaded: " + name(), e.getCause());
                    }
                }
            }
        } else {
            try {
                this.cacheConstructor = this.clazz.getConstructor(this.classes);
            } catch (NoSuchMethodException e) {
                try {
                    this.cacheConstructor = this.clazz.getConstructor();
                } catch (NoSuchMethodException noSuchMethodException) {
                    this.cacheConstructor = this.clazz.getDeclaredConstructors()[0];
                }
            }
        }
    }

    public Class<?> getCacheClass() {
        if (cacheClass == null) {
            try {
                load();
            } catch (ClassLoadException e) {
                e.printStackTrace();
            }
        }
        return cacheClass;
    }
}