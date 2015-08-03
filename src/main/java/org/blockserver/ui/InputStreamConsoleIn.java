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
package org.blockserver.ui;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.ArrayList;

public class InputStreamConsoleIn implements ConsoleIn{
	public final static byte FLAG_NONE = 0;
	public final static byte FLAG_CLOSE_ONLY = 0b01;
	public final static byte FLAG_THREADED_ONLY = 0b10;
	private BufferedReader br;
	private boolean close, threaded;
	private ReaderThread readerThread = null;
	
	public static InputStreamConsoleIn fromConsole(){
		return new InputStreamConsoleIn(System.in, FLAG_THREADED_ONLY);
	}
	public InputStreamConsoleIn(InputStream is, byte flags){
		this(new InputStreamReader(is), flags);
	}
	public InputStreamConsoleIn(Reader reader, byte flags){
		BufferedReader br;
		if(reader instanceof BufferedReader){
			br = (BufferedReader) reader;
		}
		else{
			br = new BufferedReader(reader);
		}
		this.br = br;
		close = (flags & FLAG_CLOSE_ONLY) == FLAG_CLOSE_ONLY;
		if(threaded = (flags & FLAG_THREADED_ONLY) == FLAG_THREADED_ONLY){
			readerThread = new ReaderThread();
		}
	}

	@Override
	public String read(){
		try{
			return threaded ? readerThread.shift():br.readLine();
		}catch(IOException e){
			e.printStackTrace();
			return null;
		}
	}
	@Override
	public void close(boolean isEmergency){
		if(threaded){
			readerThread.end(!isEmergency);
		}
		try{
			if(close){
				br.close();
			}
		}catch(IOException e){
			e.printStackTrace();
		}
	}
	private class ReaderThread extends Thread{
		private final ArrayList<String> lines = new ArrayList<>();
		private boolean stopRequested = false, stopped = false;
		@Override
		public void run(){
			while(!stopRequested){
				try{
					String line = br.readLine();
					if(line != null){
						synchronized(lines){
							lines.add(line);
						}
					}
				}catch(IOException e){
					e.printStackTrace();
				}
			}
			stopped = true;
		}
		public String shift(){
			synchronized(lines){
				return lines.remove(0);
			}
		}
		public void end(){
			end(true);
		}
		public void end(boolean wait){
			stopRequested = true;
			if(wait){
				while(!stopped){}
			}
		}
	}
}
