package org.blockserver.network.protocol.pocket;

import java.io.UnsupportedEncodingException;
import java.nio.BufferOverflowException;
import java.nio.ByteBuffer;

import javax.xml.bind.annotation.adapters.HexBinaryAdapter;

/**
 * An extention of a ByteBuffer to support extra PE Protocol functions.
 * @author BlockServerProject
 *
 */
public class PEByteBuffer {
	
	public final byte[] MAGIC = hexToBytes("000ffff00fefefefefdfdfdfd12345678");
	
	private ByteBuffer buf;
	private boolean wrapped;
	
	/**
	 * Create a new PEByteBuffer wrapped around a byte array.
	 * @param data The data to be wrapped.
	 */
	public PEByteBuffer(byte[] data){
		wrapped = true;
		buf = ByteBuffer.wrap(data);
	}
	
	/**
	 * Create a new Empty PEByteBuffer based on a length.
	 * @param length
	 */
	public PEByteBuffer(int length){
		wrapped = false;
		buf = ByteBuffer.allocate(length);
	}
	
	/*
	 * ---------------------
	 * Special methods
	 * ---------------------
	 */
	
	/**
	 * Put a string into the buffer.
	 * @param string The string to be put.
	 */
	public void putString(String string){
		try{
			int stringlen = string.length();
			buf.putShort((short) stringlen);
			buf.put(string.getBytes("UTF-8")); //Not sure what charset, use UTF-8 maybe?
		} catch(UnsupportedEncodingException e){
			throw new RuntimeException(e.getMessage());
		}
	}
	/**
	 * Put MAGIC into the buffer!
	 */
	public void putMAGIC(){
		buf.put(MAGIC);
	}
	
	/*
	 * -----------------------
	 * Non-Special methods. (PUT)
	 * -----------------------
	 */
	
	/**
	 * Put a byte into the buffer.
	 * @param p The byte.
	 */
	public void putByte(byte p){
		buf.put(p);
	}
	/**
	 * Put an integer into the buffer.
	 * @param p The integer.
	 */
	public void putInt(int p){
		buf.putInt(p);
	}
	/**
	 * Put a short into the buffer.
	 * @param p The short.
	 */
	public void putShort(short p){
		buf.putShort(p);
	}
	/**
	 * Put a long into the buffer.
	 * @param p The long.
	 */
	public void putLong(long p){
		buf.putLong(p);
	}
	
	/*
	 * -----------------------
	 * Special methods. (GET)
	 * -----------------------
	 */
	
	/**
	 * Get a string from the buffer (short + bytes).
	 * @return The string.
	 */
	public String getString(){
		String str = null;
		
		int len = buf.getShort();
		byte[] strBytes = new byte[len];
		buf.get(strBytes);
		str = new String(strBytes);
		
		return str;
	}
	
	/**
	 * Recieve MAGIC from the buffer! (16 bytes).
	 */
	public void getMAGIC(){
		buf.get(new byte[16]);
	}
	
	
	/*
	 * -----------------------
	 * Non-Special methods. (GET)
	 * -----------------------
	 */
	
	/**
	 * Get a byte from the buffer.
	 * @return A byte.
	 */
	public byte getByte(){
		return buf.get();
	}
	/**
	 * Get an integer from the buffer.
	 * @return An integer.
	 */
	public int getInt(){
		return buf.getInt();
	}
	/**
	 * Get a short from the buffer.
	 * @return A short.
	 */
	public short getShort(){
		return buf.getShort();
	}
	/**
	 * Get a long from the buffer.
	 * @return A long.
	 */
	public long getLong(){
		return buf.getLong();
	}
	
	/**
	 * Return a representation of this buffer as a byte array.
	 * @return The buffer in a byte array.
	 */
	public byte[] toBytes(){
		return buf.array();
	}
	
	
	
	/*
	 * ------------------
	 * Utils
	 * -------------------
	 */
	private byte[] hexToBytes(String hex){
		int len = hex.length();
	    byte[] data = new byte[len / 2];
	    for (int i = 0; i < len; i += 2) {
	        data[i / 2] = (byte) ((Character.digit(hex.charAt(i), 16) << 4)
	                             + Character.digit(hex.charAt(i+1), 16));
	    }
	    return data;
	}

}
