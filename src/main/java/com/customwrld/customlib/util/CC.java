package com.customwrld.customlib.util;

import com.google.common.collect.ImmutableMap;
import me.clip.placeholderapi.PlaceholderAPI;
import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.chat.BaseComponent;
import net.md_5.bungee.api.chat.ComponentBuilder;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;

import java.awt.*;
import java.util.List;
import java.util.*;

public class CC {

    public static final String BLUE;
    public static final String AQUA;
    public static final String YELLOW;
    public static final String RED;
    public static final String GRAY;
    public static final String GOLD;
    public static final String GREEN;
    public static final String WHITE;
    public static final String BLACK;
    public static final String BOLD;
    public static final String ITALIC;
    public static final String UNDER_LINE;
    public static final String STRIKE_THROUGH;
    public static final String RESET;
    public static final String MAGIC;
    public static final String DARK_BLUE;
    public static final String DARK_AQUA;
    public static final String DARK_GRAY;
    public static final String DARK_GREEN;
    public static final String DARK_PURPLE;
    public static final String DARK_RED;
    public static final String PINK;
    public static final String MENU_BAR;
    public static final String CHAT_BAR;
    public static final String SB_BAR;
    private static final Map<String, ChatColor> MAP_INTERNAL;
    public static final Map<String, ChatColor> MAP;

    static {
        MAP_INTERNAL = new HashMap<>();

        for (ChatColor chatColor : ChatColor.values()) {
            MAP_INTERNAL.put(chatColor.name(), chatColor);
        }

        MAP = Collections.unmodifiableMap(MAP_INTERNAL);

        BLUE = ChatColor.BLUE.toString();
        AQUA = ChatColor.AQUA.toString();
        YELLOW = ChatColor.YELLOW.toString();
        RED = ChatColor.RED.toString();
        GRAY = ChatColor.GRAY.toString();
        GOLD = ChatColor.GOLD.toString();
        GREEN = ChatColor.GREEN.toString();
        WHITE = ChatColor.WHITE.toString();
        BLACK = ChatColor.BLACK.toString();
        BOLD = ChatColor.BOLD.toString();
        ITALIC = ChatColor.ITALIC.toString();
        UNDER_LINE = ChatColor.UNDERLINE.toString();
        STRIKE_THROUGH = ChatColor.STRIKETHROUGH.toString();
        RESET = ChatColor.RESET.toString();
        MAGIC = ChatColor.MAGIC.toString();
        DARK_BLUE = ChatColor.DARK_BLUE.toString();
        DARK_AQUA = ChatColor.DARK_AQUA.toString();
        DARK_GRAY = ChatColor.DARK_GRAY.toString();
        DARK_GREEN = ChatColor.DARK_GREEN.toString();
        DARK_PURPLE = ChatColor.DARK_PURPLE.toString();
        DARK_RED = ChatColor.DARK_RED.toString();
        PINK = ChatColor.LIGHT_PURPLE.toString();
        MENU_BAR = ChatColor.GRAY.toString() + ChatColor.STRIKETHROUGH.toString() + "                                    ";
        CHAT_BAR = ChatColor.GRAY.toString() + ChatColor.STRIKETHROUGH.toString() + "                                                                              ";
        SB_BAR = ChatColor.GRAY.toString() + ChatColor.STRIKETHROUGH.toString() + "                      ";
    }

    private static final ImmutableMap<ChatColor, Material> CONCRETE_MAP = ImmutableMap.<ChatColor, Material> builder()
            .put(ChatColor.AQUA, Material.LIGHT_BLUE_CONCRETE)
            .put(ChatColor.BLACK, Material.BLACK_CONCRETE)
            .put(ChatColor.BLUE, Material.LIGHT_BLUE_CONCRETE)
            .put(ChatColor.DARK_AQUA, Material.CYAN_CONCRETE)
            .put(ChatColor.DARK_BLUE, Material.BLUE_CONCRETE)
            .put(ChatColor.DARK_GRAY, Material.GRAY_CONCRETE)
            .put(ChatColor.DARK_GREEN, Material.GREEN_CONCRETE)
            .put(ChatColor.DARK_PURPLE, Material.PURPLE_CONCRETE)
            .put(ChatColor.DARK_RED, Material.RED_CONCRETE)
            .put(ChatColor.GOLD, Material.ORANGE_CONCRETE)
            .put(ChatColor.GRAY, Material.GRAY_CONCRETE)
            .put(ChatColor.GREEN, Material.LIME_CONCRETE)
            .put(ChatColor.LIGHT_PURPLE, Material.MAGENTA_CONCRETE)
            .put(ChatColor.RED, Material.RED_CONCRETE)
            .put(ChatColor.WHITE, Material.WHITE_CONCRETE)
            .put(ChatColor.YELLOW, Material.YELLOW_CONCRETE)
            .build();

    public static String CONSOLE = DARK_RED + "Console";

    public static Material toMaterial(ChatColor color) { return CONCRETE_MAP.get(color); }

    public static Set<String> getColorNames() {
        return MAP.keySet();
    }

    public static ChatColor getColorFromName(String name) {
        if (MAP.containsKey(name.trim().toLowerCase())) {
            return MAP.get(name.trim().toLowerCase());
        }

        ChatColor color;

        try {
            color = ChatColor.valueOf(name.toUpperCase().replace(" ", "_"));
        } catch (Exception e) {
            return null;
        }

        return color;
    }

