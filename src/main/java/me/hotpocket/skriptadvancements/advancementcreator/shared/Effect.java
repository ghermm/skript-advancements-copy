package me.hotpocket.skriptadvancements.advancementcreator.shared;

import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

/**
 * Specifies a status effect.
 * @see EffectObject
 */
public enum Effect implements SharedEnum {
	SPEED,
	SLOWNESS,
	HASTE,
	MINING_FATIGUE,
	STRENGTH,
	INSTANT_HEALTH,
	INSTANT_DAMAGE,
	JUMP_BOOST,
	NAUSEA,
	REGENERATION,
	RESISTANCE,
	FIRE_RESISTANCE,
	WATER_BREATHING,
	INVISIBILITY,
	BLINDNESS,
	NIGHT_VISION,
	HUNGER,
	WEAKNESS,
	POISON,
	WITHER,
	HEALTH_BOOST,
	ABSORPTION,
	SATURATION,
	GLOWING,
	LEVITATION,
	LUCK,
	UNLUCK,
	SLOW_FALLING,
	CONDUIT_POWER,
	DOLPHINS_GRACE,
	BAD_OMEN,
	HERO_OF_THE_VILLAGE;
	
	@NotNull
	@Contract(pure = true)
	@Override
	public String getValue() {
		return "minecraft:" + name().toLowerCase();
	}
}
