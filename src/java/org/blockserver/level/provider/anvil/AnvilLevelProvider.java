package org.blockserver.level.provider.anvil;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

import org.blockserver.Server;
import org.blockserver.io.nbt.ByteTag;
import org.blockserver.io.nbt.CompoundTag;
import org.blockserver.io.nbt.DoubleTag;
import org.blockserver.io.nbt.IntTag;
import org.blockserver.io.nbt.LongTag;
import org.blockserver.io.nbt.NBTReader;
import org.blockserver.io.nbt.StringTag;
import org.blockserver.level.Level;
import org.blockserver.level.provider.ChunkPosition;
import org.blockserver.level.provider.LevelProvider;
import org.blockserver.math.Vector3d;

@SuppressWarnings("unused")
public class AnvilLevelProvider extends LevelProvider{
	private Server server;
	private File dir;

	private int version;
	private boolean initialized;
	@Deprecated
	private String levelName;
	private String generator;
	private int generatorVersion;
	private String generatorOptions;
	private long seed;
	private boolean hasFeatures;
	private long lastPlayed;
	private boolean allowCheats;
	private boolean hardcore;
	private int gamemode;
	private byte difficulty;
	private boolean difficultyLocked;
	private long levelTicks;
	private long timeTicks;
	private int[] spawn;
	private double borderCenterX, borderCenterZ;
	private double borderSize;
	private double borderSafeZone;
	private double borderWarningBlocks;
	private double borderWarningTime;
	private double borderSizeLerpTarget;
	private long borderSizeLerpTime;
	private double borderDamagePerBlock;
	private boolean raining;
	private int rainTime;
	private boolean thundering;
	private int thunderTime;
	private int clearWeatherTime;
	private CompoundTag gameRules;

	public AnvilLevelProvider(Server server, File dir, String name){
		super(name);
		this.server = server;
		this.dir = dir;
	}
	@Override
	public void init(){
		readNBT();
	}
	private void readNBT(){
		File levelDotDat = new File(dir, "level.dat");
		if(levelDotDat.isFile()){
			try{
				NBTReader reader = new NBTReader(new FileInputStream(levelDotDat));
				CompoundTag levelDat = (CompoundTag) reader.readTag();
				CompoundTag data = (CompoundTag) levelDat.get("Data");
				version = ((IntTag) data.get("version")).getValue();
				initialized = ((ByteTag) data.get("initialized")).getValue() > 0;
				levelName = ((StringTag) data.get("LevelName")).getValue();
				String dirName = dir.getName().replace("\\", "").replace("/", "");
				generator = ((StringTag) data.get("generatorName")).getValue();
				generatorVersion = ((IntTag) data.get("generatorVersion")).getValue();
				generatorOptions = ((StringTag) data.get("generatorOptions")).getValue();
				seed = ((LongTag) data.get("RandomSeed")).getValue();
				hasFeatures = ((ByteTag) data.get("MapFeatures")).getValue() > 0;
				lastPlayed = ((LongTag) data.get("LastPlayed")).getValue();
				allowCheats = ((ByteTag) data.get("allowCommands")).getValue() > 0;
				hardcore = ((ByteTag) data.get("hardcore")).getValue() > 0;
				gamemode = ((IntTag) data.get("GameType")).getValue();
				difficulty = ((ByteTag) data.get("Difficulty")).getValue();
				difficultyLocked = ((ByteTag) data.get("DifficultyLocked")).getValue() > 0;
				levelTicks = ((LongTag) data.get("Time")).getValue();
				timeTicks = ((LongTag) data.get("DayTime")).getValue();
				spawn = new int[]{
						((IntTag) data.get("SpawnX")).getValue(),
						((IntTag) data.get("SpawnY")).getValue(),
						((IntTag) data.get("SpawnZ")).getValue()
				};
				borderCenterX = ((DoubleTag) data.get("BorderCenterX")).getValue();
				borderCenterZ = ((DoubleTag) data.get("BorderCenterZ")).getValue();
				borderSize = ((DoubleTag) data.get("BorderSize")).getValue();
				borderSafeZone = ((DoubleTag) data.get("BorderSafeZone")).getValue();
				borderWarningBlocks = ((DoubleTag) data.get("BorderWarningBlocks")).getValue();
				borderWarningTime = ((DoubleTag) data.get("BorderWarningTime")).getValue();
				borderSizeLerpTarget = ((DoubleTag) data.get("BorderSizeLerpTarget")).getValue();
				borderSizeLerpTime = ((LongTag) data.get("BorderSizeLerpTime")).getValue();
				borderDamagePerBlock = ((DoubleTag) data.get("BorderDamagePerBlock")).getValue();
				raining = ((ByteTag) data.get("raining")).getValue() > 0;
				rainTime = ((IntTag) data.get("rainTime")).getValue();
				thundering = ((ByteTag) data.get("thundering")).getValue() > 0;
				thunderTime = ((IntTag) data.get("thunderTime")).getValue();
				clearWeatherTime = ((IntTag) data.get("clearWeatherTime")).getValue();
				gameRules = (CompoundTag) data.get("GameRules");
				reader.close();
			}
			catch(IOException e){
				e.printStackTrace();
			}
		}
	}

	@Override
	public boolean loadChunk(ChunkPosition pos){
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean saveChunk(ChunkPosition pos){
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean isChunkLoaded(ChunkPosition pos){
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean isChunkGenerated(ChunkPosition pos){
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean deleteChunk(ChunkPosition pos){
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public Vector3d getSpawn(){
		// TODO Auto-generated method stub
		return null;
	}

	public String getFileLevelName(){
		return dir.getName().replace("/", "").replace("\\", "");
	}
	@Deprecated
	public String getNBTLevelName(){
		return levelName;
	}
	public File getChunkRegionFile(int X, int Z){
		return new File(new File(dir, "region"), String.format("r.%d.%d.mca", X >> 5, Z >> 5));
	}
	public File getRegionFile(int X, int Z){
		return new File(new File(dir, "region"), String.format("r.%d.%d.mca", X, Z));
	}
	@Override
	public Server getServer(){
		return server;
	}
}
