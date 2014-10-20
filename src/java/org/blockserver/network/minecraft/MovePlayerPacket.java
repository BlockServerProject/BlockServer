package org.blockserver.network.minecraft;

import java.nio.ByteBuffer;

/**
 * MovePlayerPacket, Two-Way
 * @author jython234
 *
 */
public class MovePlayerPacket implements BaseDataPacket {
	private ByteBuffer buffer;
	
	public final byte PID = PacketsID.MOVE_PLAYER;
	public int eid;
	public float x;
	public float y;
	public float z;
	
	public float yaw;
	public float pitch;
	

	public MovePlayerPacket(byte[] buffer){
		this.buffer = ByteBuffer.wrap(buffer);
	}
	
	public MovePlayerPacket(int eid, float x, float y, float z, float yaw, float pitch){
		this.buffer = ByteBuffer.allocate(25);
		
		this.eid = eid;
		this.x = x;
		this.y = y;
		this.z = z;
		this.yaw = yaw;
		
		this.pitch = pitch;
	}
	
	public void encode(){
		buffer.put(PID);
		buffer.putInt(eid);
		
		buffer.putFloat(x);
		buffer.putFloat(y);
		buffer.putFloat(z);
		buffer.putFloat(yaw);
		buffer.putFloat(pitch);
	}
	
	public void decode(){
		buffer.get(); //PID
		
		this.eid = buffer.getInt();
		this.x = buffer.getFloat();
		this.y = buffer.getFloat();
		this.z = buffer.getFloat();
		this.yaw = buffer.getFloat();
		this.pitch = buffer.getFloat();
	}
	
	public ByteBuffer getBuffer(){
		return this.buffer;
	}

}
