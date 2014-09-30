package org.blockserver.network.minecraft;

import java.nio.ByteBuffer;

import org.blockserver.player.Player;

public class AddPlayerPacket extends BaseDataPacket{
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
		bb = ByteBuffer.allocate(31 + username.length() + metadata.length);
		bb.put(ADD_PLAYER);
		bb.putLong(clientID);
		bb.putShort((short) username.length());
		bb.put(username.getBytes());
		bb.putInt(EID);
		bb.putFloat(x);
		bb.putFloat(y);
		bb.putFloat(z);
		bb.put(yaw);
		bb.put(pitch);
		bb.putShort(unknown1);
		bb.putShort(unknown2);
		// bb.put(metadata);
	}
	
	public void decode(){
		throw new UnsupportedOperationException("This packet is Server to Client side only and cannot be decoded.");
	}
}
