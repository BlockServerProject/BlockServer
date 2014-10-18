package org.blockserver.network.minecraft;

import java.nio.ByteBuffer;

public final class MovePlayerPacket extends BaseDataPacket{
	public int eid;
	public float x;
	public float y;
	public float z;
	public float yaw;
	public float pitch;
	public float bodyYaw;
	public boolean isTeleport = false;

	public MovePlayerPacket(byte[] buffer){
		this.bb = ByteBuffer.wrap(buffer);
	}
	
	@Override
	public void decode() {
		bb.get();
		eid = bb.getInt();
		x = bb.getFloat();
		y = bb.getFloat();
		z = bb.getFloat();
		yaw = bb.getFloat();
		pitch = bb.getFloat();
		bodyYaw = bb.getFloat();
	}

	@Override
	public void encode() {
		//PID + isTeleport + (eid, xyz, ypb)
		bb = ByteBuffer.allocate( 2 + (0x04 * 7) );
		bb.put(MOVE_PLAYER);
		bb.putInt(eid);
		bb.putFloat(x);
		bb.putFloat(y);
		bb.putFloat(z);
		bb.putFloat(yaw);
		bb.putFloat(pitch);
		bb.putFloat(bodyYaw);
		bb.put((byte) (isTeleport == true ? 0x80 : 0x00));
	}
	
}
