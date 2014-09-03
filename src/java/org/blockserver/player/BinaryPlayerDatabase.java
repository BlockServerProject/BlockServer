package org.blockserver.player;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.BufferUnderflowException;
import java.nio.ByteBuffer;
import java.util.Locale;

import org.blockserver.level.Level;
import org.blockserver.math.Vector3d;

public class BinaryPlayerDatabase extends PlayerDatabase{
	private File folder;

	@Override
	protected PlayerData loadPlayer(String name){
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
				return new PlayerData(level, coords, CaseName);
			}
			catch(IOException e) {
				e.printStackTrace();
			}
			catch(BufferUnderflowException e){
				return dummy(name);
			}
		}
		else{
			throw new RuntimeException("Tried to load a player file that doesn't exist.");
		}
		return dummy(name);
	}

	private PlayerData dummy(String caseName){
		Level level = getServer().getDefaultLevel();
		return new PlayerData(level, level.getSpawnPos(), caseName);
	}

	@Override
	protected void savePlayer(PlayerData data){
		File file = getPlayerFile(data.getCaseName());
		if(!file.isFile()){
			try{
				file.createNewFile();
			}
			catch(IOException e){
				e.printStackTrace();
			}
		}
		try{
			FileOutputStream out = new FileOutputStream(file);
			ByteBuffer bb = ByteBuffer.allocate(0xFFFF);
			bb.put((byte) data.getCaseName().length());
			bb.put(data.getCaseName().getBytes());
			bb.putDouble(data.getCoords().getX());
			bb.putDouble(data.getCoords().getY());
			bb.putDouble(data.getCoords().getZ());
			bb.put((byte) data.getLevel().getName().length());
			bb.put(data.getLevel().getName().getBytes());
			out.write(bb.array());
			out.flush();
			out.close();
		}
		catch(IOException e){
			// TODO Auto-generated catch block
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
