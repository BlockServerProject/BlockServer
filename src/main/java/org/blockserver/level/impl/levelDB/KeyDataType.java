package org.blockserver.level.impl.levelDB;

public enum KeyDataType {
	TERRAIN_DATA((byte) 0x30), TILE_ENTITY_DATA((byte) 0x31), ENTITY_DATA((byte) 0x32), ONE_BYTE_DATA((byte) 0x76);

	private byte keyType;

	private KeyDataType(byte type){
		this.keyType = type;
	}

	public byte getByte(){
		return keyType;
	}
}