    //0.0 - 1.0
    public static ChatColor HSV(float h, float s, float b) {
        return ChatColor.of(Color.getHSBColor(h, s, b));
    }

    public static org.bukkit.ChatColor toLegacy(ChatColor color) {
        return org.bukkit.ChatColor.getByChar(color.toString().charAt(1));
    }

    public static List<org.bukkit.ChatColor> toLegacy(Collection<ChatColor> colors) {
        List<org.bukkit.ChatColor> list = new ArrayList<>();
        for(ChatColor color : colors) {
            list.add(toLegacy(color));
        }
        return list;
    }

    public static org.bukkit.ChatColor[] toLegacy(ChatColor[] colors) {
        org.bukkit.ChatColor[] array = new org.bukkit.ChatColor[colors.length];
        for(int i = 0; i < colors.length; i++) {
            array[i] = toLegacy(colors[i]);
        }
        return array;
    }

    public static String translate(String in) {
        return ChatColor.translateAlternateColorCodes('&', in);
    }

    public static String translate(Player player, String in) {
        return CC.translate(PlaceholderAPI.setPlaceholders(player, in));
    }

    public static Color hexToRGB(String colorStr) {
        return new Color(
                Integer.valueOf(colorStr.substring(0, 2), 16),
                Integer.valueOf(colorStr.substring(2, 4), 16),
                Integer.valueOf(colorStr.substring(4, 6), 16));
    }

    public static String gradient(String text, String formatting, Color from, Color to) {
        StringBuilder builder = new StringBuilder();
        float stepR = Math.abs(from.getRed() - to.getRed()) / (float) text.length();
        float stepG = Math.abs(from.getGreen() - to.getGreen()) / (float) text.length();
        float stepB = Math.abs(from.getBlue() - to.getBlue()) / (float) text.length();
        for (int step = 0; step < text.length(); step++) {
            int newR = (int) (from.getRed() < to.getRed() ? from.getRed() + (stepR * step) : from.getRed() - (stepR * step));
            int newG = (int) (from.getGreen() < to.getGreen() ? from.getGreen() + (stepG * step) : from.getGreen() - (stepG * step));
            int newB = (int) (from.getBlue() < to.getBlue() ? from.getBlue() + (stepB * step) : from.getBlue() - (stepB * step));
            builder.append(CC.RGB(newR, newG, newB)).append(formatting).append(text.charAt(step));
        }
        return builder.toString();
    }

    public static TextComponent gradientComponent(TextComponent target, Color from, Color to) {
        //builder.append(base);
        ComponentBuilder builder = new ComponentBuilder().retain(ComponentBuilder.FormatRetention.ALL);
        String text = target.toPlainText();
        int charAmount = text.length();

        gradientComponentInternal(builder, target, from, to, 0, charAmount);
        return ChatUtil.builderToComponent(builder);
    }

    private static int gradientComponentInternal(ComponentBuilder builder, TextComponent current, Color from, Color to, int step, int steps) {

        String text = current.getText();

        for (char c : text.toCharArray()) {
            TextComponent formatting = current.duplicate();
            formatting.setText("");
            if(formatting.getExtra() != null) { formatting.getExtra().clear(); }
            builder.append(formatting).append(String.valueOf(c)).color(ChatColor.of(getColorBetween(from, to, step / (float) steps)));
            step++;
        }

        if(current.getExtra() != null) {
            for (BaseComponent component : current.getExtra()) {
                if (!(component instanceof TextComponent)) {
                    continue;
                }
                step = gradientComponentInternal(builder, (TextComponent) component, from, to, step, steps);
            }
        }

        return step;
    }

    public static Color getColorBetween(Color from, Color to, float x) {
        float stepR = Math.abs(from.getRed() - to.getRed());
        float stepG = Math.abs(from.getGreen() - to.getGreen());
        float stepB = Math.abs(from.getBlue() - to.getBlue());

        int r = (int) (from.getRed() < to.getRed() ? from.getRed() + (stepR * x) : from.getRed() - (stepR * x));
        int g = (int) (from.getGreen() < to.getGreen() ? from.getGreen() + (stepG * x) : from.getGreen() - (stepG * x));
        int b = (int) (from.getBlue() < to.getBlue() ? from.getBlue() + (stepB * x) : from.getBlue() - (stepB * x));

        return new Color(r, g, b);
    }

    public static net.md_5.bungee.api.ChatColor RGB(int r, int g, int b) {
        return net.md_5.bungee.api.ChatColor.of(new Color(r, g, b));
    }

    public static ChatColor RGB(int rgb) {
        return ChatColor.of(new Color(rgb));
    }

    public static ChatColor RGB(String hexRgb) {
        return ChatColor.of(hexRgb);
    }

    public static java.util.List<String> translate(java.util.List<String> lines) {
        List<String> toReturn = new ArrayList<>();
        for (String line : lines) {
            toReturn.add(ChatColor.translateAlternateColorCodes('&', line));
        }
        return toReturn;
    }

    public static List<String> translate(String[] lines) {
        List<String> toReturn = new ArrayList<>();
        for (String line : lines) {
            if (line != null) {
                toReturn.add(ChatColor.translateAlternateColorCodes('&', line));
            }
        }
        return toReturn;
    }

    public static void broadcastOps(String message) {
        Bukkit.getOnlinePlayers().forEach(player -> {
            if(player.isOp()) {
                player.sendMessage(CC.translate(message));
            }
        });
    }

}
