package org.blockserver.core.exceptions.notfound;

import me.exerosis.reflection.exceptions.ReflectException;
import me.exerosis.reflection.exceptions.node.ExceptionBuilder;

public class NotFoundException extends ReflectException {
    public NotFoundException(String message) {
        super(message);
    }

    public NotFoundException(ExceptionBuilder factory) {
        this(factory.toString());
    }
}