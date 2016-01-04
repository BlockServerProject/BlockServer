package org.blockserver.core.exceptions.notfound;

import me.exerosis.reflection.exceptions.ReflectException;
import me.exerosis.reflection.exceptions.node.ExceptionBuilder;
import me.exerosis.reflection.exceptions.node.ExceptionNode;

/**
 * Written by Exerosis!
 */
public class FieldNotFoundException extends ReflectException {

    public FieldNotFoundException(Class<?> clazz, String name, Class<?> type, int pos) {
        super(new ExceptionBuilder("Field not found!", getExceptionNodes(clazz, name, type, pos)));
    }

    private static ExceptionNode[] getExceptionNodes(Class<?> clazz, String name, Class<?> type, int pos) {
        ExceptionNode[] nodes = new ExceptionNode[4];
        if (clazz != null)
            nodes[0] = new ExceptionNode("Class", clazz.getSimpleName());
        if (type != null)
            nodes[2] = new ExceptionNode("Type", type.getSimpleName());

        nodes[1] = new ExceptionNode("Name", name);
        nodes[3] = new ExceptionNode("Position", pos == -1 ? "Not Specified" : pos);
        return nodes;
    }
}