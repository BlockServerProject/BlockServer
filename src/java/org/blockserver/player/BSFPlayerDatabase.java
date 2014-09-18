package org.blockserver.player;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.BufferUnderflowException;
import java.nio.ByteBuffer;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import org.blockserver.Context;
import org.blockserver.io.bsf.BSF;
import org.blockserver.io.bsf.BSFWriter;
import org.blockserver.item.Inventory;
import org.blockserver.level.Level;
import org.blockserver.math.Vector3d;

public class BSFPlayerDatabase extends PlayerDatabase{
	private File folder;

	@Override
	protected PlayerData loadPlayer(Player player, String name){
		File file = getPlayerFile(name);
		if(file.exists()){
			try{
				FileInputStream stream = new FileInputStream(file);
				byte[] buffer = new byte[(int) file.length()];
				stream.read(buffer);
				stream.close();
				ByteBuffer bb = ByteBuffer.wrap(buffer);
				byte length = bb.get();
				byte[] caseName = new byte[length];
				bb.get(caseName, 0, length);
				String CaseName= new String(caseName);
				double x = bb.getDouble();
				double y = bb.getDouble();
				double z = bb.getDouble();
				Vector3d coords = new Vector3d(x, y, z);
				length = bb.get();
				byte[] world = new byte[length];
				bb.get(world, 0, length);
				Level level = getServer().getLevel(new String(world), true, false);
				return new PlayerData(level, coords, CaseName, new Inventory(player, Context.DEFAULT_PLAYER_INVENTORY_SIZE, getServer()));
			}
			catch(IOException e) {
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
