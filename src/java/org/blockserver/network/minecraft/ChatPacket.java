package org.blockserver.network.minecraft;

import java.io.UnsupportedEncodingException;
import java.nio.ByteBuffer;
import java.nio.charset.Charset;

import org.blockserver.network.PacketDecodeException;

public class ChatPacket implements BaseDataPacket {
	private boolean isFromClient;
	private ByteBuffer buffer;

	public final static byte PID = PacketsID.CHAT;
	public String sender;
	public String message;

	/**
	 * To be used when the packet is Server -> Client.
	 * @param sender The person sending the message
	 * @param message The message sent.
	 */
	public ChatPacket(String sender, String message){
		isFromClient = false;
		this.sender = sender;
		this.message = message;
		int totalLength = 6+sender.length()+message.length();
		buffer = ByteBuffer.allocate(totalLength);
	}
	/**
	 * To be used when the packet is Client -> Server.
	 * @param buffer The buffer of the packet recived.
	 */
	public ChatPacket(byte[] buffer){
		isFromClient = true;
		this.buffer= ByteBuffer.wrap(buffer);
	}

	public void encode(){
		if(isFromClient != true){
			short senderLength = (short) sender.length();
			short messageLength = (short) message.length();
			buffer.put(PID);
			buffer.putShort(senderLength);
			try{
				buffer.put(sender.getBytes("UTF-8"));
				buffer.putShort(messageLength);
				buffer.put(message.getBytes(Charset.forName("UTF-8")));
			}
			catch(UnsupportedEncodingException e){
				e.printStackTrace();
			}
		}
		else{
			throw new UnsupportedOperationException("This object is to be parsed, not encoded.");
		}
	}

	public void decode(){
		if(isFromClient == true){
			if(buffer.get() != PacketsID.CHAT){
				throw new PacketDecodeException("Wrong PID!");
			}
			else{
				short senderLength = buffer.getShort();
				byte[] senderBytes = new byte[senderLength];
				buffer.get(senderBytes);
				short messageLength = buffer.getShort();
				byte[] messageBytes = new byte[messageLength];
				buffer.get(messageBytes);
				message = new String(messageBytes, Charset.forName("UTF-8"));
				sender = new String(senderBytes, Charset.forName("UTF-8"));
			}
		}
		else{
			throw new UnsupportedOperationException("This object is to be encoded, not parsed.");
		}
	}

	public ByteBuffer getBuffer(){
		return buffer;
	}
}
