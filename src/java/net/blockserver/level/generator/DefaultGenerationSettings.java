package net.blockserver.level.generator;

import java.util.Random;

public enum DefaultGenerationSettings implements GenerationSettings{

    CAVES(Boolean.class, true),
    DECORATION(Boolean.class, true),
    MINESHAFT(Boolean.class, true),
    SEED(Long.class, 0l){
        @Override
        void construct(){
            this.def = (new Random()).nextLong();
        }
    },
    SIZE(Integer.class, 0),
    VILLAGES(Boolean.class, true);

    private Class<?> type;
    protected Object def;
    private Object value;

    private <T> DefaultGenerationSettings(Class<T> type, T def) {
        this.type = type;
        this.def = def;

        construct();

        this.value = def;
    }

    public Class<?> getType(){
        return this.type;
    }

    public Object getDefault(){
        return this.def;
    }

    public DefaultGenerationSettings setValue(Object value){
        if(!type.isAssignableFrom(value.getClass()))
            throw new IllegalArgumentException(type.getSimpleName() + " Type is not " + value.getClass().getSimpleName());
        this.value = value;
        return this;
    }

    public Object getValue(){
        return this.value;
    }

    void construct(){}
}