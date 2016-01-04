package org.blockserver.core.exceptions.node;

public class ExceptionNode {
    private String name = "";
    private Object value;

    public ExceptionNode(String name, Object value) {
        this.name = name;
        this.value = value;
    }

    public String getName() {
        return name;
    }

    public Object getValue() {
        return value;
    }
}
