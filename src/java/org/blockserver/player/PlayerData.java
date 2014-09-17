package org.blockserver.player;

import org.blockserver.item.Inventory;
import org.blockserver.level.Level;
import org.blockserver.math.Vector3d;

public class PlayerData {
	private Level level;
	private Vector3d coords;
	private String caseName;
	private Inventory inventory;

	public PlayerData(Level level, Vector3d coords, String caseName, Inventory inv){
		if(level == null){
			NullPointerException e = new NullPointerException("Level is null");
			e.printStackTrace();
			throw e;
		}
		setLevel(level);
		setCoords(coords);
		setCaseName(caseName);
		setInventory(inv);
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

	public Inventory getInventory(){
		return inventory;
	}

	public void setInventory(Inventory inventory){
		this.inventory = inventory;
	}
}
