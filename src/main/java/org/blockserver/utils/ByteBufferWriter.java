package org.blockserver.utils;

import java.io.ByteArrayOutputStream;

import org.blockserver.io.BinaryWriter;

public class ByteBufferWriter extends BinaryWriter{
	public ByteBufferWriter(int capacity){
		super(new ByteArrayOutputStream(capacity));
	}
	public byte[] getBuffer(){
		return ((ByteArrayOutputStream) os).toByteArray();
	}
}
