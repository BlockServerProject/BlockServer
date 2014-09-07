package org.blockserver.io.bsf;

import java.util.Locale;

public enum BSFVersion{
	DOOR(0);
	private int id;
	BSFVersion(int id){
		this.id = id;
	}
	public int getID(){
		return id;
	}
	public char getVersionString(){
		return (char) id;
	}
	public String getName(){
		char[] name = new char[name().toLowerCase(Locale.US).length()];
		name().getChars(0, name.length, name, 0);
		String out = new String();
		boolean cap = true;
		for(int i = 0; i < name.length; i++){
			char c = name[i];
			if(c == '_'){ // emotions?
				c = ' ';
			}
			out += cap ? Character.toUpperCase(c):c;
			if(c == ' '){
				cap = true;
			}
		}
		return out;
	}
	public static BSFVersion get(byte b){
		for(BSFVersion v: values()){
			if(v.getID() == b){
				return v;
			}
		}
		return null;
	}
}
