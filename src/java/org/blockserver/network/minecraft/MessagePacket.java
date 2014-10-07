package org.blockserver.network.minecraft;

import java.io.UnsupportedEncodingException;
import java.nio.ByteBuffer;

public class MessagePacket extends BaseDataPacket{
	private byte[] src;
	private byte[] msg;

	public MessagePacket(String msg){
		this("", msg);
	}
	public MessagePacket(String source, String message){
		if(message.length() > 0xFFFF){
			throw new StringIndexOutOfBoundsException(String.format("Message '%s' passed to MessagePacket, exceeding the maximum length (%d) by %d character(s)", message, 0xFFFF, message.length() - 0xFFFF));
//			Server.getInstance().getLogger().warning("Message '%s' was passed to MessagePacket, "
//					+ "but the message length exceeds the maximum length %d by %d character(s).",
//					message, 0xFFFF, message.length() - 0xFFFF);
//			message = message.substring(0, 0xFFFF);
		}
		try{
			msg = message.getBytes("UTF-8");
			src = source.getBytes("UTF-8");
			bb = ByteBuffer.allocate(6 + msg.length + src.length);
		}
		catch(UnsupportedEncodingException e){
			throw new RuntimeException(e);
		}
	}
	public MessagePacket(byte[] buffer){
		bb = ByteBuffer.wrap(buffer);
	}

	@Override
	public void encode(){
		bb.put(MESSAGE);
		bb.putShort((short) src.length);
		bb.put(src);
		bb.putShort((short) msg.length);
		bb.put(msg);
	}

	@Override
	public void decode(){
		bb.get();
		short senderLength = bb.getShort();
		src = new byte[senderLength];
		bb.get(src);
		short messageLength = bb.getShort();
		msg = new byte[messageLength];
		bb.get(msg);
	}

	public String getSource(){
		return new String(src);
	}
	public String getMessage(){
		return new String(msg);
	}
}
