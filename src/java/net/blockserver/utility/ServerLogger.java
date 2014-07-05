package net.blockserver.utility;

import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Level;

public class ServerLogger
{
    private Logger logger;

    public ServerLogger()
    {
        this.logger = LogManager.getLogger("Server");
    }

    public void log(Level lvl, String format, Object... message){
        this.logger.log(lvl, String.format(format, message));
    }

    public void trace(String format, Object... message){
        this.logger.log(Level.TRACE, format, message);
    }

    public void fatal(String format, Object... message){
        this.logger.log(Level.FATAL, format, message);
    }

    public void warning(String format, Object... message){
        this.logger.log(Level.WARN, format, message);
    }

    public void error(String format, Object... message){
        this.logger.log(Level.ERROR, format, message);
    }

    public void info(String format, Object... message){
        this.logger.log(Level.INFO, format, message);
    }

    public void debug(String format, Object... message){
        this.logger.log(Level.DEBUG, format, message);
    }

}
