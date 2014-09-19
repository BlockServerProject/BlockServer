package org.blockserver.player;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.BufferUnderflowException;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import org.blockserver.GeneralConstants;
import org.blockserver.io.bsf.BSF;
import org.blockserver.io.bsf.BSFReader;
import org.blockserver.io.bsf.BSFWriter;
import org.blockserver.item.Inventory;
import org.blockserver.level.Level;
import org.blockserver.math.Vector3d;
import org.blockserver.objects.IInventory;
import org.blockserver.objects.IItem;

public class BSFPlayerDatabase extends PlayerDatabase implements GeneralConstants{
	private File folder;

	@Override
	@SuppressWarnings("unchecked")
	protected PlayerData loadPlayer(Player player, String name){
		File file = getPlayerFile(name);
		if(file.exists()){
			try{
				FileInputStream stream = new FileInputStream(file);
				BSFReader reader = new BSFReader(stream);
				Map<String, Object> data = reader.readAll();
				reader.close();
				String caseName = (String) data.get(BSF.P_CASE_NAME);
				double[] v = (double[]) data.get(BSF.P_VECTORS);
				Vector3d coords = new Vector3d(v[0], v[1], v[2]);
				Level level = getServer().getLevel((String) data.get(BSF.P_WORLD_NAME), true, false);
				Inventory inv = new Inventory((IInventory<? extends IItem>) data.get(BSF.P_I_INVENTORY), player, getServer());
				return new PlayerData(level, coords, caseName, inv);
			}
			catch(IOException e){
				e.printStackTrace();
			}
			catch(BufferUnderflowException e){
				return dummy(player, name);
			}
		}
		else{
			throw new RuntimeException("Tried to load a player file that doesn't exist.");
		}
		return dummy(player, name);
	}

	@Override
	protected void savePlayer(PlayerData data){
		File file = getPlayerFile(data.getCaseName());
		try{
			FileOutputStream out = new FileOutputStream(file);
			BSFWriter writer = new BSFWriter(out, BSF.Type.PLAYER);
			Map<String, Object> args = new HashMap<String, Object>(4);
			args.put(BSF.P_CASE_NAME, data.getCaseName());
			args.put(BSF.P_VECTORS, data.getCoords().toArray());
			args.put(BSF.P_WORLD_NAME, data.getLevel().getName());
			args.put(BSF.P_I_INVENTORY, data.getInventory());
			writer.writeAll(args);
			writer.close();
		}
		catch(IOException e){
			e.printStackTrace();
		}
	}

	@Override
	public void initialize(){
		folder = getServer().getPlayersDir();
		folder.mkdirs();
	}

	public File getPlayerFile(String name){
		return new File(folder, name.toLowerCase(Locale.US) + ".bsf"); // Block Server Format
	}
}
