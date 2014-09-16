package org.blockserver.entity;

import java.util.Random;

import org.blockserver.utility.Utils;

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

	public static Dye getRandomDye(){
		return Utils.arrayRandom(values());
	}
	public static Dye getRandomDye(Random random){
		return Utils.arrayRandom(values(), random);
	}

	public static Dye getRandomSheepDye(){
		return getRandomSheepDye(new Random());
	}
	public static Dye getRandomSheepDye(Random random){
		double d = random.nextDouble();
		d *= 100d;
		if(d < 81.836d){
			return BONE_MEAL;
		}
		d -= 81.836d;
		if(d < 7.5d){
			return INK_SAC;
		}
		d -= 7.5d;
		if(d < 7.5d){
			return GRAY_DYE;
		}
		d -= 7.5d;
		if(d < 3d){
			return COCOA_BEANS;
		}
		return PINK_DYE;
	}
}
