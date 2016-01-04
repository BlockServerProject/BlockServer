package org.blockserver.core.exceptions;

import org.blockserver.core.exceptions.node.ExceptionBuilder;

public class BlockServerException extends RuntimeException {
    public BlockServerException(String message) {
        super(message);
    }

    public BlockServerException(ExceptionBuilder factory) {
        super(factory.toString());
    }
}