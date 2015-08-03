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

import org.apache.logging.log4j.LogManager;

public class Log4j2ConsoleOut implements ConsoleOut{
	private org.apache.logging.log4j.Logger logger;

	public Log4j2ConsoleOut(String loggerName){
		logger = LogManager.getLogger(loggerName);
	}

	public Log4j2ConsoleOut(){
		logger = LogManager.getLogger("BlockServer");
	}

	@Override
	public void trace(String msg){
		logger.trace(msg);
	}

	@Override
	public void debug(String msg){
		logger.debug(msg);
	}

	@Override
	public void info(String msg){
		logger.info(msg);
	}

	@Override
	public void warning(String msg){
		logger.warn(msg);
	}

	@Override
	public void error(String msg){
		logger.error(msg);
	}

	@Override
	public void fatal(String msg){
		logger.fatal(msg);
	}
}
