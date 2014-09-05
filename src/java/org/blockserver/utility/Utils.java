package org.blockserver.utility;

import java.nio.ByteBuffer;
import java.util.List;

public class Utils{
	public static int getTriad(byte[] data, int offset){
		return (int) (data[offset++] << 16 | data[offset++] << 8  | data[offset]);
	}
	public static int getTriad(ByteBuffer bb){
		return (int) (bb.get() << 16 | bb.get() << 8 | bb.get());
	}
	public static int getLTriad(byte[] data, int offset){
		return (int) (data[offset++] | data[offset++] << 8 | data[offset] << 16);
	}

	public static byte[] putTriad(int v){
		return put(v, 3, false);
	}
	public static byte[] putLTriad(int v){
		return put(v, 3, true);
	}
	public static byte[] put(int x, int len, boolean reverse){
		byte[] buffer = new byte[len];
		int shift = (len - 1) * 8;
		for(int i = 0; i < len; i++){
			buffer[reverse ? (len - i - 1):i] = (byte) (x >> shift);
			shift -= 8;
		}
		return buffer;
	}
	public static <T> T[] arrayShift(T[] t, T[] emptyBuffer){
		System.arraycopy(t, 1, emptyBuffer, 0, t.length - 1);
		return emptyBuffer;
	}
	public static <T> T arrayShift(List<T> t){
		return t.remove(0);
	}
}
