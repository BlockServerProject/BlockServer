package org.blockserver.entity;

import java.util.Random;

public enum Dye{
	
	INK_SAC,
	ROSE_RED,
	CACTUS_GREEN,
	COCOA_BEANS,
	LAPIS_LAZULI,
	PURPLE_DYE,
	CYAN_DYE,
	LIGHT_GRAY_DYE,
	GRAY_DYE,
	PINK_DYE,
	LIME_DYE,
	DANDELION_YELLOW,
	LIGHT_BLUE_DYE,
	MAGENTA_DYE,
	ORANGE_DYE,
	BONE_MEAL;

	public static Dye getRandomSheepDye(){
		return Dye.values()[new Random().nextInt(Dye.length)];
	}

}
