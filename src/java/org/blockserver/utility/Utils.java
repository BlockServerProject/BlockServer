package org.blockserver.utility;

import java.lang.reflect.Array;
import java.nio.ByteBuffer;
import java.util.Collection;
import java.util.List;
import java.util.Random;

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
	public static <T> T arrayRandom(T[] array){
		return arrayRandom(array, new Random());
	}
	public static <T> T arrayRandom(T[] array, Random random){
		return array[random.nextInt(array.length)];
	}
	public static <T> T[] toArray(Collection<T> coll, Class<T> clazz){
		@SuppressWarnings("unchecked")
		T[] arr = (T[]) Array.newInstance(clazz, coll.size());
		int i = 0;
		for(T item: coll){
			arr[i++] = item;
		}
		return arr;
	}

	public static void setNibble(byte x, byte y, byte z, byte nibble, byte[] buffer){
		int offset = (y << 7) + (z << 3) + (x >> 1);
		byte b = buffer[offset];
		if((x & 1) == 0){
			b &= 0xF0;
			b |= (nibble & 0x0F);
		}
		else{
			b &= 0x0F;
			b |= ((nibble << 4) & 0xF0);
		}
		buffer[offset] = b;
	}
	public static byte getNibble(byte x, byte y, byte z, byte[] buffer){
		return (byte) (0x0F & (buffer[(y << 7) + (z << 3) + (x >> 1)] >> ((x & 1) == 0 ? 0:4)));
	}
}
