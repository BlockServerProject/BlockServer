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
