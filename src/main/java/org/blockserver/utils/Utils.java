package org.blockserver.utils;

import org.apache.commons.lang3.ArrayUtils;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.util.zip.Deflater;
import java.util.zip.DeflaterOutputStream;

public abstract class Utils{
	public static void writeLTriad(int triad, ByteBuffer bb){
		bb.put(writeLTriad(triad));
	}
	public static byte[] writeLTriad(int triad){
		return new byte[]{
				(byte) (triad & 0x0000FF),
				(byte) ((triad & 0x00FF00) >> 8),
				(byte) ((triad & 0xFF0000) >> 16)
		};
	}
	public static int readLTriad(ByteBuffer bb){
		byte[] triad = new byte[3];
		bb.get(triad);
		return readLTriad(triad);
	}
	public static int readLTriad(byte[] triad){
		return triad[0]
				+ (triad[1] << 8)
				+ (triad[2] << 16);
	}
	public static int readLTriad(byte[] data, int offset){
		return (data[offset] & 0xff) | (data[offset+1] & 0xff) << 8 | (data[offset+2] & 0xff) << 16;
	}
	public static boolean inArray(byte needle, byte[] haystack){
		for(byte item: haystack){
			if(item == needle){
				return true;
			}
		}
		return false;
	}
	public static <T> boolean inArray(T object, T[] array){
		for(T item: array){
			if(object.equals(item)){
				return true;
			}
		}
		return false;
	}
	public static byte[] compressBytes(byte[] uncompressed) throws IOException {
		Deflater def = new Deflater(7);
		byte[] buf = new byte[65536];
		def.reset();
		def.setInput(uncompressed);
		def.finish();

		int size = def.deflate(buf);
		return ArrayUtils.subarray(buf, 0, size);
	}
}
