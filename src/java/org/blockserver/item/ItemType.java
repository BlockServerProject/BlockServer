package org.blockserver.item;

import java.util.ArrayList;
import java.util.List;

public enum ItemType{
	IRON_SHOVEL(256, "Iron shovel"),
	IRON_PICKAXE(257, "Iron pickaxe"),
	IRON_AXE(258, "Iron axe"),
	FLINT_AND_STEEL(259, "Flint and Steel"),
	APPLE(260, 64, "Apple"),
	BOW(261, "Bow"),
	ARROW(262, 64, "Arrow"),
	ARROWS(262, 64, "Arrow"),
	COAL(263, 1, 64, "Coal"),
	CHARCOAL(263, 1, 64, "Charcoal"),
	DIAMOND(264, 64, "Diamond"),
	IRON_INGOT(265, 64, "Iron Ingot"),
	IRON(265, 64, "Iron Ingot"),
	GOLD_INGOT(266, 64, "Gold Ingot"),
	GOLD(266, 64, "Gold Ingot"),
	IRON_SWORD(267, "Iron Sword"),
	WOODEN_SWORD(268, "Wooden Sword"),
	WOOD_SWORD(268, "Wooden Sword"),
	WOODEN_SHOVEL(269, "Wooden Shovel"),
	WOOD_SHOVEL(269, "Wooden Shovel"),
	WOODEN_PICKAXE(270, "Wooden Pickaxe"),
	WOOD_PICKAXE(270, "Wooden Pickaxe"),
	WOODEN_AXE(271, "Wooden Axe"),
	WOOD_AXE(271, "Wooden Axe"),
	STONE_SWORD(272, "Stone Sword"),
	STONE_SHOVEL(273, "Stone Shovel"),
	STONE_PICKAXE(274, "Stone Pickaxe"),
	STONE_AXE(275, "Stone Axe"),
	DIAMOND_SWORD(276, "Diamond Sword"),
	DIAMOND_SHOVEL(277, "Diamond Shovel"),
	DIAMOND_PICKAXE(278, "Diamond Pickaxe"),
	DIAMOND_AXE(279, "Diamond Axe"),
	STICK(280, 64, "Stick"),
	BOWL(281, 64, "Bowl"),
	MUSHROOM_STEW(282, "Mushroom Stew"),
	MUSHROOM_SOUP(282, "Mushroom Stew"), // which one is the official name?
	GOLD_SWORD(283, "Gold Sword"),
	GOLDEN_SWORD(283, "Gold Sword"),
	GOLD_SHOVEL(284, "Gold Shovel"),
	GOLD_PICKAXE(285, "Gold Pickaxe"),
	GOLD_AXE(286, "Gold Pickaxe"),
	STRING(287, 64, "String"),
	STRINGS(287, 64, "String"),
	FEATHER(288, 64, "Feather"),
	FEATHERS(288, 64, "Feather"),
	GUNPOWDER(289, 64, "Gunpowder"),
	WOODEN_HOE(290, "Wooden Hoe"),
	WOOD_HOE(290, "Wooden Hoe"),
	STONE_HOE(291, "Stone Hoe"),
	IRON_HOE(292, "Iron Hoe"),
	DIAMOND_HOE(293, "Diamond Hoe"),
	GOLD_HOE(294, "Golden Hoe"),
	WHEAT_SEED(295, 64, "Seed"),
	WHEAT_SEEDS(295, 64, "Seed"),
	WHEAT(296, 64, "Wheat Seed"),
	BREAD(297, 64, "Bread"),
	LEATHER_CAP(298, "Leather Cap"),
	LEATHER_HELMET(298, "Leather Cap"),
	CAP(298, "Leather Cap"),
	LEATHER_TUNIC(299, "Leather Tunic"),
	LEATHER_CHESTPLATE(299, "Leather Tunic"),
	TUNIC(299, "Leather Tunic"),
	LEATHER_PANTS(300, "Leather Pants"),
	LEATHER_LEGGINGS(300, "Leather Pants"),
	PANTS(300, "Leahter Pants"),
	LEATHER_BOOTS(301, "Leather Boots"),
	LEATHER_SHOES(301, "Leather Boots"),
	SHOES(301, "Leather Boots"),
	CHAIN_HELMET(302, "Chain Helmet"),
	CHAIN_CHESTPLATE(303, "Chain Helmet"),
	CHAIN_LEGGINGS(304, "Chain Helmet"),
	CHAIN_BOOTS(305, "Chain Boots"),
	IRON_HELMET(306, "Iron Helmet"),
	IRON_CHESTPLATE(307, "Iron Chestplate"),
	IRON_LEGGINGS(308, "Iron Leggings"),
	IRON_BOOTS(309, "Iron Boots"),
	DIAMOND_HELMET(310, "Diamond Helmet"),
	DIAMOND_CHESTPLATE(311, "Diamond Chestplate"),
	DIAMOND_LEGGINGS(312, "Diamond Leggings"),
	DIAMOND_BOOTS(313, "Diamond Boots"),
	GOLDEN_HELMET(314, "Golden Helmet"),
	GOLD_HELMET(314, "Golden Helmet"),
	GOLD_CHESTPLATE(315, "Golden Chestplate"),
	GOLDEN_CHESTPLATE(315, "Golden Chestplate"),
	GOLD_LEGGINGS(316, "Golden Leggings"),
	GOLDEN_LEGGINGS(316, "Golden Leggings"),
	GOLD_BOOTS(317, "Golden Boots"),
	GOLDEN_BOOTS(317, "Golden Boots"),
	FLINT(318, 64, "Flint"),
	RAW_PORKCHOP(319, 64, "Raw Porkchop"),
	RAW_PORK(319, 64, "Raw Porkchop"),
	COOKED_PORKCHOP(320, 64, "Cooked Porkchop"),
	COOK_PORKCHOP(320, 64, "Cooked Porkchop"),
	PAINTING(321, "Painting"),
	PAINT(321, "Painting"),
	PAINTINGS(321, "Painting"),
	SIGN(323, 64, "Sign"),
	SIGNS(323, 64, "Sign"),
	BUCKET(325, 0, 64, "Bucket"),
	BUCKETS(325, 0, 64, "Bucket"),
	MILK_BUCKET(325, 1, 1, "Milk Bucket"),
	WATER_BUCKET(325, 8, 1, "Water Bucket"),
	LAVA_BUCKET(325, 10, 1, "Lava Bucket"),
	MINECART(328, "Minecart"),
	MINECARTS(328, "Minecart"),
	MINE_CART(328, "Minecart"),
	SADDLE(329, "Saddle"),
	REDSTONE(331, 64, "Redstone Dust"),
	REDSTONE_DUST(331, 64, "Redstone Dust"),
	SNOWBALL(332, 16, "Snowball"),
	SNOWBALLS(332, 16, "Snowball"),
	LEATHER(334, 64, "Leather"),
	CLAY_BRICK(336, 64, "Clay Brick"),
	BRICK(336, 64, "Clay Brick"),
	CLAY(337, 64, "Clay"),
	SUGAR_CANE(338, 64, "Sugar Cane"),
	REED(338, "Sugar Cane"),
	REEDS(338, "Sugar Cane"),
	PAPER(339, 64, "Paper"),
	PAPERS(339, "Paper"),
	BOOK(340, 64, "Book"),
	BOOKS(340, 64, "Book"),
	SLIMEBALL(341, 64, "Slimeball"),
	SLIME_BALL(341, 64, "Slimeball"),
	SLIME_BALLS(341, 64, "Slimeball"),
	SLIMEBALLS(341, 64, "Slimeball"),
	EGG(344, 16, "Egg"),
	EGGS(344, 16, "Egg"),
	COMPASS(345, "Compass"),
	CLOCK(347, "Clock"),
	GLOWSTONE_DUST(348, 64, "Glowstone Dust"),
	INK_SAC(351, 0, 64, "Ink Sac"),
	INK_SACK(351, 0, 64, "Ink Sac"),
	INK_SACKS(351, 0, 64, "Ink Sac"),
	BLACK_DYE(351, 0, 64, "Ink Sac"),
	ROSE_RED(351, 1, 64, "Rose Red"),
	RED_DYE(351, 1, 64, "Rose Red"),
	CACTUS_GREEN(351, 2, 64, "Cactus Green"),
	GREEN_DYE(351, 2, 64, "Cactus Green"),
	COCOA_BEANS(351, 3, 64, "Cocoa Beans"),
	BROWN_DYE(351, 3, 64, "Cocoa Beans"),
	LAPIS_LAZULI(351, 4, 64, "Lapis Lazuli"),
	BLUE_DYE(351, 4, 64, "Lapis Lazuli"),
	PURPLE_DYE(351, 5, 64, "Purple Dye"),
	CYAN_DYE(351, 6, 64, "Cyan Dye"),
	LIGHT_GRAY_DYE(351, 7, 64, "Light Gray Dye"),
	GRAY_DYE(351, 8, 64, "Gray Dye"),
	PINK_DYE(351, 9, 64, "Pink Dye"),
	LIME_DYE(351, 10, 64, "Lime Dye"),
	DANDELION_YELLOW(351, 11, 64, "Dandelion Yellow"),
	DANDELION_YELLOW_DYE(351, 11, 64, "Dandelion Yellow"),
	YELLOW_DYE(351, 11, 64, "Dandelion Yellow"),
	LIGHT_BLUE_DYE(351, 12, 64, "Light Blue Dye"),
	MAGENTA_DYE(351, 13, 64, "Magenta Dye"),
	ORANGE_DYE(351, 14, 64, "Orange Dye"),
	BONE_MEAL(351, 15, 64, "Bone Meal"),
	BONE_MEAL_DYE(351, 15, 64, "Bone Meal"),
	WHITE_DYE(351, 15, 64, "Bone Meal"),
	BONE(352, 64, "Bone"),
	BONES(352, 64, "Bone"),
	SUGAR(353, 64, "Sugar"),
	CAKE(354, "Cake"),
	BED(355, "Bed"),
	COOKIE(357, 64, "Cookie"),
	COOKIES(357, 64, "Cookie"),
	SHEARS(359, "Shears"),
	MELON_SLICE(360, 64, "Melon Slice"),
	PUMPKIN_SEEDS(361, 64, "Pumpkin Seeds"),
	MELON_SEEDS(362, 64, "Melon Seeds"),
	RAW_BEEF(363, 64, "Raw Beef"),
	RAW_STEAK(363, 64, "Raw Beef"),
	STEAK(364, 64, "Steak"),
	COOKED_STEAK(364, 64, "Steak"),
	RAW_CHICKEN(365, 64, "Raw Chicken"),
	COOKED_CHICKEN(366, "Cooked Chicken"),
	SPAWN_CHICKEN(383, 10, 64, "Spawn Chicken"),
	CHICKEN_SPAWNEGG(383, 10, 64, "Spawn Chicken"),
	SPAWN_COW(383, 11, 64, "Spawn Cow"),
	COW_SPAWNEGG(383, 11, 64, "Spawn Cow"),
	SPAWN_PIG(383, 12, 64, "Spawn Pig"),
	PIG_SPAWNEGG(383, 12, 64, "Spawn Pig"),
	SPAWN_SHEEP(383, 13, 64, "Spawn Sheep"),
	SHEEP_SPAWNEGG(383, 13, 64, "Spawn Sheep"),
	SPAWN_WOLF(383, 14, 64, "Spawn Wolf"),
	WOLF_SPAWNEGG(383, 14, 64, "Spawn Wolf"),
	SPAWN_VILLAGER(383, 15, 64, "Spawn Villager"),
	VILLAGER_SPAWNEGG(383, 15, 64, "Spawn Villager"),
	SPAWN_MOOSHROOM(383, 16, 64, "Spawn Mooshroom"),
	MOOSHROOM_SPAWNEGG(383, 16, 64, "Spawn Mooshroom"),
	SPAWN_ZOMBIE(383, 32, 64, "Spawn Zombie"),
	ZOMBIE_SPAWNEGG(383, 32, 64, "Spawn Zombie"),
	SPAWN_CREEPER(383, 33, 64, "Spawn Creeper"),
	CREEPER_SPAWNEGG(383, 33, 64, "Spawn Creeper"),
	SPAWN_SKELETON(383, 34, 64, "Spawn Skeleton"),
	SKELETON_SPAWNEGG(383, 34, 64, "Spawn Skeleton"),
	SPAWN_SPIDER(383, 35, 64, "Spawn Spider"),
	SPIDER_SPAWNEGG(383, 35, 64, "Spawn Spider"),
	SPAWN_ZOMBIE_PIGMAN(383, 36, 64, "Spawn Zombie Pigman"),
	ZOMBIE_PIGMAN_SPAWNEGG(383, 36, 64, "Spawn Zombie Pigman"),
	SPAWN_SLIME(383, 37, 64, "Spawn Slime"),
	SLIME_SPAWNEGG(383, 37, 64, "Spawn Slime"),
	SPAWN_ENDERMAN(383, 38, 64, "Spawn Enderman"),
	ENDERMAN_SPAWNEGG(383, 38, 64, "Spawn Enderman"),
	SPAWN_SILVERFISH(383, 39, 64, "Spawn Silverfish"),
	SILVERFISH_SPAWNEGG(383, 39, 64, "Spawn Silverfish"),
	EMERALD(388, 64, "Emerald"),
	CARROT(391, 64, "Carrot"),
	CARROTS(391, 64, "Carrot"),
	POTATO(392, 64, "Potato"),
	POTATOS(392, 64, "Potato"),
	POTATOES(392, 64, "Potato"),
	BAKED_POTATO(393, 64, "Baked Potato"),
	PUMPKIN_PIE(400, 64, "Pumpkin Pie"),
	NETHER_BRICK(405, 64, "Nether Brick"),
	NETHER_BRICKS(405, 64, "Nether Brick"),
	NETHER_QUARTZ(406, 64, "Nether Quartz"),
	BEETROOT(457, 64, "Beetroot"),
	BEETROOTS(457, 64, "Beetroot"),
	BEETROOT_SEEDS(458, 64, "Beetroot Seeds"),
	BEETROOT_SOUP(459, "Beetroot Soup"),
	GENERIC(512, 64, "Unknown");
	
