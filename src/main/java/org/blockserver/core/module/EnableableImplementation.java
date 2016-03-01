package org.blockserver.core.module;


/**
 * An implementation for Enableable, all modules and anything that can be enabled/disabled
 * extend this.
 */
public abstract class EnableableImplementation implements Enableable {
    private boolean enabled;

    protected void onEnable() {} //Override in impl
    protected void onDisable() {} //Override in impl

    @Override
    public final void enable() {
        onEnable();
        enabled = true;
    }

    @Override
    public boolean isEnabled() {
        return enabled;
    }

    @Override
    public final void disable() {
        onDisable();
        enabled = false;
    }
}
