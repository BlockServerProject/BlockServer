package org.blocksever.test.core;

import java.util.HashMap;
import java.util.Map;

public class Server extends Module {
    public static void main(String[] args) {
        new Server().enable();
    }
    private final Map<String, Module> moduleLoaders = new HashMap<>();

    public Server() {
        moduleLoaders.put("CoreModuleLoader", new CoreModuleLoader());
    }

    @Override
    protected void onEnable() {
        System.out.println("Starting the Server!");
        moduleLoaders.values().forEach(Module::enable);
        System.out.println("Enabled the Server!");
    }

    @Override
    protected void onDisable() {
        System.out.println("Stopping the Server!");
        moduleLoaders.values().forEach(Module::disable);
        System.out.println("Disabled the Server!");
    }
}