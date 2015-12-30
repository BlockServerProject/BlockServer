package org.blockserver.core.modules.world;

import lombok.Getter;
import org.blockserver.core.modules.player.Player;
import org.blockserver.core.modules.world.positions.Vector;

/**
 * Written by Exerosis!
 */
public class Block {
    @Getter private World world;
    @Getter private Material material;
    @Getter private byte lightLevel;
    @Getter private Vector vector;

    public void setLightLevel(byte lightLevel) {
        this.lightLevel = lightLevel;
    }

    public void setMaterial(Material material) {
        this.material = material;
        for (Player player : world.getPlayers()) {
        }
    }
}