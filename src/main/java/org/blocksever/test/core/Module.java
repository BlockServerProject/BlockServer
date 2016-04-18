package org.blocksever.test.core;

public class Module implements Enableable {
    private boolean enabled = false;

    @Override
    public void enable() {
        if (isEnabled())
            return;
        onEnable();
        enabled = true;
    }

    @Override
    public void disable() {
        if (!isEnabled())
            return;
        onDisable();
        enabled = false;
    }

    @Override
    public boolean isEnabled() {
        return enabled;
    }

    protected void onEnable() {

    }


    protected void onDisable() {

    }
}