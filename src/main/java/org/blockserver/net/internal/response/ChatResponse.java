package org.blockserver.net.internal.response;

import jdk.nashorn.internal.parser.JSONParser;

public class ChatResponse extends InternalResponse{
	/**
	 * Plain message. Accepts color codes.
	 */
	public String message;
	/**
	 * JSON-encoded message
	 * <br>
	 * If {@code null}, should be automatically formatted using {@code message}
	 */
	public String formattedMessage = null;

	/**
	 * Chat message in chat box
	 */
	public final static byte POSITION_CHAT = 0;
	/**
	 * System message in chat box
	 */
	public final static byte POSITION_SYSTEM = 1;
	/**
	 * Above action bar
	 */
	public final static byte POSITION_ACTION_BAR = 2;
	/**
	 * Position of the chat to appear at. Only compatible with PC.
	 */
	public byte position;

	public void validate(){
		if(formattedMessage == null){
			String quote = JSONParser.quote(message);
			formattedMessage = "{\"text\": \"" + quote + "\"}";
		}
	}
}
