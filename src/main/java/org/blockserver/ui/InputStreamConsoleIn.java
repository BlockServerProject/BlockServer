package org.blockserver.ui;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;

public class InputStreamConsoleIn implements ConsoleIn{
	private BufferedReader br;
	private boolean close;
	public static InputStreamConsoleIn fromConsole(){
		return new InputStreamConsoleIn(System.in, false);
	}
	public InputStreamConsoleIn(InputStream is, boolean close){
		this(new InputStreamReader(is), close);
	}
	public InputStreamConsoleIn(Reader reader, boolean close){
		BufferedReader br;
		if(reader instanceof BufferedReader){
			br = (BufferedReader) reader;
		}
		else{
			br = new BufferedReader(reader);
		}
		this.br = br;
		this.close = close;
	}
	@Override
	public String read(){
		try{
			return br.readLine().trim();
		}
		catch(IOException e){
			e.printStackTrace();
			return null;
		}
	}
	@Override
	protected void finalize() throws Throwable{
		super.finalize();
		if(close){
			br.close();
		}
	}
}
