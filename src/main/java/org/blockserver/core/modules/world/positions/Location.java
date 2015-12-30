package org.blockserver.core.modules.world.positions;

import lombok.Getter;

/**
 * Written by Exerosis!
 */
public class Location extends Vector {
    @Getter long yaw;
    @Getter long pitch;

    public Location(Vector vector) {
        super(vector);
    }

    public Location(Location location) {
        this(location.getX(), location.getY(), location.getZ(), location.getYaw(), location.getPitch());
    }

    public Location(float x, float y, float z) {
        super(x, y, z);
    }

    public Location(float x, float y, float z, long yaw, long pitch) {
        this(x, y, z);
        this.yaw = yaw;
        this.pitch = pitch;
    }
}