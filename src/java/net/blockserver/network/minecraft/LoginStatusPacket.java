package net.blockserver.network.minecraft;


import java.nio.ByteBuffer;

public class LoginStatusPacket implements BaseDataPacket
{
	public int status;
	private ByteBuffer bb;

	public LoginStatusPacket(int status)
	{
		this.status = status;
		bb = ByteBuffer.allocate(5);
	}

	public void encode() {
		bb.put(PacketsID.LOGIN_STATUS);
		bb.putInt(this.status);
	}

	public void decode() {
		throw new UnsupportedOperationException();
	}

	public ByteBuffer getBuffer() {
		return bb;
	}
}
