package org.blockserver.level;

public enum BiomeType{
	OCEAN(0),
	PLAINS(1),
	DESERT(2),
	EXTREME_HILLS(3),
	FOREST(4),
	TAIGA(5),
	SWAMPLAND(6),
	RIVER(7),
	HELL(8),
	NETHER(8),
	THE_END(9),
	FROZEN_OCEAN(10),
	FROZEN_RIVER(11),
	ICE_PLANS(12),
	ICE_MOUNTAINS(13),
	MUSHROOM_ISLAND(14),
	MUSHROOM_ISLAND_SHORE(15),
	BEACH(16),
	DESERT_HILLS(17),
	FOREST_HILLS(18),
	TAIGA_HILLS(19),
	EXTREME_HILLS_EDGE(20),
	JUNGLE(21),
	JUNGLE_HILLS(22),
	JUNGLE_EDGE(23),
	DEEP_OCEAN(24),
	STONE_BEACH(25),
	COLD_BEACH(26),
	BIRCH_FOREST(27), // Watch out! Don't tap the button the right to "r"!
	BIRCH_FOREST_HILLS(28),
	ROOFED_FOREST(29),
	COLD_TAIGA(30),
	COLD_TAIGA_HILLS(31),
	MEGA_TAIGA(32),
	MEGA_TAIGA_HILLS(33),
	EXTREME_HILLS_PLUS(34),
	SAVANNA(35),
	SAVANNA_PLATEAU(36),
	MESA(37),
	MESA_PLATEAU_F(38),
	MESA_PLATEAU(39);
	private byte id;
	private BiomeType(int id){
		this.id = (byte) id;
	}
	public byte getID(){
		return id;
	}
	public boolean hasVar(){
		return false;
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
