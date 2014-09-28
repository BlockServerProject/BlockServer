package org.blockserver.network.minecraft;

import java.nio.ByteBuffer;

import org.blockserver.player.Player;

public class AddPlayerPacket implements BaseDataPacket {
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
		clientID = player.getClientID();
		username = player.getName();
		EID = player.getEID();
		x = (float) player.getX();
		y = (float) player.getY();
		z = (float) player.getZ();
		yaw = (byte) player.getYaw();
		pitch = (byte) player.getPitch();
	}
	
	public void encode(){
		buffer = ByteBuffer.allocate(31 + username.length() + metadata.length);
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
	
	public void decode(){
		throw new UnsupportedOperationException("This packet is Server to Client side only and cannot be decoded.");
	}
	
	public ByteBuffer getBuffer(){
		return buffer;
	}

}
