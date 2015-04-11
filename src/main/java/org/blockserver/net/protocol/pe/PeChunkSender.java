package org.blockserver.net.protocol.pe;

import org.blockserver.level.ChunkPosition;
import org.blockserver.level.IChunk;
import org.blockserver.net.protocol.pe.sub.gen.FullChunkDataPacket;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;

/**
 * A MCPE Chunk Sender.
 */
public class PeChunkSender extends Thread{
	public final HashMap<ChunkPosition, IChunk> useChunks = new HashMap<>();
	private final HashMap<Integer, ArrayList<ChunkPosition>> mapOrder = new HashMap<>();
	private final HashMap<ChunkPosition, Boolean> requestChunks = new HashMap<>();
	private final ArrayList<Integer> orders = new ArrayList<>();

	public boolean first = true;
	public int lastCX = 0, lastCZ = 0;

	private PeProtocolSession session;

	public PeChunkSender(PeProtocolSession session){
		this.session = session;
	}

	@Override
	public void run(){
		setName("PEChunkSender");
		session.getServer().getLogger().debug("ChunkSender start.");
		System.out.println("In ChunkSender");
		while(!isInterrupted()){
			int centerX = (int) Math.floor(session.getPlayer().getLocation().getX()) >> 4; // otherwise -1 will become 0 too
			int centerZ = (int) Math.floor(session.getPlayer().getLocation().getZ()) >> 4;

			try{
				if(centerX == lastCX && centerZ == lastCZ && !first){
					Thread.sleep(100);
					continue;
				}
			}catch(InterruptedException e){
				continue;
			}
			System.out.println("Do ChunkSender " + centerX + ", " + centerZ);
			first = false;
			lastCX = centerX; lastCZ = centerZ;
			int radius = 8;

			mapOrder.clear();
			requestChunks.clear();
			orders.clear();

			for(int x = -radius; x <= radius; ++x){
				for(int z = -radius; z <= radius; ++z){
					int distance = (x*x) + (z*z);
					int chunkX = x + centerX;
					int chunkZ = z + centerZ;
					if(!mapOrder.containsKey(distance)){
						mapOrder.put(distance, new ArrayList<>());
					}
					requestChunks.put(new ChunkPosition(chunkX, chunkZ), true);
					mapOrder.get(distance).add(new ChunkPosition(chunkX, chunkZ));
					orders.add(distance);
				}
			}
			Collections.sort(orders);

			for(Integer i : orders){
				for(ChunkPosition v : mapOrder.get(i)){
					try{
						if(useChunks.containsKey(v)){
							continue;
						}
						useChunks.put(v, session.getServer().getLevelManager().getLevelImplemenation().getChunkAt(v));
						FullChunkDataPacket dp = new FullChunkDataPacket(useChunks.get(v));
						dp.encode();
						session.addToQueue(dp.getCompressed());
					}catch(Exception e){
						e.printStackTrace();
					}
				}
			}
			//TODO Unload Unused Chunks
			/*
			ChunkPosition[] v2a = requestChunks.keySet().toArray(new Vector2[useChunks.keySet().size()] );
			for( int i = 0; i < v2a.length; i++ ){
				ChunkPosition v = v2a[i];
				if( !useChunks.containsKey( v2a ) ){
					level.releaseChunk(v);
					useChunks.remove(v);
				}
			}
			*/
			System.out.println("Do Finish");
			try{
				Thread.sleep(100);
			}catch(InterruptedException e){
				e.printStackTrace();
			}
		}
		System.out.println("Out ChunkSender");

		/*
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
		}catch(Exception e){
			e.printStackTrace();
		}

		session.getServer().getLogger().debug("Chunksender end.");
		*/
	}
}
