package org.blockserver.player;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import org.blockserver.BlockServer;
import org.blockserver.Server;
import org.blockserver.cmd.CommandIssuer;
import org.blockserver.entity.Entity;
import org.blockserver.entity.SavedEntity;
import org.blockserver.item.Inventory;
import org.blockserver.level.generator.Generator;
import org.blockserver.level.provider.ChunkPosition;
import org.blockserver.level.provider.IChunk;
import org.blockserver.math.Vector3;
import org.blockserver.math.Vector3d;
import org.blockserver.network.minecraft.AddPlayerPacket;
import org.blockserver.network.minecraft.BaseDataPacket;
import org.blockserver.network.minecraft.ClientConnectPacket;
import org.blockserver.network.minecraft.ClientHandShakePacket;
import org.blockserver.network.minecraft.DisconnectPacket;
import org.blockserver.network.minecraft.FullChunkDataPacket;
import org.blockserver.network.minecraft.LoginPacket;
import org.blockserver.network.minecraft.LoginStatusPacket;
import org.blockserver.network.minecraft.MessagePacket;
import org.blockserver.network.minecraft.MovePlayerPacket;
import org.blockserver.network.minecraft.PacketIDs;
import org.blockserver.network.minecraft.PingPacket;
import org.blockserver.network.minecraft.PongPacket;
import org.blockserver.network.minecraft.ServerHandshakePacket;
import org.blockserver.network.minecraft.SetHealthPacket;
import org.blockserver.network.minecraft.SetSpawnPositionPacket;
import org.blockserver.network.minecraft.SetTimePacket;
import org.blockserver.network.minecraft.StartGamePacket;
import org.blockserver.network.raknet.ACKPacket;
import org.blockserver.network.raknet.AcknowledgePacket;
import org.blockserver.network.raknet.CustomPacket;
import org.blockserver.network.raknet.InternalPacket;
import org.blockserver.network.raknet.NACKPacket;
import org.blockserver.scheduler.CallbackTask;

public class Player extends Entity implements CommandIssuer, PacketIDs{
	public /*final*/ static long MAX_PING = 15000L;
	private String name;
	private long lastPing, pingMeasure = 0;
	private String ip;
	private int port;

	private int lastSequenceNum;
	private int sequenceNum;
	private short mtuSize; // Maximum Transport Unit Size
	private int messageIndex;
	private CustomPacket queue;
	private List<Integer> ACKQueue; // Received Packet Queue
	private List<Integer> NACKQueue; // Not received packet queue
	private Map<Integer, CustomPacket> recoveryQueue; // Packet sent queue to be used if not received
	private long clientID; // Client ID From MCPE Client

	private int maxHealth;
	private Server server;
	protected Inventory inventory;
	
	private ChunkSender sender;

	public Player(Server server, String ip, int port, short mtu, long clientID){
		super(new Vector3d(0, 128, 0), null);
		lastPing = System.currentTimeMillis(); // should I use nanos instead?
		this.ip = ip.replace("/", "");
		this.port = port;
		mtuSize = mtu;
		this.clientID = clientID;
		lastSequenceNum = sequenceNum = messageIndex = 0;
		this.server = server;
		queue = new CustomPacket();
		ACKQueue = new ArrayList<Integer>();
		NACKQueue = new ArrayList<Integer>();
		recoveryQueue = new HashMap<Integer, CustomPacket>();
		try{
			server.getScheduler().addTask(new CallbackTask(this, "update", 10, true)); // do this with the entity context?
			server.getScheduler().addTask(new CallbackTask(this, "sendChunk", 1, true));
		}
		catch(Exception e){
			e.printStackTrace();
		}
		sender = new ChunkSender();
	}
	
	public static final byte[] FLATREPEAT = new byte[]{0x07, 0x03, 0x03, 0x02, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00};
	public static final byte[] BIOMECOLOR = new byte[]{0x00 ,(byte) 0x85 ,(byte) 0xb2 ,0x4a};
	
	/**
	 * I'm can't handle BSLChunk, Dummy Chunk for Sending Test
	 * @author Blue Electric
	 */
	public final class DummyChunk extends IChunk {
		public final byte[] blockIDs;
		public final byte[] blockDamages;
		public final byte[] skyLights;
		public final byte[] blockLights;
		public final byte[] biomeIds;
		public final int[] biomeColors;
		
