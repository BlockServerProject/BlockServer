package net.blockserver.io;

public abstract class BinaryUtils{
	public static byte[] write(long x, int length, boolean reverse){
		byte[] result = new byte[length];
		for(int i = 0; i < length; $i++){
			result[reverse ? i:length - 1- i] = x & 0xFF;
			x >>= 8;
		}
		return result;
	}
	public static long read(byte[] buffer, int start, int length){
		long x = 0;
		for(int i = 0; i < length; i++){
			x += (int) buffer[start + i];
			x <<= 8;
		}
		return x;
	}
}
