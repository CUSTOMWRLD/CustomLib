package com.customwrld.customlib.util;

import lombok.AllArgsConstructor;
import lombok.Getter;
import me.clip.placeholderapi.PlaceholderAPI;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.TextComponent;
import net.kyori.adventure.text.format.NamedTextColor;
import net.kyori.adventure.text.format.TextColor;
import net.kyori.adventure.text.format.TextDecoration;
import net.kyori.adventure.text.serializer.legacy.LegacyComponentSerializer;
import org.apache.commons.lang.Validate;
import org.apache.commons.lang3.StringUtils;
import org.bukkit.entity.Player;

import java.awt.*;

public class MC {

    @Getter
    @AllArgsConstructor
    public enum CC {
        BLACK("black", new Color(0x000000)),
        DARK_BLUE("dark_blue", new Color(0x0000AA)),
        DARK_GREEN("dark_green", new Color(0x00AA00)),
        DARK_AQUA("dark_aqua", new Color(0x00AAAA)),
        DARK_RED("dark_red", new Color(0xAA0000)),
        DARK_PURPLE("dark_purple", new Color(0xAA00AA)),
        GOLD("gold", new Color(0xFFAA00)),
        GRAY("gray", new Color(0xAAAAAA)),
        DARK_GRAY("dark_gray", new Color(0x555555)),
        BLUE("blue", new Color(0x5555FF)),
        GREEN("green", new Color(0x55FF55)),
        AQUA("aqua", new Color(0x55FFFF)),
        RED("red", new Color(0xFF5555)),
        LIGHT_PURPLE("light_purple", new Color(0xFF55FF)),
        YELLOW("yellow", new Color(0xFFFF55)),
        WHITE("white", new Color(0xFFFFFF));

        private final String name;
        private final Color color;

        public TextColor getTextColor() {
            return TextColor.color(this.color.getRGB());
        }
    }

    public static class Style {
        private static final LegacyComponentSerializer serializer = LegacyComponentSerializer.builder()
                .character('&')
                .useUnusualXRepeatedCharacterHexFormat()
                .build();

        public static TextComponent CONSOLE = Component.text("Console").color(NamedTextColor.DARK_RED);

        public static TextComponent SEPARATOR = Component.text(StringUtils.repeat(" ", 80))
                .color(NamedTextColor.DARK_GRAY)
                .decoration(TextDecoration.STRIKETHROUGH, true)
                .decoration(TextDecoration.ITALIC, false);

        public static TextComponent MENU_SEPARATOR = Component.text(StringUtils.repeat(" ", 40))
                .color(NamedTextColor.DARK_GRAY)
                .decoration(TextDecoration.STRIKETHROUGH, true)
                .decoration(TextDecoration.ITALIC, false);

        public static TextComponent fromLegacy(String string) {
            return LegacyComponentSerializer.legacy('&').deserialize(string);
        }

        public static String toLegacy(Component component) {
            String legacyText = serializer.serializeOrNull(component);

            if(legacyText != null) {
                return translateLegacy(legacyText);
            }

            return null;
        }

        public static String translateLegacy(String text) {
            Validate.notNull(text, "Cannot translate null text");

            char[] array = text.toCharArray();

            for(int i = 0; i < array.length - 1; ++i) {
                if (array[i] == '&' && "0123456789AaBbCcDdEeFfKkLlMmNnOoRrXx".indexOf(array[i + 1]) > -1) {
                    array[i] = 167;
                    array[i + 1] = Character.toLowerCase(array[i + 1]);
                }
            }

            return new String(array);
        }

        public static String translatePAPI(Player player, String text) {
            return translateLegacy(PlaceholderAPI.setPlaceholders(player, text));
        }

        public static Color hexToColor(String colorStr) {
            return new Color(
                    Integer.valueOf(colorStr.substring(0, 2), 16),
                    Integer.valueOf(colorStr.substring(2, 4), 16),
                    Integer.valueOf(colorStr.substring(4, 6), 16));
        }

        public static TextComponent gradientComponent(String text, Color from, Color to) {
            TextComponent.Builder builder = Component.text();

            int steps = text.length();
            int step = 0;

            for (char c : text.toCharArray()) {
                builder.append(Component.text(c)
                        .color(TextColor.color(getColorBetween(from, to, step / (float) steps).getRGB())));
                step++;
            }

            return builder.build();
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
    }

    public static class Chat {
        public static TextComponent colorPing(int ping) {
            if (ping <= 40) {
                return Component.text(ping)
                        .color(CC.GREEN.getTextColor());
            } else if (ping <= 70) {
                return Component.text(ping)
                        .color(CC.YELLOW.getTextColor());
            } else if (ping <= 100) {
                return Component.text(ping)
                        .color(CC.GOLD.getTextColor());
            } else {
                return Component.text(ping)
                        .color(CC.RED.getTextColor());
            }
        }

