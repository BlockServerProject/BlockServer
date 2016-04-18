package org.blocksever.test.core.test;

import org.blocksever.test.core.Module;

/**
 * Created by Exerosis.
 */
public class TestModule2 extends Module {
    @Override
    protected void onEnable() {
        System.out.println("Enabled test module number 2");
    }

    @Override
    protected void onDisable() {
        System.out.println("Disabled test module number 2");
    }
}
