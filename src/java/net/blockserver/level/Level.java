package net.blockserver.level;


import net.blockserver.blocks.Block;
import net.blockserver.level.format.IChunk;
import net.blockserver.level.format.ILevel;
import net.blockserver.math.Vector3;

public class Level implements ILevel
{
    private IChunk[] chunks;

    public String getName() {
        return null;
    }

    public void setName() {

    }

    @Override
    public String getPath() {
        return null;
    }

    @Override
    public void setPath(String p) {

    }

    public Block getBlock(int x, int y, int z) {
        return null;
    }

    public void setBlock(Block block) {

    }

    public IChunk[] getAllChunks() {
        return this.chunks;
    }

    public IChunk getChunk(int x, int z) {
        return null;
    }

    public int getBlockID(int x, int y, int z) {
        return 0;
    }

    public void setBlockID(int x, int y, int z, int blockID) {

    }

    public int getBlockMeta(int x, int y, int z) {
        return 0;
    }

    public void setBlockMeta(int x, int y, int z, int meta) {

    }

    public int getBlockColor(int x, int y, int z) {
        return 0;
    }

    public void setBlockColor(int x, int y, int z, int r, int g, int b) {

    }

    public boolean isChunkLoaded(int chunkX, int chunkZ) {
        return false;
    }

    public void setChunk(int x, int z) {

    }

    public double getGravityAt(Vector3 coords){
        return 9.8; // Earth gravitational constant; not sure if same for Minecraft ;)
    }

}
