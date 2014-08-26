package org.blockserver.blocks;

import java.util.Locale;

@SuppressWarnings("serial")
public class UnknownBlockException extends Exception{	
	public UnknownBlockException(){
		this("Unknown block");
	}
	public UnknownBlockException(String message){
		super(message);
	}
	public UnknownBlockException(int id, int damage){
		super(String.format(Locale.US, "Cannot find block of ID %d and damage %d", id, damage));
	}
 
	@Override
	public String toString() {
		return getMessage();
	}
}
