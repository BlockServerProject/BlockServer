package org.blockserver.io.bsf;

import java.io.IOException;
import java.io.OutputStream;
import java.util.Map;

import org.blockserver.io.BinaryWriter;
import org.blockserver.item.Inventory;
import org.blockserver.item.Item;

public class BSFWriter extends BinaryWriter{
	public final static byte[] HEADER = {
		0x50, (byte) 0xb5, (byte) 0xff
	};
	public final static byte[] FOOTER = {
		(byte) 0xe0, (byte) 0xb5, (byte) 0xff
	};

	protected boolean inited = false;

	protected BSFType type;
	protected BSFVersion version;

	public BSFWriter(OutputStream os, BSFType type) throws IOException{
		this(os, type, BSFVersion.DOOR);
	}
	public BSFWriter(OutputStream os, BSFType type, BSFVersion version) throws IOException{
		this(os, type, version, true);
	}
	public BSFWriter(OutputStream os, BSFType type, boolean init) throws IOException{
		this(os, type, BSFVersion.DOOR, init);
	}
	public BSFWriter(OutputStream os, BSFType type, BSFVersion version, boolean init) throws IOException{
		super(os);
		this.type = type;
		this.version = version;
		if(init){
			init();
		}
	}

	public void init() throws IOException{
		if(inited){
			throw new IllegalStateException("Writer already initialized");
		}
		inited = true;
		writeBytes(HEADER);
		writeByte((byte) type.getID());
	}

	public void writeInventory(Inventory inv) throws IOException{
		writeInt(inv.getSize());
		for(Item item: inv){
			writeItem(item);
		}
	}

	public void writeItem(Item item) throws IOException{
		writeShort((short) item.getID());
		writeByte((byte) item.getDamage());
		writeByte((byte) item.getCount());
		writeMap(item.getMetadata());
	}

	public void writeMap(Map<CharSequence, String> map) throws IOException{
		writeInt(map.size());
		for(Map.Entry<CharSequence, String> entry: map.entrySet()){
			writeString(entry.getKey().toString());
			writeString(entry.getValue());
		}
	}

	@Override
	public void close() throws IOException{
		writeBytes(FOOTER);
		super.close();
	}
}
