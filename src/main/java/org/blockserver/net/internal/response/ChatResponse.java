/**
 * This file is part of BlockServer.
 *
 * BlockServer is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * BlockServer is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with BlockServer.  If not, see <http://www.gnu.org/licenses/>.
 */
package org.blockserver.net.internal.response;

import jdk.nashorn.internal.parser.JSONParser;

public class ChatResponse extends InternalResponse{
	/**
	 * Plain message. Accepts color codes.
	 */
	public String message = "";
	/**
	 * JSON-encoded message. Only for PC.
	 * <br>
	 * If {@code null}, it should be automatically formatted with {@code message} by a protocol
	 * response adapter that uses it.
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

	/**
	 * Author field. Displays as {@code <author> message}
	 * <br>
	 * Response adapters for protocols that don't have the {@code author} field
	 * should inject the field into {@code message} by calling {@code injectAuthor()}
	 * (before calling {@code validate()}, if necessary).
	 */
	public String author = "";

	public void validate(){
		if(formattedMessage == null){
			String quote = JSONParser.quote(message);
			formattedMessage = "{\"text\": \"" + quote + "\"}";
		}
	}
	public void injectAuthor(){
		if(!author.isEmpty()){
			message = "<" + author + ">" + message;
		}
	}
}
