package net.blockserver.level.generator;

import java.util.Random;

public enum GenerationSettings{

    CAVES(Boolean.class, true),
    DECORATION(Boolean.class, true),
    DEGREE_OF_FLAT(Integer.class, 0),
    MINESHAFT(Boolean.class, true),
    SEED(Long.class, 0l),
    SIZE(Integer.class, 0),
    VILLAGES(Boolean.class, true);

    private Class<?> type;
    private Object def;

    private <T> GenerationSettings(Class<T> type, T def) {
        this.type = type;
        this.def = def;
        if(this.name().equals("SEED"))
            this.def = (new Random()).nextLong();
    }

    public Class<?> getType(){
        return this.type;
    }

    public Object getDefault(){
        return this.def;
    }
}