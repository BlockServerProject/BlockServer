package org.blockserver.net.protocol.pe;

import org.blockserver.level.IChunk;
import org.blockserver.net.protocol.pe.sub.gen.FullChunkDataPacket;
import org.blockserver.utils.Position;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;

/**
 * A MCPE Chunk Sender.
 */
public class PeChunkSender extends Thread{
    public final HashMap<Position, IChunk> useChunks = new HashMap<>();
    private final HashMap<Integer, ArrayList<Position>> mapOrder = new HashMap<>();
    private final HashMap<Position, Boolean> requestChunks = new HashMap<>();
    private final ArrayList<Integer> orders = new ArrayList<>();

    public boolean first = true;
    public int lastCX = 0, lastCZ = 0;

    private PeProtocolSession session;

    public PeChunkSender(PeProtocolSession session){
        this.session = session;
    }

    public void run(){
        setName("PEChunkSender");
        session.getServer().getLogger().debug("ChunkSender start.");
        int centerX = (int) session.getPlayer().getLocation().getX();
        int centerZ = (int) session.getPlayer().getLocation().getZ();

        int cornerX = centerX - 48;
        int cornerZ = centerZ + 48;

        int x = cornerX;
        int z = cornerZ;

        int chunkNum = 0;
        try{
            while(chunkNum < 49){
                System.out.println("ChunkSender chunk "+x+", "+z);
                FullChunkDataPacket dp = new FullChunkDataPacket(session.getServer().getLevelManager().getLevelImplemenation().getChunkAt(x, z));
                dp.encode();
                session.addToQueue(dp.getCompressed());

                if(x < cornerX + 112){
                    x = x + 16;
                } else {
                    x = cornerX;
                    z = z - 16;
                }
                chunkNum++;
                Thread.sleep(100);
            }
        } catch(Exception e){
            e.printStackTrace();
        }

        session.getServer().getLogger().debug("Chunksender end.");
    }
}
