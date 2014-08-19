package net.blockserver.utility;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class ServerLogger
{
    private Logger logger;

    public ServerLogger()
    {
        logger = LogManager.getLogger("Server");
    }

    public void log(Level lvl, String format, Object... message){
        logger.log(lvl, format, message);
    }

    public void trace(String format, Object... message){
        logger.log(Level.TRACE, String.format(format, message));
    }

    public void fatal(String format, Object... message){
        logger.log(Level.FATAL, String.format(format, message));
    }

    public void warning(String format, Object... message){
        logger.log(Level.WARN, String.format(format, message));
    }

    public void error(String format, Object... message){
        logger.log(Level.ERROR, String.format(format, message));
    }

    public void info(String format, Object... message){
        logger.log(Level.INFO, String.format(format, message));
    }

    public void debug(String format, Object... message){
        logger.log(Level.DEBUG, String.format(format, message));
    }

}
