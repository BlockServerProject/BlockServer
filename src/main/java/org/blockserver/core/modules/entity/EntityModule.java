package org.blockserver.core.modules.entity;

import lombok.Getter;
import org.blockserver.core.module.Module;

/**
 * Created by Exerosis.
 */
public class EntityModule implements Module {
    @Getter private final Entity entity;

    public EntityModule(Entity entity) {
        this.entity = entity;
    }

}