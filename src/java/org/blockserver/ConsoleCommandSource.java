package org.blockserver;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

public interface ConsoleCommandSource{
	public class InputStreamConsoleCommandSource implements
			ConsoleCommandSource{
		private BufferedReader reader;
		public InputStreamConsoleCommandSource(InputStream is){
			this(new BufferedReader(new InputStreamReader(is)));
		}
		public InputStreamConsoleCommandSource(BufferedReader r){
			reader = r;
		}
		@Override
		public String readLine() throws IOException{
			return reader.readLine();
		}
	}

	public String readLine() throws IOException;
}
