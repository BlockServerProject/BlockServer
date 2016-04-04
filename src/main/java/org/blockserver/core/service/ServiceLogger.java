package org.blockserver.core.service;

import org.slf4j.Logger;

/**
 * Utility class for logging used by services.
 *
 * @author BlockServer Project
 */
public class ServiceLogger {
    private final Service service;
    private final Logger log;

    public ServiceLogger(Service service, Logger log) {
        this.service = service;
        this.log = log;
    }

    public void debug(String message) {
        this.log.debug(service.getName() + ": ");
    }

    public void info(String message) {
        this.log.info(service.getName() + ": ");
    }

    public void warn(String message) {
        this.log.warn(service.getName() + ": ");
    }

    public void error(String message) {
        this.log.error(service.getName() +": ");
    }
}
