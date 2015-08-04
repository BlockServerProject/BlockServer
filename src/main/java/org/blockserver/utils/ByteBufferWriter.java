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
