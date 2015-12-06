package org.blockserver.core.modules.logging;

import org.blockserver.core.Server;
import org.blockserver.core.module.Module;
import org.omg.PortableInterceptor.SYSTEM_EXCEPTION;

/**
 * TODO: Implement SLF4j and/or log4j2
 * @author BlockServer Team
 */
public class LoggingModule extends Module{

    public LoggingModule(Server server) {
        super(server);
    }

    public void info(String message) {
        System.out.println("[INFO]: "+message);
    }

    public void warn(String message) {
        System.out.println("[WARN]: "+message);
    }

    public void error(String message) {
        System.err.println("[ERROR]: "+message);
    }
}
