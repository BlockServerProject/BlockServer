package net.blockserver.player;

import net.blockserver.level.Level;
import net.blockserver.math.Vector3d;

public class PlayerData {

    private Level level;
    private Vector3d coords;
    private String caseName;
    public PlayerData(Level level, Vector3d coords, String caseName) {
        setLevel(level);
        setCoords(coords);
        setCaseName(caseName);
    }
    public Level getLevel() {
        return level;
    }
    public void setLevel(Level level) {
        this.level = level;
    }
    public Vector3d getCoords() {
        return coords;
    }
    public void setCoords(Vector3d coords) {
        this.coords = coords;
    }
    public String getCaseName() {
        return caseName;
    }
    public void setCaseName(String caseName) {
        this.caseName = caseName;
    }

}
