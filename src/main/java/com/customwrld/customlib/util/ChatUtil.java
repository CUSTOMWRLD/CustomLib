package com.customwrld.customlib.util;

import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.chat.BaseComponent;
import net.md_5.bungee.api.chat.ComponentBuilder;
import net.md_5.bungee.api.chat.TextComponent;
import org.apache.commons.lang.StringUtils;

import java.awt.*;
import java.util.List;

public class ChatUtil {

    public static final TextComponent SPACE = new TextComponent(" ");
    public static final TextComponent TAB = new TextComponent("    ");

    public static final TextComponent SEPERATOR = ChatUtil.builderToComponent(
            new ComponentBuilder(StringUtils.repeat(" ", 80))
                    .color(ChatColor.DARK_GRAY)
                    .strikethrough(true));

    public static void appendMultiple(ComponentBuilder builder, List<? extends BaseComponent> toAppend) {
        for(BaseComponent component : toAppend) {
            builder.append(component);
        }
    }

    public static void addExtras(BaseComponent base, List<? extends BaseComponent> extras) {
        for(BaseComponent extra : extras) {
            base.addExtra(extra);
        }
    }

    public static TextComponent builderToComponent(ComponentBuilder builder) {
        BaseComponent[] components = builder.create();

        BaseComponent base = components[0];
        TextComponent component;
        if(base instanceof TextComponent) {
            component = new TextComponent((TextComponent) base);
        } else {
            component = new TextComponent(base);
        }

        for(int i = 1; i < components.length ; i++) {
            component.addExtra(components[i]);
        }

        return component;
    }

    public static TextComponent healthToHearts(double health, double maxHealth, double heartScale, boolean displayAmount) {
        int allHearts = Math.floorDiv((int) maxHealth, (int) heartScale);
        int fullHearts = Math.floorDiv((int) health, (int) heartScale);
        boolean halfHeart = (maxHealth - health) % heartScale != 0;
        int emptyHearts = allHearts - fullHearts - (halfHeart ? 1 : 0);

        ComponentBuilder builder = new ComponentBuilder()
                .append(StringUtils.repeat("❤", fullHearts)).color(ChatColor.DARK_RED);

        if(halfHeart) {
            builder.append("❤").color(ChatColor.RED);
        }

        builder.append(StringUtils.repeat("❤", emptyHearts)).color(ChatColor.DARK_GRAY);

        if(displayAmount) {
            builder.append(" (" + (int) health + "/" + (int) maxHealth + ")").color(ChatColor.WHITE);
        }

        return builderToComponent(builder);
    }

    public static TextComponent levelProgressMessage(int lvl, int nextLvl, long exp, long nextLevelExp, Color currentColor, Color nextColor, boolean displayTitle, boolean displayLevels, boolean displayUnder) {
        TextComponent message = new TextComponent();

        float progress = Math.min(exp / (float) nextLevelExp, 1);

        Color endColor = CC.getColorBetween(currentColor, nextColor, progress);

        TextComponent component;
        if(displayTitle) {
            component = new TextComponent("Level Progress\n");
            component.setBold(true);
            component.setColor(ChatColor.GOLD);
            message.addExtra(AlignUtil.centerAlignMessage(component));
        }

        ComponentBuilder builder = new ComponentBuilder("█");

        int amountOfSteps = 41;
        int progressSteps = Math.min((int) Math.floor(amountOfSteps * progress) + 1, amountOfSteps); //TODO 0 is one line when should be none
        int stepsLeft = amountOfSteps - progressSteps;

        TextComponent progressBar = CC.gradientComponent(
                ChatUtil.builderToComponent(
                        builder.append(StringUtils.repeat(" ", progressSteps)).strikethrough(true)), currentColor, endColor);

        component = new TextComponent(StringUtils.repeat(" ", stepsLeft));
        component.setStrikethrough(true);
        component.setColor(ChatColor.DARK_GRAY);
        progressBar.addExtra(component);

        component = new TextComponent("█");
        component.setColor(progress < 1 ? ChatColor.of(new Color(64, 64, 64)) : ChatColor.of(endColor));
        progressBar.addExtra(component);

        if(displayLevels) {
            component = ChatUtil.builderToComponent(
                    new ComponentBuilder(lvl + " ")
                            .append(progressBar)
                            .append(" " + nextLvl + "\n").reset());
        } else {
            component = progressBar;
        }

        message.addExtra(AlignUtil.centerAlignMessage(component));

        if(displayUnder) {
            message.addExtra(AlignUtil.centerAlignMessage(
                    progress < 1 ?
                            new TextComponent("(" + exp + "/" + nextLevelExp + ")\n") :
                            ChatUtil.builderToComponent(new ComponentBuilder("Leveled Up!\n").bold(true).color(ChatColor.YELLOW))
            ));
        }

        return message;
    }
}
