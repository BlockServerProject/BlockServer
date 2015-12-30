package org.blockserver.core.modules.world.positions;

import lombok.Getter;

/**
 * Written by Exerosis!
 */
public class Vector {
    @Getter float x;
    @Getter float y;
    @Getter float z;

    public Vector(Vector vector) {
        this(vector.getX(), vector.getY(), vector.getZ());
    }

    public Vector(float x, float y, float z) {
        this.x = x;
        this.y = y;
        this.z = z;
    }
}