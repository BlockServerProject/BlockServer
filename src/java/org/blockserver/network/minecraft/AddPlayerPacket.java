package org.blockserver.network.minecraft;

import java.nio.ByteBuffer;
import java.util.Random;

import org.blockserver.network.PacketDecodeException;
import org.blockserver.network.minecraft.BaseDataPacket;
import org.blockserver.player.Player;

public class AddPlayerPacket implements BaseDataPacket {
	private Player player;
	private ByteBuffer buffer;
	
	public final static byte PID = PacketsID.ADD_PLAYER;
	public long clientID;
	public String username;
	public int EID;
	public float x;
	public float y;
	public float z;
	public byte yaw;
	public byte pitch;
	public short unknown1;
	public short unknown2;
	public byte[] metadata = new byte[12];
	
	public AddPlayerPacket(Player player){
		this.player = player;
		this.clientID = player.getClientID();
		this.username = player.getName();
		this.EID = new Random().nextInt(1000);
		
		this.x = (float) player.getX();
		this.y = (float) player.getY();
		this.z = (float) player.getZ();
		
		this.yaw = (byte) player.getYaw();
		this.pitch = (byte) player.getPitch();
		
	}
	
	public void encode(){
		this.buffer = ByteBuffer.allocate(31+username.length()+metadata.length);
		buffer.put(PID);
		buffer.putLong(clientID);
		buffer.putShort((short) username.length());
		buffer.put(username.getBytes());
		
		buffer.putInt(EID);
		
		buffer.putFloat(x);
		buffer.putFloat(y);
		buffer.putFloat(z);
		buffer.put(yaw);
		buffer.put(pitch);
		
		buffer.putShort(unknown1);
		buffer.putShort(unknown2);
		
		//buffer.put(metadata);
		
	}
	
	public void decode() { throw new PacketDecodeException("This packet is Server to Client side only.");};
	
	public ByteBuffer getBuffer(){
		return buffer;
	}

}
