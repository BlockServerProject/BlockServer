package org.blockserver.utils;

import org.apache.commons.lang3.ArrayUtils;

import java.net.SocketAddress;
import java.nio.ByteBuffer;
import java.util.zip.Deflater;

public abstract class Utils{
	public static void writeLTriad(int triad, ByteBuffer bb){
		bb.put(writeLTriad(triad));
	}
	public static byte[] writeLTriad(int triad){
		byte b1,b2,b3;
		b3 = (byte)(triad & 0xFF);
		b2 = (byte)((triad >> 8) & 0xFF);
		b1 = (byte)((triad >> 16) & 0xFF);
		return new byte[] {b3, b2, b1};
	}
	public static int readLTriad(ByteBuffer bb){
		byte[] triad = new byte[3];
		bb.get(triad);
		return readLTriad(triad);
	}
	public static int readLTriad(byte[] triad){
		return (triad[0] & 0xFF) | ((triad[1] & 0xFF) << 8) | ((triad[2] & 0x0F) << 16);
	}
	public static int readLTriad(byte[] data, int offset){
		return (data[offset] & 0xff) | (data[offset+1] & 0xff) << 8 | (data[offset+2] & 0xff) << 16;
	}
	public static boolean inArray(byte needle, byte[] haystack){
		for(byte item : haystack){
			if(item == needle){
				return true;
			}
		}
		return false;
	}
	public static <T> boolean inArray(T object, T[] array){
		for(T item : array){
			if(object.equals(item)){
				return true;
			}
		}
		return false;
	}
	public static byte[] compressBytes(byte[] uncompressed){
		Deflater def = new Deflater(7);
		byte[] buf = new byte[65536];
		def.reset();
		def.setInput(uncompressed);
		def.finish();

		int size = def.deflate(buf);
		return ArrayUtils.subarray(buf, 0, size);
	}
	public static int getPortFromSocketAddress(SocketAddress address){
		String str = address.toString();
		return Integer.parseInt(str.split(":")[1]);
	}
	public static byte[][] splitArray(byte[] array, int singleSlice){
		if (array.length <= singleSlice) {
			byte[][] singleRet = new byte[1][];
			singleRet[0] = array;
			return singleRet;
		}
		byte[][] ret = new byte[(array.length / singleSlice + (array.length % singleSlice == 0 ? 0 : 1))][];
		int pos = 0;
		int slice = 0;
		while (slice < ret.length) {
			if (pos + singleSlice < array.length) {
				ret[slice] = ArrayUtils.subarray(array, pos, singleSlice);
				pos += singleSlice;
				slice++;
			} else {
				ret[slice] = ArrayUtils.subarray(array, pos, array.length);
				pos += array.length - pos;
				slice++;
			}
		}
		return ret;
	}
}
