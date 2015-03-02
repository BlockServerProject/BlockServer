package org.blockserver.net.internal.request;

public class ChatRequest extends InternalRequest{
	public String message;
	public String author;
	public byte position; // should be useless
}
