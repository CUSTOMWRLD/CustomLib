package com.customwrld.customlib.pigeon.converters;

import com.customwrld.pigeon.Converter;
import com.google.gson.JsonElement;
import com.google.gson.JsonPrimitive;
import net.md_5.bungee.api.ChatColor;

import java.lang.reflect.Type;

public class ChatColorConverter extends Converter<ChatColor> {

    public ChatColorConverter() { super(ChatColor.class, true, false); }

    @Override
    public ChatColor convertToValue(Type fieldType, JsonElement element) {
        return Utils.convertToValue(element);
    }

    @Override
    public JsonElement convertToSimple(Type fieldType, ChatColor value) {
        return Utils.convertToSimple(value);
    }

    public static class Utils {
        public static ChatColor convertToValue(JsonElement element) {
            return ChatColor.of(element.getAsString());
        }

        public static JsonElement convertToSimple(ChatColor value) {
            return new JsonPrimitive(value.toString());
        }
    }

}
