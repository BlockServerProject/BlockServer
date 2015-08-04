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
package org.blockserver.utils;

import org.apache.commons.lang3.ArrayUtils;

import java.io.*;
import java.nio.ByteBuffer;
import java.util.zip.Deflater;

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
	public static void copyResource(String resource, String dest) throws IOException {
		BufferedReader reader = new BufferedReader(new InputStreamReader(ClassLoader.getSystemResourceAsStream(resource)));
		BufferedWriter writer = new BufferedWriter(new FileWriter(dest));
		String line;
		while((line = reader.readLine()) != null){
			writer.write(line+"\n");
		}
		reader.close();
		writer.close();
	}
}
