package com.customwrld.customlib.util;

import org.apache.commons.lang3.StringUtils;

public class ChatUtil {

//    public static TextComponent healthToHearts(double health, double maxHealth, double heartScale, boolean displayAmount) {
//        int allHearts = Math.floorDiv((int) maxHealth, (int) heartScale);
//        int fullHearts = Math.floorDiv((int) health, (int) heartScale);
//        boolean halfHeart = (maxHealth - health) % heartScale != 0;
//        int emptyHearts = allHearts - fullHearts - (halfHeart ? 1 : 0);
//
//        return Component.text(StringUtils.repeat("❤", fullHearts))
//                .color(NamedTextColor.DARK_RED)
//                .append(halfHeart ? Component.text("❤")
//                        .color(NamedTextColor.RED) : Component.empty())
//                .append(Component.text(StringUtils.repeat("❤", emptyHearts))
//                        .color(NamedTextColor.DARK_GRAY))
//                .append(displayAmount ? Component.text(" (" + (int) health + "/" + (int) maxHealth + ")")
//                        .color(NamedTextColor.WHITE) : Component.empty());
//    }

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