        public static TextComponent colorHealth(double health) {
            if (health > 15) {
                return Component.text(convertHealth(health))
                        .color(CC.GREEN.getTextColor());
            } else if (health > 10) {
                return Component.text(convertHealth(health))
                        .color(CC.YELLOW.getTextColor());
            } else if (health > 5) {
                return Component.text(convertHealth(health))
                        .color(CC.GOLD.getTextColor());
            } else {
                return Component.text(convertHealth(health))
                        .color(CC.RED.getTextColor());
            }
        }

        public static double convertHealth(double health) {
            double dividedHealth = health / 2;

            if (dividedHealth % 1 == 0) {
                return dividedHealth;
            }

            if (dividedHealth % .5 == 0) {
                return dividedHealth;
            }

            if (dividedHealth - ((int) dividedHealth) > .5) {
                return ((int) dividedHealth) + 1;
            } else if (dividedHealth - ((int) dividedHealth) > .25) {
                return ((int) dividedHealth) + .5;
            } else {
                return ((int) dividedHealth);
            }
        }

        public static TextComponent notificationMessage(String prefix, Component component) {
            return Component.text()
                    .append(
                            Component.text(prefix)
                                    .color(NamedTextColor.DARK_AQUA)
                                    .decoration(TextDecoration.BOLD, true)
                    )
                    .append(
                            Component.text(" » ")
                                    .color(NamedTextColor.DARK_GRAY)
                                    .decoration(TextDecoration.BOLD, true)
                    )
                    .append(component)
                    .build();
        }

        public static TextComponent healthToHearts(double health, double maxHealth, double heartScale, boolean displayAmount) {
            char heart = '❤';
            int allHearts = Math.floorDiv((int) maxHealth, (int) heartScale);
            int fullHearts = Math.floorDiv((int) health, (int) heartScale);
            boolean halfHeart = (maxHealth - health) % heartScale != 0;
            int emptyHearts = allHearts - fullHearts - (halfHeart ? 1 : 0);

            return Component.text()
                    .append(Component.text(StringUtils.repeat(heart, fullHearts)).color(NamedTextColor.DARK_RED))
                    .append(halfHeart ? Component.text(heart)
                            .color(NamedTextColor.RED) : Component.empty())
                    .append(Component.text(StringUtils.repeat(heart, emptyHearts))
                            .color(NamedTextColor.DARK_GRAY))
                    .append(displayAmount ? Component.text(" (" + (int) health + "/" + (int) maxHealth + ")")
                            .color(NamedTextColor.WHITE) : Component.empty())
                    .build();
        }

        // TODO: Convert to proper Components
//        public static TextComponent levelProgressMessage(int lvl, int nextLvl, long exp, long nextLevelExp, Color currentColor, Color nextColor, boolean displayTitle, boolean displayLevels, boolean displayUnder) {
//            net.md_5.bungee.api.chat.TextComponent message = new net.md_5.bungee.api.chat.TextComponent();
//
//            float progress = Math.min(exp / (float) nextLevelExp, 1);
//
//            Color endColor = CC.getColorBetween(currentColor, nextColor, progress);
//
//            net.md_5.bungee.api.chat.TextComponent component;
//            if(displayTitle) {
//                component = new net.md_5.bungee.api.chat.TextComponent("Level Progress\n");
//                component.setBold(true);
//                component.setColor(ChatColor.GOLD);
//                message.addExtra(AlignUtil.centerAlignMessage(component));
//            }
//
//            ComponentBuilder builder = new ComponentBuilder("█");
//
//            int amountOfSteps = 41;
//            int progressSteps = Math.min((int) Math.floor(amountOfSteps * progress) + 1, amountOfSteps); //TODO 0 is one line when should be none
//            int stepsLeft = amountOfSteps - progressSteps;
//
//            net.md_5.bungee.api.chat.TextComponent progressBar = CC.gradientComponent(
//                    ChatUtil.builderToComponent(
//                            builder.append(org.apache.commons.lang.StringUtils.repeat(" ", progressSteps)).strikethrough(true)), currentColor, endColor);
//
//            component = new net.md_5.bungee.api.chat.TextComponent(org.apache.commons.lang.StringUtils.repeat(" ", stepsLeft));
//            component.setStrikethrough(true);
//            component.setColor(ChatColor.DARK_GRAY);
//            progressBar.addExtra(component);
//
//            component = new net.md_5.bungee.api.chat.TextComponent("█");
//            component.setColor(progress < 1 ? ChatColor.of(new Color(64, 64, 64)) : ChatColor.of(endColor));
//            progressBar.addExtra(component);
//
//            if(displayLevels) {
//                component = ChatUtil.builderToComponent(
//                        new ComponentBuilder(lvl + " ")
//                                .append(progressBar)
//                                .append(" " + nextLvl + "\n").reset());
//            } else {
//                component = progressBar;
//            }
//
//            message.addExtra(AlignUtil.centerAlignMessage(component));
//
//            if(displayUnder) {
//                message.addExtra(AlignUtil.centerAlignMessage(
//                        progress < 1 ?
//                                new net.md_5.bungee.api.chat.TextComponent("(" + exp + "/" + nextLevelExp + ")\n") :
//                                ChatUtil.builderToComponent(new ComponentBuilder("Leveled Up!\n").bold(true).color(ChatColor.YELLOW))
//                ));
//            }
//
//            return message;
//        }
    }

