package org.blockserver.ui;

import java.io.PrintWriter;
import java.io.StringWriter;

public class Logger{
	private final ConsoleOut out;
	public Logger(ConsoleOut out){
		this.out = out;
	}

	public void trace(String msg, Object... args){
		synchronized(out){
			out.trace(String.format(msg, args));
		}
	}
	public void debug(String msg, Object... args){
		synchronized(out){
			out.debug(String.format(msg, args));
		}
	}
	public void info(String msg, Object... args){
		synchronized(out){
			out.info(String.format(msg, args));
		}
	}
	public void warning(String msg, Object... args){
		synchronized(out){
			out.warning(String.format(msg, args));
		}
	}
	public void error(String msg, Object... args){
		synchronized(out){
			out.error(String.format(msg, args));
		}
	}
	public void fatal(String msg, Object... args){
		synchronized(out){
			out.fatal(String.format(msg, args));
		}
	}
	public void buffer(String prefix, byte[] buffer, String suffix){
		StringBuilder out = new StringBuilder(buffer.length * 3);
		for(byte bite: buffer){
			String string = Integer.toHexString(bite & 0xFF);
			while(string.length() < 2){
				string = "0" + string;
			}
			out.append(string);
			out.append(',');
		}
		debug(prefix + "0x" + out.toString() + suffix);
	}
	public void trace(Throwable t){
		StringWriter writer = new StringWriter();
		t.printStackTrace(new PrintWriter(writer));
		for(String str: writer.toString().split("[\r\n]+")){
			trace(str);
		}
	}
}