		public final int x, z;
		
		public DummyChunk(int x, int z) {
			this.x = x; this.z = z;
			blockIDs = new byte[0x8000];
			blockDamages = new byte[0x4000];
			skyLights = new byte[0x4000];
			blockLights = new byte[0x4000];
			biomeIds = new byte[0x100];
			for(int i = 0; i < blockIDs.length; i+=FLATREPEAT.length) {
				System.arraycopy(FLATREPEAT, 0, blockIDs, i, FLATREPEAT.length);
			}
			
			//GOD... WHY!!!!!!! WHY GRASS IS DARKJET!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
			biomeColors = new int[0x100];
			for(int i = 0; i < biomeColors.length; i++) {
				biomeColors[i] = 0xff;
			}
		}
		
		@Override
		public Map<Integer, SavedEntity> getMappedEntities(){
			return null;
		}

		@Override
		public Collection<SavedEntity> getEntities(){
			return null;
		}

		@Override
		public byte[] getBlocks(){
			return blockIDs;
		}

		@Override
		public byte[] getDamages(){
			return blockDamages;
		}

		@Override
		public byte[] getSkyLights(){
			return skyLights;
		}

		@Override
		public byte[] getBlockLights(){
			return blockLights;
		}

		@Override
		public byte[] getBiomeIds(){
			return biomeIds;
		}

		@Override
		public int[] getBiomeColors(){
			return biomeColors;
		}

		@Override
		public int getX(){
			return x;
		}

		@Override
		public int getZ(){
			return z;
		}

		@Override
		public byte[] getTiles(){
			return null;
		}
		
	}
	
	public final class ChunkSender {
		public final class ChunkCache {
			public final IChunk chunk;
			private boolean sended = false;
			
			public ChunkCache(IChunk chunk) {
				this.chunk = chunk;
			}
			
			public void send() throws Exception {
				if(sended) { return; }
				sendQueue();
				addToQueue( new FullChunkDataPacket(chunk) );
				lastChunkSeq = queue.sequenceNumber;
				sendQueue();
				sended = true;
			}
			public boolean isSended() {
				return sended;
			}
		}
		
		protected final HashMap<ChunkPosition, ChunkCache> useChunks = new HashMap<>();
		private final HashMap<Integer, ArrayList<ChunkPosition>> MapOrder = new HashMap<>();
		private final HashMap<ChunkPosition, Boolean> requestChunks = new HashMap<>();
		private final ArrayList<Integer> orders = new ArrayList<>();
		private final LinkedList<ChunkCache> sendChunk = new LinkedList<>();
		private int totalSend = 0;
		private boolean first = true;
		private int lastCX = 0, lastCZ = 0;
		private int lastChunkSeq = -1;
		private final Object lastChunkLock = new Object();
		
		public final void destroy() {
			//TODO: Release Chunk
			/*
			for( ChunkCache cc : useChunks.values() ) {
				level.releaseChunk( cc.chunk.x, cc.chunk.z );
			}
			*/
		}
		
		public final void ACKReceive(int seq) {
			synchronized (lastChunkLock) {
				if(seq >= lastChunkSeq) {
					lastChunkSeq = -1;
				}
			}
		}
		
		public final boolean updateChunk() throws Exception {
			if( first && totalSend == 56 ) {
				InitPlayer();
				first = false;
			}
			synchronized (lastChunkLock) {
				if( lastChunkSeq != -1 ) { return false; }
			}
			if( !sendFourChunk() ) {
				return refreshChunkList();
			}
			return true;
		}
		
		private final boolean sendFourChunk() throws Exception {
			return sendOneChunk() && sendOneChunk() && sendOneChunk() && sendOneChunk();
		}
		
		private final boolean sendOneChunk() throws Exception {
			ChunkCache cc = sendChunk.poll();
			if( cc == null ) {
				return false;
			}
			if( !cc.isSended() ) {
				cc.send();
				totalSend++;
				Thread.sleep(1);
			} else {
				return sendOneChunk();
			}
			return true;
		}
		
