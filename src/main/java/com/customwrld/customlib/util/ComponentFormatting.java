package com.customwrld.customlib.util;

import lombok.AllArgsConstructor;
import lombok.Builder;
import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.chat.BaseComponent;
import net.md_5.bungee.api.chat.ComponentBuilder;

@Builder
@AllArgsConstructor
public class ComponentFormatting {
    public static final ComponentFormatting NONE = new ComponentFormatting(ChatColor.WHITE, null, false, false, false, false, false);

    private ChatColor color;
    private String font;
    private boolean bold;
    private boolean italic;
    private boolean strikethrough;
    private boolean underlined;
    private boolean obfuscated;

    public ComponentFormatting(ComponentBuilder builder) {
        BaseComponent current = builder.getCurrentComponent();
        if(current.hasFormatting()) {
            color = current.getColor();
            font = current.getFont();
            bold = current.isBold();
            italic = current.isItalic();
            underlined = current.isUnderlined();
            strikethrough = current.isStrikethrough();
            obfuscated = current.isObfuscated();
        }
    }

    public ComponentFormatting(BaseComponent component) {
        if(component.hasFormatting()) {
            color = component.getColor();
            font = component.getFont();
            bold = component.isBold();
            italic = component.isItalic();
            underlined = component.isUnderlined();
            strikethrough = component.isStrikethrough();
            obfuscated = component.isObfuscated();
        }
    }

    public ComponentBuilder apply(ComponentBuilder builder) {
        builder.color(color);
        builder.font(font);
        builder.bold(bold);
        builder.italic(italic);
        builder.underlined(underlined);
        builder.strikethrough(strikethrough);
        return builder;
    }

    public BaseComponent apply(BaseComponent component) {
        component.setColor(color);
        component.setFont(font);
        component.setBold(bold);
        component.setItalic(italic);
        component.setUnderlined(underlined);
        component.setStrikethrough(strikethrough);
        return component;
    }

    public static ComponentBuilder directCopy(ComponentBuilder builder, BaseComponent component) {
        builder.color(component.getColor());
        builder.font(component.getFont());
        builder.bold(component.isBold());
        builder.italic(component.isItalic());
        builder.underlined(component.isUnderlined());
        builder.strikethrough(component.isStrikethrough());
        return builder;
    }

    public static BaseComponent directCopy(BaseComponent component, ComponentBuilder builder) {
        BaseComponent current = builder.getCurrentComponent();
        component.setColor(current.getColor());
        component.setFont(current.getFont());
        component.setBold(current.isBold());
        component.setItalic(current.isItalic());
        component.setUnderlined(current.isUnderlined());
        component.setStrikethrough(current.isStrikethrough());
        return component;
    }

    public static BaseComponent directCopy(BaseComponent target, BaseComponent from) {
        target.setColor(from.getColor());
        target.setFont(from.getFont());
        target.setBold(from.isBold());
        target.setItalic(from.isItalic());
        target.setUnderlined(from.isUnderlined());
        target.setStrikethrough(from.isStrikethrough());
        return target;
    }
}
