package org.blockserver.net.protocol.pe;

import org.blockserver.level.ChunkPosition;
import org.blockserver.level.impl.dummy.DummyChunk;
import org.blockserver.net.protocol.pe.raknet.NetworkChannel;
import org.blockserver.net.protocol.pe.sub.v20.FullChunkDataPacketV20;
import org.blockserver.net.protocol.pe.sub.v27.FullChunkDataPacketV27;
import org.blockserver.net.protocol.pe.sub.v27.PlayStatusPacket;

import java.nio.ByteBuffer;

/**
 * Dummy Chunk Sender: send dummy chunks to MCPE clients.
 */
public class DummyChunkSender extends PeChunkSender{

    public DummyChunkSender(RakNetProtocolSession session) {
        super(session);
    }

    @Override
    public void run() {
        RakNetProtocolSession session = getSession();
        setName("PEChunkSender");
        session.getServer().getLogger().debug("ChunkSender start.");
        System.out.println("In ChunkSender");

        int centerX = session.getPlayer().getLocation().getChunkPos().getX();
        int centerZ = session.getPlayer().getLocation().getChunkPos().getZ();

        //FullChunkDataPacket dp = new FullChunkDataPacket(server.world.getChunkAt((int) x, (int) z), server.world);
        //player.addToQueue(dp);
        int cornerX = centerX - 64;
        int cornerZ = centerZ + 64;

        int x = cornerX;
        int z = cornerZ;

        int chunkNum = 0;
        DummyChunk chunk = new DummyChunk(new ChunkPosition(x, z));
        chunk.generate();
        byte[] buffer = assembleChunkData(chunk);
        try{
            while(chunkNum < 96){
                System.out.println("ChunkSender chunk "+x+", "+z);
                if(session.getProtocolId() >= 21){
                    FullChunkDataPacketV27 dp = new FullChunkDataPacketV27();
                    dp.data = buffer;
                    dp.setChannel(NetworkChannel.CHANNEL_WORLD_CHUNKS);
                    session.addToQueue(dp);
                } else {
                    FullChunkDataPacketV20 dp = new FullChunkDataPacketV20();
                    dp.data = buffer;
                    dp.setChannel(NetworkChannel.CHANNEL_WORLD_CHUNKS);
                    session.addToQueue(dp);
                }
                if(x < cornerX + 144){
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
        if(session.getProtocolId() >= 21) {
            PlayStatusPacket psp = new PlayStatusPacket();
            psp.status = PlayStatusPacket.PLAYER_SPAWN;
            psp.setChannel(NetworkChannel.CHANNEL_PRIORITY);
            session.addToQueue(psp);
        }
    }

    private byte[] assembleChunkData(DummyChunk chunk) {
        ByteBuffer bb = ByteBuffer.allocate(83200);
        bb.put(chunk.getBlockIds());
        bb.put(chunk.getBlockData());
        bb.put(chunk.getSkylight());
        bb.put(chunk.getBlocklight());
        bb.put(chunk.getBiomeIds());
        bb.put(chunk.getBiomeColors());
        return bb.array();
    }
}
