package net.blockserver.blocks;


public class Block
{
    private int id;
    private int metadata;
    private String name;

    public Block(int id)
    {
        this.id = id;
        this.metadata = 0;
        this.name = "Unknown";
    }
    
    public Block(BlockType block){
        this.id = block.getID();
        this.metadata = block.getMetadata();
        this.name = block.name();
    }

    public Block(String name, int id)
    {
        this.id = id;
        this.metadata = 0;
        this.name = name.isEmpty() ? "Unknown" : name;
    }

    public Block(String name, int id, int metadata)
    {
        this.id = id;
        this.metadata = metadata;
        this.name = name.isEmpty() ? "Unknown" : name;
    }

    public int getID(){
        return id;
    }

    public int getDamage(){
        return metadata;
    }

    public String getName(){
        return name;
    }

}
