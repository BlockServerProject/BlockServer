package org.blockserver.ui;

public class Logger{
	private ConsoleOut out;
	public Logger(ConsoleOut out){
		this.out = out;
	}
	public void trace(String msg, Object... args){
		out.trace(String.format(msg, args));
	}
	public void debug(String msg, Object... args){
		out.debug(String.format(msg, args));
	}
	public void info(String msg, Object... args){
		out.info(String.format(msg, args));
	}
	public void warning(String msg, Object... args){
		out.warning(String.format(msg, args));
	}
	public void error(String msg, Object... args){
		out.error(String.format(msg, args));
	}
	public void fatal(String msg, Object... args){
		out.fatal(String.format(msg, args));
	}
	public void buffer(String prefix, byte[] buffer, String suffix){
		StringBuilder out = new StringBuilder(buffer.length * 2);
		for(byte bite: buffer){
			String string = Integer.toHexString(bite);
			while(string.length() < 2){
				string = "0" + string;
			}
			out.append(string);
		}
		debug(prefix + "0x" + out.toString() + suffix);
	}
}
