package org.blockserver.utility;

public class StringReader{
	private String str;
	private int pointer = 0;
	public StringReader(String str){
		this.str = str;
	}
	public char read(){
		return str.charAt(pointer++);
	}
	public String tillNext(char ch){
		int point = str.substring(pointer).indexOf(ch);
		String result = str.substring(pointer, pointer + point); // **** it, I thought it is PHP and assumed the 2nd param to be length.
		pointer = point + 1;
		return result;
	}
	public boolean feof(){
		return pointer == str.length();
	}
}
