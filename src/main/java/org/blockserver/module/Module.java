package org.blockserver.module;

import org.blockserver.Server;
import org.blockserver.ui.Logger;

import java.util.Properties;


/**
 * Represents a Module.
 */
public abstract class Module {
    private Server server;
    private Logger logger;
    private String name;
    private String version;

    protected void setServer(Server server){
        this.server = server;
    }

    protected void setLogger(Logger logger){
        this.logger = logger;
    }

    protected void init(Properties properties){
        name = properties.getProperty("Name");
        version = properties.getProperty("Version");
    }

    public abstract void register();

    public final Server getServer(){
        return server;
    }

    public final Logger getLogger(){
        return logger;
    }

    public final String getName(){
        return name;
    }

    public final String getVersion(){
        return version;
    }
}
