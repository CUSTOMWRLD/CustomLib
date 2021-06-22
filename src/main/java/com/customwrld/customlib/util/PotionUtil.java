package com.customwrld.customlib.util;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.bukkit.potion.PotionEffectType;

@AllArgsConstructor
@Getter
public enum PotionUtil {

    SPEED(PotionEffectType.SPEED, "speed", "Swiftness"),
    SLOW(PotionEffectType.SLOW, "slowness", "Slowness"),
    FAST_DIGGING(PotionEffectType.FAST_DIGGING, "haste", "Haste"),
    SLOW_DIGGING(PotionEffectType.SLOW_DIGGING, "mining_fatigue", "Mining Fatigue"),
    INCREASE_DAMAGE(PotionEffectType.INCREASE_DAMAGE, "strength", "Strength"),
    HEAL(PotionEffectType.HEAL, "instant_health", "Healing"),
    HARM(PotionEffectType.HARM, "instant_damage", "Harming"),
    JUMP(PotionEffectType.JUMP, "jump_boost", "Leaping"),
    CONFUSION(PotionEffectType.CONFUSION, "nausea", "Nausea"),
    REGENERATION(PotionEffectType.REGENERATION, "regeneration", "Regeneration"),
    DAMAGE_RESISTANCE(PotionEffectType.DAMAGE_RESISTANCE, "resistance", "Resistance"),
    FIRE_RESISTANCE(PotionEffectType.FIRE_RESISTANCE, "fire_resistance", "Fire Resistance"),
    WATER_BREATHING(PotionEffectType.WATER_BREATHING, "water_breathing", "Water Breathing"),
    INVISIBILITY(PotionEffectType.INVISIBILITY, "invisibility", "Invisibility"),
    BLINDNESS(PotionEffectType.BLINDNESS, "blindness", "Blindness"),
    NIGHT_VISION(PotionEffectType.NIGHT_VISION, "night_vision", "Night Vision"),
    HUNGER(PotionEffectType.HUNGER, "hunger", "Hunger"),
    WEAKNESS(PotionEffectType.WEAKNESS, "weakness", "Weakness"),
    POISON(PotionEffectType.POISON, "poison", "Poison"),
    WITHER(PotionEffectType.WITHER, "wither", "Wither"),
    HEALTH_BOOST(PotionEffectType.HEALTH_BOOST, "health_boost", "Health Boost"),
    ABSORPTION(PotionEffectType.ABSORPTION, "absorption", "Absorption"),
    SATURATION(PotionEffectType.SATURATION, "saturation", "Saturation"),
    GLOWING(PotionEffectType.GLOWING, "glowing", "Glowing"),
    LEVITATION(PotionEffectType.LEVITATION, "levitation", "Levitation"),
    LUCK(PotionEffectType.LUCK, "luck", "Luck"),
    UNLUCK(PotionEffectType.UNLUCK, "unluck", "Bad Luck"),
    SLOW_FALLING(PotionEffectType.SLOW_FALLING, "slow_falling", "Slow Falling"),
    CONDUIT_POWER(PotionEffectType.CONDUIT_POWER, "conduit_power", "Conduit Power"),
    DOLPHINS_GRACE(PotionEffectType.DOLPHINS_GRACE, "dolphins_grace", "Dolphins Grace"),
    BAD_OMEN(PotionEffectType.BAD_OMEN, "bad_omen", "Bad Omen"),
    HERO_OF_THE_VILLAGE(PotionEffectType.HERO_OF_THE_VILLAGE, "hero_of_the_village", "Hero of The Village");

    private final PotionEffectType type;
    private final String name;
    private final String display;

}
