package org.blockserver.core.modules.entity.entities;

import org.blockserver.core.modules.entity.Entity;
import org.blockserver.core.modules.entity.modules.ExampleEntityModule;
import org.blockserver.core.modules.world.positions.Location;

/**
 * Created by Exerosis.
 */
public class ExampleEntity extends Entity {
    public ExampleEntity(Location location) {
        super(location);
    }

    public ExampleEntity(float x, float y, float z) {
        super(x, y, z);
        addModule(new ExampleEntityModule(this));
    }
}
