package org.blockserver.io.bsf;

public enum BSFType{
	PLAYER(0x00);

	private int id;

	BSFType(int id){
		this.id = id;
	}
	public int getID(){
		return id;
	}

	public static BSFType get(int id){
		for(BSFType t: values()){
			if(t.getID() == id){
				return t;
			}
		}
		throw new IndexOutOfBoundsException();
	}
}
