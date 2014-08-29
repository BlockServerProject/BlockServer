package org.blockserver.network.minecraft;

import java.nio.ByteBuffer;
import java.nio.charset.Charset;

import org.blockserver.Server;

public class MessagePacket implements BaseDataPacket{
	private ByteBuffer bb;
	private String msg;
	public MessagePacket(String message){
		if(message.length() > 0xFF){
//			throw new RuntimeException("Message '" + message +
//					"' was passed to MessagePacket, but the message length exceeds "
//					+ "the maximum length (255) by " + Integer.toString(message.length() - 255) 
//					+ " character(s).");
			Server.getInstance().getLogger().warning("Message '%s' was passed to MessagePacket, "
					+ "but the message length exceeds the maximum length %d by %d character(s).",
					message, 0xFF, message.length() - 0xFF);
			message = message.substring(0, 0xFF);
		}
		msg = message;
	}

	@Override
	public void encode(){
		bb = ByteBuffer.allocate(3 + msg.getBytes(Charset.forName("UTF-8")).length);
		bb.put(PacketsID.MESSAGE);
		bb.putShort((short) msg.length());
		bb.put(msg.getBytes(Charset.forName("UTF-8")));
	}

	@Override
	public void decode(){
		int length = bb.getShort();
		byte[] buffer = new byte[length];
		bb.get(buffer, 0, length);
	}

	@Override
	public ByteBuffer getBuffer(){
		return bb;
	}
}
