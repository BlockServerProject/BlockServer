package org.blockserver.api;

public class Argument<Type>{
	protected Type value;
	public Argument(Type value){
		this.value = value;
	}
	public void setValue(Type value){
		this.value = value;
	}
	public Type getValue(){
		return value;
	}
}