		private final boolean refreshChunkList() {
			if(name == null) {
				return false;
			}
			
			int centerX = (int) ( (int) Math.floor(x) / 16 );
			int centerZ = (int) ( (int) Math.floor(z) / 16 );
			
			if( centerX == lastCX && centerZ == lastCZ && !first ) {
				return true;
			}
			lastCX = centerX; lastCZ = centerZ;
			int radius = 4; //TODO Receive it from Config
			
			MapOrder.clear(); requestChunks.clear(); orders.clear();
			
			for (int x = -radius; x <= radius; ++x) {
				for (int z = -radius; z <= radius; ++z) {
					int distance = (x*x) + (z*z);
					int chunkX = x + centerX;
					int chunkZ = z + centerZ;
					ChunkPosition v = ChunkPosition.byDirectIndex(chunkX, chunkZ);
					if( !MapOrder.containsKey( distance ) ) {
						MapOrder.put(distance, new ArrayList<ChunkPosition>());
					}
					requestChunks.put(v, true);
					MapOrder.get(distance).add( v );
					if( !orders.contains(distance) ) {
						orders.add(distance);
					}
				}
			}
			Collections.sort(orders);
			
			for( Integer i : orders ) {
				for( ChunkPosition v : MapOrder.get(i) ) {
					try {
						if( useChunks.containsKey(v) ) { continue; }
						ChunkCache cc = new ChunkCache( new DummyChunk(v.getX(), v.getZ()) );
						useChunks.put(v, cc);	
						if( !sendChunk.contains(cc) ) {
							sendChunk.add( cc );
						}
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
			}
			
			//TODO: Release Chunk
			/*
			Vector2[] v2a = useChunks.keySet().toArray(new Vector2[useChunks.keySet().size()] );
			for( int i = 0; i < v2a.length; i++ ) {
				Vector2 v = v2a[i];
				if( !requestChunks.containsKey( v ) ) {
					owner.level.releaseChunk(v);
					useChunks.remove(v);
				}
			}
			*/
			return false;
		}
	}
	
	public final void InitPlayer() {
		addToQueue( new SetTimePacket( 0 ) );
		MovePlayerPacket player = new MovePlayerPacket(getEID(), (float) x, (float) y, (float) z, (float) getYaw(), (float) getPitch(), (float) 0, false);
		addToQueue(player);
		
		//TODO: Add player for other Player
		//AddPlayerPacket app = new AddPlayerPacket(Player.this);
		//leader.player.broadcastPacket(app, false, Player.this);
		
		sendChatArgs(server.getMOTD());
		server.getChatMgr().broadcast(name + " joined the game.");
		for(Player other: server.getConnectedPlayers()){
			if(!(other.clientID == this.clientID)){
				spawnPlayer(other);
			}
		}
	}
	
	public void sendChunk(long tick){
		try{
			sender.updateChunk();
		}
		catch(Exception e){
			e.printStackTrace();
		}
	}

	public void update(long ticks){
		synchronized(ACKQueue){
			if(this.ACKQueue.size() > 0){
				int[] array = new int[this.ACKQueue.size()];
				int offset = 0;
				for(Integer i: ACKQueue){
					array[offset++] = i;
				}
				ACKPacket pck = new ACKPacket(array);
				pck.encode();
				server.sendPacket(pck.getBuffer(), ip, port);
			}
		}
		synchronized(NACKQueue){
			if(NACKQueue.size() > 0){
				int[] array = new int[NACKQueue.size()];
				int offset = 0;
				for(Integer i: NACKQueue){
					array[offset++] = i;
				}
				NACKPacket pck = new NACKPacket(array);
				pck.encode();
				server.sendPacket(pck.getBuffer(), ip, port);
			}
		}
		if(System.currentTimeMillis() - lastPing >= MAX_PING){
			close(String.format("Ping timeout: %d seconds", (System.currentTimeMillis() - lastPing) / 1000d));
		}
		synchronized(queue){
			if(queue.packets.size() > 0){
                queue.sequenceNumber = sequenceNum++;
				queue.encode();
				server.sendPacket(queue.getBuffer(), ip, port);
				recoveryQueue.put(queue.sequenceNumber, queue);
				queue.packets.clear();
			}
		}
	}

	public void addToQueue(BaseDataPacket pck){
		synchronized(queue){
			pck.encode();
			BlockServer.Debugging.logSentDataPacket(pck, this);
			InternalPacket ipck = new InternalPacket();
			ipck.buffer = pck.getBuffer();
			ipck.reliability = 2;
			ipck.messageIndex = messageIndex++;
			ipck.toBinary();
			if(queue.getLength() + pck.getBuffer().length >= mtuSize){
				sendQueue();
			}
			queue.packets.add(ipck);
		}
	}
	public void sendQueue() {
		synchronized(queue){
			queue.sequenceNumber = sequenceNum++;
			queue.encode();
			server.sendPacket(queue.getBuffer(), ip, port);
			queue.packets.clear();
		}
	}
	
	public void handlePacket(CustomPacket pck){
		if(pck.sequenceNumber - this.lastSequenceNum == 1){
			lastSequenceNum = pck.sequenceNumber;
		}
		else{
			for(int i = this.lastSequenceNum; i < pck.sequenceNumber; ++i){
				NACKQueue.add(i);
			}
		}
		synchronized(ACKQueue){
			ACKQueue.add(pck.sequenceNumber);
		}
		for(InternalPacket ipck : pck.packets){
			BlockServer.Debugging.logReceivedInternalPacket(ipck, this);
			switch(ipck.buffer[0]){
				case PING: //PING Packet
					PingPacket pp = new PingPacket(ipck.buffer);
					pp.decode();
					pingMeasure = System.currentTimeMillis() - lastPing;
					lastPing = System.currentTimeMillis();
					PongPacket reply = new PongPacket(pp.pingID);
					addToQueue(reply);
					break;
				case CLIENT_CONNECT: // 0x09. Use the constants class
					ClientConnectPacket ccp = new ClientConnectPacket(ipck.buffer);
					ccp.decode();
					//Send a ServerHandshake packet
					ServerHandshakePacket shp = new ServerHandshakePacket(this.port, ccp.session);
					addToQueue(shp);
					break;
				case CLIENT_HANDSHAKE:
					ClientHandShakePacket chs = new ClientHandShakePacket(ipck.buffer);
					chs.decode();
					break;
				case LOGIN:
					LoginPacket lp = new LoginPacket(ipck.buffer);
					server.getLogger().info("Login Packet: %d", ipck.buffer.length);
					lp.decode();
					if(lp.protocol != CURRENT_PROTOCOL || lp.protocol2 != CURRENT_PROTOCOL){
						if(lp.protocol < CURRENT_PROTOCOL || lp.protocol2 < CURRENT_PROTOCOL){
							addToQueue(new LoginStatusPacket(1)); // Client outdated
							close("Wrong Protocol: Client is outdated.");
						}

						if(lp.protocol > CURRENT_PROTOCOL || lp.protocol2 > CURRENT_PROTOCOL){
							addToQueue(new LoginStatusPacket(2)); // Server outdated
							close("Wrong Protocol: Server is outdated.");
						}
					}
					addToQueue(new LoginStatusPacket(0)); // No error with the protocol.
					if(lp.username.length() < 3 || lp.username.length() > 15){
						close("Username is not valid.");
					}
					else{
						name = lp.username;
						server.getLogger().info("%s (%s:%d) logged in with a fake entity ID.", name, ip, port);

						login();
						//Re-enable it
						//StartGamePacket sgp = new StartGamePacket(level.getSpawnPos().toVector3(), this, StartGamePacket.GENERATOR_INFINITE, 1, level.getSeed(), 0);
						StartGamePacket sgp = new StartGamePacket(new Vector3(128, 4, 128), this, StartGamePacket.GENERATOR_INFINITE, 1, 0L, 0);
						addToQueue(sgp);
						
						SetTimePacket stp = new SetTimePacket(0);
						addToQueue(stp);
						
						SetSpawnPositionPacket sspp = new SetSpawnPositionPacket( new Vector3(128, 4, 128) );
						addToQueue(sspp);
						
						SetHealthPacket setHealth = new SetHealthPacket((byte) 0x20);
						addToQueue(setHealth);
					}
					break;
				case DISCONNECT:
					disconnect("disconnected by client");
					break;
				case MESSAGE:
					MessagePacket mpk = new MessagePacket(ipck.buffer);
					mpk.decode();
					server.getChatMgr().handleChat(this, mpk.getMessage());
					break;
				case MOVE_PLAYER:
					MovePlayerPacket movePlayer = new MovePlayerPacket( ipck.buffer );
					movePlayer.decode();
					x = movePlayer.x;
					y = movePlayer.y;
					z = movePlayer.z;
					break;
				default:
					server.getLogger().debug("Unsupported packet recived: %02x", ipck.buffer[0]);
                    break;
			}
		}
	}

	public void handleAcknowledgePackets(AcknowledgePacket pck){ // ACK and NACK
		pck.decode();
		if(pck instanceof ACKPacket){ // When we receive a ACK Packet then
			for(int i: pck.sequenceNumbers){
				//server.getLogger().info("ACK Packet Received Seq: %d", i);
				sender.ACKReceive(i);
				recoveryQueue.remove(i);
			}
		}
		else if(pck instanceof NACKPacket){
			for(int i: pck.sequenceNumbers){
				server.getLogger().info("NACK Packet Received Seq: %d", i);
				server.sendPacket(recoveryQueue.get(i).getBuffer(), ip, port);
			}
		}
		else{
			server.getLogger().error("Unknown Acknowledge Packet: %02x", pck.buffer[0]);
		}
	}

	// API methods outside networking
	public void sendChat(String msg, String src){
		MessagePacket mpkt = new MessagePacket(msg);
		addToQueue(mpkt); // be aware of the message-too-long exception
	}
	public void sendChatArgs(String msg, Object... args){
		sendChat(String.format(msg, args));
	}
	@Override
	public void sendChat(String msg){
		sendChat(msg, "");
	}
	public void spawnPlayer(Player other){
		if(other != this){
			addToQueue(new AddPlayerPacket(other));
		}
	}

	protected void login(){
		List<Player> toKick = new ArrayList<Player>();
		for(Player player: server.getConnectedPlayers()){
			if(player != null && player != this){
				if(player.getName().equalsIgnoreCase(name)){
					toKick.add(player);
				}
			}
		}
		for(Player player: toKick){
			player.close("logging in from another location.");
		}
		level = server.getDefaultLevel();
		//TODO: Re-enable this.
		/*
		PlayerData data = server.getPlayerDatabase().load(this);
		setCoords(data.getCoords());
		level = data.getLevel(); // validated!
		*/
		start(server);
	}
	public void close(String reason){
		if(reason != null){
			sendChat(reason);
		}
		addToQueue(new DisconnectPacket());
		disconnect(String.format("kicked (%s)", reason));
	}
	protected void disconnect(String reason){
		server.getLogger().info("%s (%s:%d) disconnected: %s.", name, ip, port, reason);
		server.getChatMgr().broadcast(name+" left the game.");
		server.removePlayer(this);
//		server.getPlayerDatabase().save(new PlayerData(level, this, getIName(), getInventory()));
	}

	public InetAddress getAddress(){
		try{
			return InetAddress.getByName(ip);
		}
		catch(UnknownHostException e){
			e.printStackTrace();
			return null;
		}
	}
	public String getIP(){
		return ip;
	}
	public int getPort(){
		return port;
	}
	public String getIName(){
		return name.toLowerCase(Locale.US);
	}
	public String getName(){
		return name;
	}
	public String getIdentifier(){ // why not just use EID?
		return ip + Integer.toString(port);
	}
	public long getLastPing(){
		return lastPing;
	}
	public long getPingMeasure(){
		return pingMeasure;
	}
	@Override
	public byte getTypeID(){
		return 63;
	}
	@Override
	public int getMaxHealth(){
		return maxHealth;
	}
//	@Override
	public Inventory getInventory(){
		return inventory;
	}
	public long getClientID() {
		return clientID;
	}

	@Override
	protected void broadcastMotion(){
		// TODO Auto-generated method stub
	}

	@Override
	public void sudoCommand(String line){
		server.getCmdManager().runCommand(this, line.substring(1));
	}
	@Override
	public int getHelpLines(){
		return 5; // TODO customization should be permitted
	}
	@Override
	public Server getServer(){
		return server;
	}
	@Override
	public String getEOL(){
		return "\n";
	}
}