    public static class Alignment {
        // TODO: Convert to proper Components
//    @Getter private static final int chatPixels = 320;
//    @Getter private static final int centerPixels = chatPixels / 2;
//
//    public static List<BaseComponent> equalizeLengthsReversed(List<List<BaseComponent>> lines) {
//        int amountOfGroups = lines.get(0).size();
//
//        List<Integer> biggestElementPixelsInGroup = new ArrayList<>();
//
//        for(int i = 0; i < amountOfGroups; i++) {
//            biggestElementPixelsInGroup.add(0);
//        }
//
//        for (List<BaseComponent> line : lines) {
//            for (int groupIndex = 0; groupIndex < amountOfGroups; groupIndex++) {
//                int possibleBiggestPixels = getComponentPixels(line.get(groupIndex));
//                if (possibleBiggestPixels > biggestElementPixelsInGroup.get(groupIndex)) {
//                    biggestElementPixelsInGroup.set(groupIndex, possibleBiggestPixels);
//                }
//            }
//        }
//
//        List<BaseComponent> alignedLines = new ArrayList<>();
//
//        for (List<BaseComponent> line : lines) {
//            TextComponent alignedLine = new TextComponent();
//            int prevGroupDifference = 0;
//            for (int groupIndex = 0; groupIndex < amountOfGroups; groupIndex++) {
//                TextComponent alignedElement = new TextComponent(getSpacePadding(Math.max(biggestElementPixelsInGroup.get(groupIndex) - getComponentPixels(line.get(groupIndex)) - prevGroupDifference, 0)));
//                alignedElement.addExtra(line.get(groupIndex));
//                alignedLine.addExtra(alignedElement);
//                prevGroupDifference = getComponentPixels(alignedElement) - biggestElementPixelsInGroup.get(groupIndex);
//            }
//            alignedLines.add(alignedLine);
//        }
//
//        return alignedLines;
//    }
//
//    public static TextComponent rightAlignMessage(BaseComponent message) {
//        int messagePxSize = getComponentPixels(message);
//
//        TextComponent spacePadding = getSpacePadding(chatPixels - messagePxSize - 3);
//
//        TextComponent centeredComponent = new TextComponent(spacePadding);
//        centeredComponent.addExtra(message);
//
//        return centeredComponent;
//    }
//
//    public static TextComponent rightAlignWithPadding(BaseComponent padding, BaseComponent line) {
//        int paddingPxSize = getComponentPixels(padding);
//        int linePxSize = getComponentPixels(line);
//
//        TextComponent spacePadding = getSpacePadding(chatPixels - paddingPxSize - linePxSize - 3);
//
//        TextComponent centeredComponent = new TextComponent(padding);
//        centeredComponent.addExtra(spacePadding);
//        centeredComponent.addExtra(line);
//
//        return centeredComponent;
//    }
//
//
//    public static TextComponent centerAlignMessage(BaseComponent message) {
//        int messagePxSize = getComponentPixels(message);
//
//        TextComponent spacePadding = getSpacePadding(centerPixels - (messagePxSize / 2));
//
//        TextComponent centeredComponent = new TextComponent(spacePadding);
//        centeredComponent.addExtra(message);
//        return centeredComponent;
//    }
//
//    public static List<BaseComponent> centerAlongFirst(List<BaseComponent> lines) {
//        int start = centerPixels - (getComponentPixels(lines.get(0)) / 2);
//
//        List<BaseComponent> alignedLines = new ArrayList<>();
//        for(BaseComponent line : lines) {
//            TextComponent spacePadding = getSpacePadding(start);
//
//            TextComponent alignedLine = new TextComponent(spacePadding);
//            alignedLine.addExtra(line);
//            alignedLine.addExtra("\n");
//            alignedLines.add(alignedLine);
//        }
//
//        return alignedLines;
//    }
//
//    public static List<BaseComponent> centerAlongBiggest(List<BaseComponent> lines) {
//        int biggestLinePixels = 0;
//        for(BaseComponent line : lines) {
//            int possibleBiggestPixels = getComponentPixels(line);
//            if(possibleBiggestPixels > biggestLinePixels) {
//                biggestLinePixels = possibleBiggestPixels;
//            }
//        }
//
//        int start = centerPixels - (biggestLinePixels / 2);
//
//        List<BaseComponent> alignedLines = new ArrayList<>();
//        for(BaseComponent line : lines) {
//            TextComponent spacePadding = getSpacePadding(start);
//
//            TextComponent alignedLine = new TextComponent(spacePadding);
//            alignedLine.addExtra(line);
//            alignedLine.addExtra("\n");
//            alignedLines.add(alignedLine);
//        }
//
//        return alignedLines;
//    }
//
//    public static List<BaseComponent> centerWithPaddingAlongBiggest(List<BaseComponent> paddings, List<BaseComponent> linesToCenter) {
//        int biggestPaddingPixels = 0;
//        for(BaseComponent padding : paddings) {
//            int possibleBiggestPixels = getComponentPixels(padding);
//            if(possibleBiggestPixels > biggestPaddingPixels) {
//                biggestPaddingPixels = possibleBiggestPixels;
//            }
//        }
//
//        int biggestToCenterPixels = 0;
//        for(BaseComponent line : linesToCenter) {
//            int possibleBiggestPixels = getComponentPixels(line);
//            if(possibleBiggestPixels > biggestToCenterPixels) {
//                biggestToCenterPixels = possibleBiggestPixels;
//            }
//        }
//
//        int center = biggestPaddingPixels + ((chatPixels - biggestPaddingPixels) / 2);
//
//        int start = center - (biggestToCenterPixels / 2);
//
//        List<BaseComponent> alignedLines = new ArrayList<>();
//        int numOfLines = Math.max(paddings.size(), linesToCenter.size());
//        for(int i = 0; i < numOfLines; i++) {
//            if(i < linesToCenter.size()) {
//                BaseComponent lineToCenter = linesToCenter.get(i);
//                if (lineToCenter != null) {
//                    BaseComponent padding = null;
//                    int paddingPxSize = 0;
//                    if (i < paddings.size()) {
//                        padding = paddings.get(i);
//                        if (padding != null) {
//                            paddingPxSize = getComponentPixels(padding);
//                        }
//                    }
//
//                    TextComponent spacePadding = getSpacePadding(start - paddingPxSize);
//
//                    TextComponent alignedLine;
//                    if(padding != null) {
//                        alignedLine = new TextComponent(padding);
//                        alignedLine.addExtra(spacePadding);
//                    } else {
//                        alignedLine = new TextComponent(spacePadding);
//                    }
//                    alignedLine.addExtra(lineToCenter);
//                    alignedLine.addExtra("\n");
//                    alignedLines.add(alignedLine);
//                }
//            }
//        }
//
//        return alignedLines;
//    }
//
//    public static int getComponentPixels(BaseComponent message) {
//        if(message == null) {
//            return 0;
//        }
//
//        int messagePxSize = 0;
//        if(message instanceof TextComponent) {
//            TextComponent textMessage = (TextComponent) message;
//            if (textMessage.getText() != null) {
//                String plainText = textMessage.getText();
//
//                boolean isBold = textMessage.isBold();
//
//                //System.out.println(plainText.toCharArray());
//                for (char c : plainText.toCharArray()) {
//                    DefaultFontInfo dFI = DefaultFontInfo.getDefaultFontInfo(c);
//                    messagePxSize += isBold ? dFI.getBoldLength() : dFI.getLength();
//                    messagePxSize++; //Include one pixel for background
//                }
//            }
//        }
//
//        if(message.getExtra() != null) {
//            for(BaseComponent extra : message.getExtra()) {
//                messagePxSize += getComponentPixels(extra);
//            }
//        }
//
//        return messagePxSize;
//    }
//
//    public static TextComponent getSpacePadding(int compensate) {
//        int spaceLength = DefaultFontInfo.SPACE.getLength() + 1;
//        int boldSpaces = compensate % spaceLength;
//        int spaces = (compensate / spaceLength) - boldSpaces;
//
//        return ChatUtil.builderToComponent(
//                new ComponentBuilder(StringUtils.repeat(" ", spaces))
//                        .append(StringUtils.repeat(" ", boldSpaces)).bold(true));
//    }
    }

}
