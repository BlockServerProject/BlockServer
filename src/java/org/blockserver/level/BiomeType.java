package org.blockserver.level;

/**
 * Source: https://github.com/erich666/Mineways/blob/master/Win/biomes.h and
 * https://github.com/erich666/Mineways/blob/master/Win/biomes.cpp
 */
public enum BiomeType{
	OCEAN("Ocean", 0, 0.5f, 0.5f, 0x92BD59, 0x77AB2F),
	PLAINS("Plains", 1, 0.8f, 0.4f, 0x92BD59, 0x77AB2F),
	DESERT("Desert", 2, 2.0f, 0.0f, 0x92BD59, 0x77AB2F),
	EXTREME_HILLS("Extreme Hills", 3, 0.2f, 0.3f, 0x92BD59, 0x77AB2F),
	FOREST("Forest", 4, 0.7f, 0.8f, 0x92BD59, 0x77AB2F),
	TAIGA("Taiga", 5, 0.05f, 0.8f, 0x92BD59, 0x77AB2F),
	SWAMPLAND("Swampland", 6, 0.8f, 0.9f, 0x92BD59, 0x77AB2F),
	RIVER("River", 7, 0.5f, 0.5f, 0x92BD59, 0x77AB2F),
	NETHER("Nether", 8, 2.0f, 0.0f, 0x92BD59, 0x77AB2F),
	END("End", 9, 0.5f, 0.5f, 0x92BD59, 0x77AB2F),
	FROZEN_OCEAN("Frozen Ocean", 10, 0.0f, 0.5f, 0x92BD59, 0x77AB2F),
	FROZEN_RIVER("Frozen River", 11, 0.0f, 0.5f, 0x92BD59, 0x77AB2F),
	ICE_PLAINS("Ice Plains", 12, 0.0f, 0.5f, 0x92BD59, 0x77AB2F),
	ICE_MOUNTAINS("Ice Mountains", 13, 0.0f, 0.5f, 0x92BD59, 0x77AB2F),
	MUSHROOM_ISLAND("Mushroom Island", 14, 0.9f, 1.0f, 0x92BD59, 0x77AB2F),
	MUSHROOM_ISLAND_SHORE("Mushroom Island Shore", 15, 0.9f, 1.0f, 0x92BD59, 0x77AB2F),
	BEACH("Beach", 16, 0.8f, 0.4f, 0x92BD59, 0x77AB2F),
	DESERT_HILLS("Desert Hills", 17, 2.0f, 0.0f, 0x92BD59, 0x77AB2F),
	FOREST_HILLS("Forest Hills", 18, 0.7f, 0.8f, 0x92BD59, 0x77AB2F),
	TAIGA_HILLS("Taiga Hills", 19, 0.2f, 0.7f, 0x92BD59, 0x77AB2F),
	EXTREME_HILLS_EDGE("Extreme Hills Edge", 20, 0.2f, 0.3f, 0x92BD59, 0x77AB2F),
	JUNGLE("Jungle", 21, 1.2f, 0.9f, 0x92BD59, 0x77AB2F),
	JUNGLE_HILLS("Jungle Hills", 22, 1.2f, 0.9f, 0x92BD59, 0x77AB2F),
	JUNGLE_EDGE("Jungle Edge", 23, 0.95f, 0.8f, 0x92BD59, 0x77AB2F),
	DEEP_OCEAN("Deep Ocean", 24, 0.5f, 0.5f, 0x92BD59, 0x77AB2F),
	STONE_BEACH("Stone Beach", 25, 0.2f, 0.3f, 0x92BD59, 0x77AB2F),
	COLD_BEACH("Cold Beach", 26, 0.05f, 0.3f, 0x92BD59, 0x77AB2F),
	BIRCH_FOREST("Birch Forest", 27, 0.6f, 0.6f, 0x92BD59, 0x77AB2F),
 	BIRCH_FOREST_HILLS("Birch Forest Hills", 28, 0.6f, 0.6f, 0x92BD59, 0x77AB2F),
	ROOFED_FOREST("Roofed Forest", 29, 0.7f, 0.8f, 0x92BD59, 0x77AB2F),
	COLD_TAIGA("Cold Taiga", 30, -0.5f, 0.4f, 0x92BD59, 0x77AB2F),
	COLD_TAIGA_HILLS("Cold Taiga Hills", 31, -0.5f, 0.4f, 0x92BD59, 0x77AB2F),
	MEGA_TAIGA("Mega Taiga", 32, 0.3f, 0.8f, 0x92BD59, 0x77AB2F),
	MEGA_TAIGA_HILLS("Mega Taiga Hills", 33, 0.3f, 0.8f, 0x92BD59, 0x77AB2F),
	EXTREME_HILLS_PLUS("Extreme Hills+", 34, 0.2f, 0.3f, 0x92BD59, 0x77AB2F),
	SAVANNA("Savanna", 35, 1.2f, 0.0f, 0x92BD59, 0x77AB2F),
	SAVANNA_PLATEAU("Savanna Plateau", 36, 1.0f, 0.0f, 0x92BD59, 0x77AB2F),
	MESA("Mesa", 37, 2.0f, 0.0f, 0x92BD59, 0x77AB2F),
	MESA_PLATEAU_F("Mesa Plateau F", 38, 2.0f, 0.0f, 0x92BD59, 0x77AB2F),
	MESA_PLATEAU("Mesa Plateau", 39, 2.0f, 0.0f, 0x92BD59, 0x77AB2F),
	SUNFLOWER_PLAINS("Sunflower Plains", 129, 0.8f, 0.4f, 0x92BD59, 0x77AB2F),
	DESERT_M("Desert M", 130, 2.0f, 0.0f, 0x92BD59, 0x77AB2F),
	EXTREME_HILLS_M("Extreme Hills M", 131, 0.2f, 0.3f, 0x92BD59, 0x77AB2F),
	FLOWER_FOREST("Flower Forest", 132, 0.7f, 0.8f, 0x92BD59, 0x77AB2F),
	TAIGA_M("Taiga M", 133, 0.05f, 0.8f, 0x92BD59, 0x77AB2F),
	SWAMPLAND_M("Swampland M", 134, 0.8f, 0.9f, 0x92BD59, 0x77AB2F),
	ICE_PLAINS_SPIKES("Ice Plains Spikes", 140, 0.0f, 0.5f, 0x92BD59, 0x77AB2F),
	JUNGLE_M("Jungle M", 149, 1.2f, 0.9f, 0x92BD59, 0x77AB2F),
	JUNGLEEDGE_M("JungleEdge M", 151, 0.95f, 0.8f, 0x92BD59, 0x77AB2F),
	BIRCH_FOREST_HILLS_M("Birch Forest Hills M", 156, 0.6f, 0.6f, 0x92BD59, 0x77AB2F),
	ROOFED_FOREST_M("Roofed Forest M", 157, 0.7f, 0.8f, 0x92BD59, 0x77AB2F),
	COLD_TAIGA("Cold Taiga M", 158, -0.5f, 0.4f, 0x92BD59, 0x77AB2F),
	MEGA_SPRUCE_TAIGA("Mega Spruce Taiga", 160, 0.25f, 0.8f, 0x92BD59, 0x77AB2F),	// special exception look down
	MEGA_SPRUCE_TAIGA_HILLS("Mega Spruce Taiga Hills", 161, 0.3f, 0.8f, 0x92BD59, 0x77AB2F),
	//    { /* 162 */ "Extreme Hills+ M",			0.2f, 0.3f, 0x92BD59, 0x77AB2F },
	//    { /* 163 */ "Savanna M",				1.2f, 0.0f, 0x92BD59, 0x77AB2F },
	//    { /* 164 */ "Savanna Plateau M",		1.0f, 0.0f, 0x92BD59, 0x77AB2F },
	//    { /* 165 */ "Mesa (Bryce)",				2.0f, 0.0f, 0x92BD59, 0x77AB2F },
	//    { /* 166 */ "Mesa Plateau F M",			2.0f, 0.0f, 0x92BD59, 0x77AB2F },
	//    { /* 167 */ "Mesa Plateau M",			2.0f, 0.0f, 0x92BD59, 0x77AB2F },
	;
	private byte id;
	private String name;
	private final float temperature, rainfall;
	private final int grass, foliage;
	BiomeType(String name, int id, float temperature, float rainfall, int grass, int foliage){
		this.name = name;
		this.id = (byte) id;
		this.temperature = temperature;
		this.rainfall = rainfall;
		this.grass = grass;
		this.foliage = foliage;
	}
	public String getName(){
		return name;
	}
	public byte getID(){
		return id;
	}
	public float getTemperature(){
		return temperature;
	}
	public float getRainfall(){
		return rainfall;
	}
	public int getGrass(){
		return grass;
	}
	public int getFoliage(){
		return foliage;
	}
	public static BiomeType get(int in){
		byte id = (byte) (in & 0x7F);
		for(BiomeType t: values()){
			if(t.getID() == id){
				return t;
			}
		}
		return null;
	}
}
