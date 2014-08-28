package org.blockserver.entity;

import java.util.Random;

public enum Dye{
	INK_SAC(0),
	ROSE_RED(1),
	CACTUS_GREEN(2),
	COCOA_BEANS(3),
	LAPIS_LAZULI(4),
	PURPLE_DYE(5),
	CYAN_DYE(6),
	LIGHT_GRAY_DYE(7),
	GRAY_DYE(8),
	PINK_DYE(9),
	LIME_DYE(10),
	DANDELION_YELLOW(11),
	LIGHT_BLUE_DYE(12),
	MAGENTA_DYE(13),
	ORANGE_DYE(14),
	BONE_MEAL(15);

	private int id;

	private Dye(int id){
		this.id = id;
	}

	public int getID(){
		return id;
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
