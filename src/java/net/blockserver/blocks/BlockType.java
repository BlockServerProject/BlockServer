package net.blockserver.blocks;

public enum BlockType {

    AIR(0),
    STONE(1),
    GRASS(2),
    DIRT(3),
    COBBLESTONE(4),
    COBBLE(4),
    PLANK(5),
    PLANKS(5),
    WOODEN_PLANK(5),
    WOODEN_PLANKS(5),
    SAPLING(6),
    SAPLINGS(6),
    BEDROCK(7),
    WATER(8),
    STILL_WATER(9),
    LAVA(10),
    STILL_LAVA(11),
    SAND(12),
    GRAVEL(13),
    GOLD_ORE(14),
    IRON_ORE(15),
    COAL_ORE(16),
    WOOD(17),
    TRUNK(17),
    LOG(17),
    LEAVES(18),
    LEAVE(18),
    SPONGE(19),
    GLASS(20),
    LAPIS_ORE(21),
    LAPIS_BLOCK(22),
    SANDSTONE(24),
    CHISELED_SANDSTONE(24, 1),
    SMOOTH_SANDSTONE(24, 2),
    BED_BLOCK(26),
    POWERED_RAIL(27),
    COBWEB(30),
    TALL_GRASS(31),
    BUSH(32),
    DEAD_BUSH(32),
    WHITE_WOOL(35),
    ORANGE_WOOL(35, 1),
    MAGENTA_WOOL(35, 2),
    LIGHT_BLUE_WOOL(35, 3),
    YELLOW_WOOL(35, 4),
    LIME_GREEN_WOOL(35, 5),
    PINK_WOOL(35, 6),
    GRAY_WOOL(35, 7),
    LIGHT_GRAY_WOOL(35, 8),
    CYAN_WOOL(35, 9),
    PURPLE_WOOL(35, 10),
    BLUE_WOOL(35, 11),
    BROWN_WOOL(35, 12),
    GREEN_WOOL(35, 13),
    RED_WOOL(35, 14),
    BLACK_WOOL(35, 15),
    DANDELION(37),
    ROSE(38),
    POPPY(38),
    BROWN_MUSHROOM(39),
    RED_MUSHROOM(40),
    GOLD_BLOCK(41),
    IRON_BLOCK(42),
    DOUBLE_SLAB(43),
    DOUBLE_SLABS(43),
    SLAB(44),
    SLABS(44),
    BRICKS(45),
    BRICKS_BLOCK(45),
    TNT(46),
    BOOKSHELF(47),
    MOSS_STONE(48),
    MOSSY_STONE(48),
    OBSIDIAN(49),
    TORCH(50),
    FIRE(51),
    MONSTER_SPAWNER(52),
    WOOD_STAIRS(53),
    WOODEN_STAIRS(53),
    OAK_WOOD_STAIRS(53),
    OAK_WOODEN_STAIRS(53),
    CHEST(54),
    DIAMOND_ORE(56),
    DIAMOND_BLOCK(57),
    CRAFTING_TABLE(58),
    WORKBENCH(58),
    WHEAT_BLOCK(59),
    FARMLAND(60),
    FURNACE(61),
    BURNING_FURNACE(62),
    LIT_FURNACE(62),
    SIGN_POST(63),
    DOOR_BLOCK(64),
    WOODEN_DOOR_BLOCK(64),
    WOOD_DOOR_BLOCK(64),
    LADDER(65),
    COBBLE_STAIRS(67),
    COBBLESTONE_STAIRS(67),
    WALL_SIGN(68),
    IRON_DOOR_BLOCK(71),
    REDSTONE_ORE(73),
    GLOWING_REDSTONE_ORE(74),
    LIT_REDSTONE_ORE(74),
    SNOW(78),
    SNOW_LAYER(78),
    ICE(79),
    SNOW_BLOCK(80),
    CACTUS(81),
    CLAY_BLOCK(82),
    REEDS(83),
    SUGARCANE_BLOCK(83),
    FENCE(85),
    PUMPKIN(86),
    NETHERRACK(87),
    SOUL_SAND(88),
    GLOWSTONE(89),
    GLOWSTONE_BLOCK(89),
    LIT_PUMPKIN(91),
    JACK_O_LANTERN(91),
    CAKE_BLOCK(92),
    INVISIBLE_BEDROCK(95),
    TRAPDOOR(96),
    STONE_BRICKS(98),
    STONE_BRICK(98),
    IRON_BAR(101),
    IRON_BARS(101),
    GLASS_PANE(102),
    GLASS_PANEL(102),
    MELON_BLOCK(103),
    PUMPKIN_STEM(104),
    MELON_STEM(105),
    FENCE_GATE(107),
    BRICK_STAIRS(108),
    STONE_BRICK_STAIRS(109),
    NETHER_BRICKS(112),
    NETHER_BRICK_BLOCK(112),
    NETHER_BRICKS_STAIRS(114),
    SANDSTONE_STAIRS(128),
    EMERALD_ORE(129),
    EMERALD_BLOCK(133),
    SPRUCE_WOOD_STAIRS(134),
    SPRUCE_WOODEN_STAIRS(134),
    BIRCH_WOOD_STAIRS(135),
    BIRCH_WOODEN_STAIRS(135),
    JUNGLE_WOOD_STAIRS(136),
    JUNGLE_WOODEN_STAIRS(136),
    COBBLE_WALL(139),
    STONE_WALL(139),
    COBBLESTONE_WALL(139),
    CARROT_BLOCK(141),
    POTATO_BLOCK(142),
    QUARTZ_BLOCK(155),
    QUARTZ_STAIRS(156),
    DOUBLE_WOOD_SLAB(157),
    DOUBLE_WOODEN_SLAB(157),
    DOUBLE_WOOD_SLABS(157),
    DOUBLE_WOODEN_SLABS(157),
    WOOD_SLAB(158),
    WOODEN_SLAB(158),
    WOOD_SLABS(158),
    WOODEN_SLABS(158),
    STAINED_CLAY(159),
    STAINED_HARDENED_CLAY(159),
    LEAVES2(161),
    LEAVE2(161),
    WOOD2(162),
    TRUNK2(162),
    LOG2(162),
    ACACIA_WOOD_STAIRS(163),
    ACACIA_WOODEN_STAIRS(163),
    DARK_OAK_WOOD_STAIRS(164),
    DARK_OAK_WOODEN_STAIRS(164),
    HAY_BALE(170),
    CARPET(171),
    HARDENED_CLAY(172),
    COAL_BLOCK(173),
    BEETROOT_BLOCK(244),
    STONECUTTER(245),
    GLOWING_OBSIDIAN(246),
    NETHER_REACTOR(247);
    
    private int id;
    private int metadata = 0;

    private BlockType(int id){
    	this.id = id;
    }
    
    private BlockType(int id, int metadata){
    	this.id = id;
    	this.metadata = metadata;
    }
    
    public int getID(){
    	return this.id;
    }
    
    public BlockType setMetadata(int metadata){
    	this.metadata = metadata;
    	return this;
    }
    
    public int getMetadata(){
    	return this.metadata;
    }
    
    public static BlockType getByID(int id) throws UnknownBlockException{
    	for(BlockType block : BlockType.values())
    		if(block.getID() == id)
    			return block;
    	throw new UnknownBlockException("No block found with ID" + id);
    }
    
    public static BlockType getByID(int id, int metadata) throws UnknownBlockException{
        return BlockType.getByID(id).setMetadata(metadata);
    }
    
    @Override
    public String toString(){
        return this.id + ":" + this.metadata;
    }

}
