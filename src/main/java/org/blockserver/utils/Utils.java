package org.blockserver.utils;

import java.nio.ByteBuffer;

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
}
