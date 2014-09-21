package org.blockserver.network.minecraft;

import java.io.UnsupportedEncodingException;
import java.nio.ByteBuffer;
import java.nio.charset.Charset;

public class MessagePacket implements BaseDataPacket{
	private ByteBuffer bb;
	private String src;
	public String msg;
	public MessagePacket(String msg){
		this("", msg);
	}
	public MessagePacket(String src, String msg){
		this.src = src;
		if(msg.length() > 0xFFFF){
			throw new StringIndexOutOfBoundsException(String.format("Message '%s' passed to MessagePacket, exceeding the maximum length (%d) by %d character(s)", msg, 0xFFFF, msg.length() - 0xFFFF));
//			Server.getInstance().getLogger().warning("Message '%s' was passed to MessagePacket, "
//					+ "but the message length exceeds the maximum length %d by %d character(s).",
//					message, 0xFFFF, message.length() - 0xFFFF);
//			message = message.substring(0, 0xFFFF);
		}
		this.msg = msg;
	}
	public MessagePacket(byte[] buffer){
		bb = ByteBuffer.wrap(buffer);
	}

	@Override
	public void encode(){
		try{
			byte[] message = msg.getBytes("UTF-8");
			byte[] source = src.getBytes("UTF-8");
			bb = ByteBuffer.allocate(6 + message.length + source.length);
			bb.put(PacketsID.MESSAGE);
			bb.putShort((short) source.length);
			bb.put(source);
			bb.putShort((short) message.length);
			bb.put(message);
		}
		catch(UnsupportedEncodingException e){
			e.printStackTrace();
		}
	}

	@Override
	public void decode(){
		bb.get();
		short senderLength = bb.getShort();
		byte[] senderBytes = new byte[senderLength];
		bb.get(senderBytes);
		short messageLength = bb.getShort();
		byte[] messageBytes = new byte[messageLength];
		bb.get(messageBytes);
		msg = new String(messageBytes, Charset.forName("UTF-8"));
		src = new String(senderBytes, Charset.forName("UTF-8"));

	}

	@Override
	public ByteBuffer getBuffer(){
		return bb;
	}
}