	public final static int DAMAGE_NEGLIGIBLE = 16;
	private int id;
	private int damage;
	private String itemName;
	private int maxCount;

	private ItemType(int id){
		this(id, DAMAGE_NEGLIGIBLE);
	}
	private ItemType(int id, String name){
		this(id, DAMAGE_NEGLIGIBLE, name);
	}
	private ItemType(int id, int maxCount){
		this(id, DAMAGE_NEGLIGIBLE, maxCount);
	}
	private ItemType(int id, int damage, int maxCount){
		this(id, damage, maxCount, null);
		itemName = name();
	}
	private ItemType(int id, int maxCount, String name){
		this(id, DAMAGE_NEGLIGIBLE, maxCount, name);
	}
	private ItemType(int id, int damage, int maxCount, String name){
		this.id = id;
		this.damage = damage;
		itemName = name;
		this.maxCount = maxCount;
	}

	public int getID(){
		return id;
	}
	public int getDamage(){
		return damage;
	}
	public String getName(){
		return itemName; // TODO
	}
	public int getMaxCount(){
		return maxCount;
	}

	public static ItemType[] getItemTypes(int id){
		List<ItemType> types = new ArrayList<ItemType>(1);
		for(ItemType type: values()){
			if(type.getID() == id){
				types.add(type);
			}
		}
		ItemType[] array = new ItemType[types.size()];
		ItemType[] ret = types.toArray(array);
		return ret == null ? array:ret;
	}
	public static ItemType getItemType(int id){
		ItemType[] types = getItemTypes(id);
		if(types.length >= 1){
			return types[0];
		}
		return null;
	}
	public static ItemType getItemType(int id, int damage){
		for(ItemType type: values()){
			if(type.getID() == id){
				if(type.getDamage() == DAMAGE_NEGLIGIBLE || type.getDamage() == damage){
					return type;
				}
			}
		}
		return null;
	}
}
