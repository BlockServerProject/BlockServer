package org.blockserver.blocks;

/**
 * An implementation of a Minecraft block.
 * @author BlockServer Team
 *
 */
public class Block {
    private int id;
    private int metadata;
    private String name;

    /**
     * The Simple constructor for Block. Metadata is 0, and the name is unknown.
     * @param id The Block ID for the block.
     */
    public Block(int id) {
        this.id = id;
        this.metadata = 0;
        this.name = "Unknown";
    }
    
    /**
     * Another Simple constructor for Block. Requires a BlockType object
     * @param block The BlockType object for the Block object.
     */
    public Block(BlockType block){
        this.id = block.getID();
        this.metadata = block.getMetadata();
        this.name = block.name();
    }

    /**
     * Another constructor for Block. Requires the name of the block, and the ID. Metadata is 0.
     * @param name The name of the block.
     * @param id The Block ID of the block.
     */
    public Block(String name, int id) {
        this.id = id;
        this.metadata = 0;
        this.name = name.isEmpty() ? "Unknown" : name;
    }

    /**
     * Another constructor for Block.
     * @param name The name of the block.
     * @param id The Block ID of the block.
     * @param metadata The metadata for the block.
     */
    public Block(String name, int id, int metadata) {
        this.id = id;
        this.metadata = metadata;
        this.name = name.isEmpty() ? "Unknown" : name;
    }

    /**
     * Get the Block ID of the block.
     * @return The block ID.
     */
    public int getID(){
        return id;
    }

    /**
     * Get the block's damage
     * @return The block's damage.
     */
    public int getDamage(){
        return metadata;
    }

    /**
     * Get the block's name.
     * @return The block's name
     */
    public String getName(){
        return name;
    }

}
