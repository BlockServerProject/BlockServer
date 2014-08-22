package net.blockserver.level.generator;

public interface GenerationSettings {

    public Class<?> getType();

    public Object getDefault();

    public Enum<?> setValue(Object value);

    public Object getValue();

}
