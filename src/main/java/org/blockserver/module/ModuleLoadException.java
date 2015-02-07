package org.blockserver.module;

/**
 * Represents a Module Loader Exception.
 */
public class ModuleLoadException extends RuntimeException{

    public ModuleLoadException(String message){
        super(message);
    }
}
