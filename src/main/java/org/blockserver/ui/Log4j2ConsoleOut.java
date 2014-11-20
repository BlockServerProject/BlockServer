package org.blockserver.ui;

import org.apache.logging.log4j.LogManager;

public class Log4j2ConsoleOut implements ConsoleOut{
	private org.apache.logging.log4j.Logger logger =
			LogManager.getLogger("BlockServer");
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
