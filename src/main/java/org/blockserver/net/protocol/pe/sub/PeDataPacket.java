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
package org.blockserver.net.protocol.pe.sub;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.nio.ByteBuffer;

import org.blockserver.io.BinaryReader;
import org.blockserver.io.BinaryUtils;
import org.blockserver.io.BinaryWriter;
import org.blockserver.net.protocol.pe.PeProtocolConst;

public abstract class PeDataPacket implements PeProtocolConst{
	private ByteBuffer bb;

	public PeDataPacket(byte[] buffer){
		bb = ByteBuffer.wrap(buffer);
	}
	public final void decode(byte[] buffer){
		try{
			_decode(new BinaryReader(new ByteArrayInputStream(buffer), BinaryUtils.LITTLE_ENDIAN));
		}catch(IOException e){
			throw new RuntimeException(e);
		}
	}
	protected void _decode(BinaryReader reader) throws IOException{
		throw new RuntimeException(getClass().getSimpleName() + " cannot be decoded");
	}
	public final byte[] encode(){
		try{
			BinaryWriter writer = new BinaryWriter(new ByteArrayOutputStream(getLength()));
			_encode(writer);
			ByteArrayOutputStream os = (ByteArrayOutputStream) writer.getOutputStream();
			return os.toByteArray();
		}catch(IOException e){
			throw new RuntimeException(e);
		}
	}
	protected void _encode(BinaryWriter writer) throws IOException{
		throw new RuntimeException(getClass().getSimpleName() + " cannot be encoded");
	}
	protected abstract int getLength();
	public final byte getPid(){
		byte pid = bb.get();
		bb.position(0);
		return pid;
	}
}
