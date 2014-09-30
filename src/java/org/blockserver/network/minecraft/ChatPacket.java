package org.blockserver.network.minecraft;

import java.io.UnsupportedEncodingException;
import java.nio.ByteBuffer;
import java.nio.charset.Charset;

public class ChatPacket extends BaseDataPacket {
	public String sender;
	public String message;

	/**
	 * To be used when the packet is Server -> Client.
	 * @param sender The person sending the message
	 * @param message The message sent.
	 */
	public ChatPacket(String sender, String message){
		this.sender = sender;
		this.message = message;
	}

	public void encode(){
		int totalLength = 6+sender.length() + message.length();
		bb = ByteBuffer.allocate(totalLength);
		short senderLength = (short) sender.length();
		short messageLength = (short) message.length();
		bb.put(CHAT);
		bb.putShort(senderLength);
		try{
			bb.put(sender.getBytes("UTF-8"));
			bb.putShort(messageLength);
			bb.put(message.getBytes(Charset.forName("UTF-8")));
		}
		catch(UnsupportedEncodingException e){
			e.printStackTrace();
		}
	}
	public void decode(){
		throw new UnsupportedOperationException();
	}
}
