package org.blockserver.level.generator;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import org.blockserver.blocks.Block;
import org.blockserver.level.BiomeType;
import org.blockserver.level.provider.ChunkPosition;
import org.blockserver.level.provider.LevelProvider;
import org.blockserver.math.Vector3d;

public class FlatGenerator implements Generator{
	private LevelProvider provider;
	private long seed;
	private Random random;
	private int version;
	private String opts;
	private int presetVersion;
	private List<BlockLayer> layers;
	private BiomeType biomeType;
	private List<StructDef> structDefs;

	public FlatGenerator(LevelProvider provider, long seed, Random random, int version, String opts){
		this.provider = provider;
		this.seed = seed;
		this.random = random;
		this.version = version;
		this.opts = opts;
		try{
			parseOpts();
		}
		catch(NumberFormatException | IndexOutOfBoundsException e){
			provider.getServer().getLogger().warning("Preset for world %s (%s) cannot "
					+ "be correctly parsed! The default preset will be used instead.",
					provider.getLevelName(), opts);
			e.printStackTrace();
			opts = "2;7,40*1,3*3,2;1;";
			parseOpts();
		}
	}
	protected void parseOpts(){
		String[] parts = opts.split(";", 4);
		presetVersion = Integer.parseInt(parts[0]);
		for(String layer: parts[1].split(",")){
			String[] numbers = layer.split("*", 2);
			int count = 1;
			Block block;
			if(numbers.length == 0){
				continue;
			}
			if(numbers.length == 1){
				block = parseBlock(numbers[0]);
			}
			else{
				count = Integer.parseInt(numbers[0]);
				block = parseBlock(numbers[1]);
			}
			layers.add(new BlockLayer(block, count));
		}
		int biomeId = Integer.parseInt(parts[2]);
		biomeType = BiomeType.get(biomeId);
		if(biomeType == null){
			throw new IndexOutOfBoundsException("Unknown biome ID " + biomeId);
		}
		parseStructures(parts[3]);
	}
	protected Block parseBlock(String str){
		String[] tokens = str.split(":", 2);
		int id;
		int damage = 0;
		if(tokens.length == 0){
			throw new NumberFormatException();
		}
		id = Integer.parseInt(tokens[0]);
		if(tokens.length == 2){
			damage = Integer.parseInt(tokens[1]);
		}
		if(damage >= 16){
			throw new IndexOutOfBoundsException("Damage too large");
		}
		return new Block(id, damage);
	}
	protected void parseStructures(String code){
		for(String stct: code.split(",")){
			String name = stct;
			Map<String, Integer> args = new HashMap<String, Integer>();
			int index = stct.indexOf('(');
			if(index != -1){
				name = stct.substring(0, index);
				if(stct.charAt(stct.length() - 1) != ')'){
					throw new NumberFormatException("Missing close bracket");
				}
				String[] params = stct.substring(index + 1, stct.length() - 1).split(" ");
				for(String arg: params){
					String[] tokens = arg.split("=", 2);
					if(tokens.length != 2){
						throw new NumberFormatException("Incorect parameter format");
					}
					args.put(tokens[0], Integer.parseInt(tokens[1]));
				}
			}
			structDefs.add(new StructDef(name, args));
		}
	}
	public LevelProvider getProvider(){
		return provider;
	}
	@Override
	public void initLevel(){
		Vector3d spawn = provider.getSpawn();
		generateChunk(ChunkPosition.fromCoords(spawn), FLAG_GENERATE_SPAWN);
	}
	@Override
	public void generateChunk(ChunkPosition pos, int flag){
		// TODO Auto-generated method stub
		
	}
	public long getSeed(){
		return seed;
	}
	public Random getRandom(){
		return random; // safe?
	}
	public int getGeneratorVersion(){
		return version;
	}
	public int getPresetVersion(){
		return presetVersion;
	}
	public String getPreset(){
		return opts;
	}
	public List<BlockLayer> getBlockLayers(){
		return layers;
	}
	public BiomeType getBiomeType(){
		return biomeType;
	}
	public List<StructDef> getStructDefs(){
		return structDefs;
	}
	protected class BlockLayer{
		private Block block;
		private int height;
		public BlockLayer(Block block, int height){
			this.block = block;
			this.height = height;
		}
		public Block getBlock(){
			return block;
		}
		public int getHeight(){
			return height;
		}
	}
	protected class StructDef{ // C++? :D
		private String name;
		private Map<String, Integer> params;

		public StructDef(String name, Map<String, Integer> params){
			this.name = name;
			this.params = params;
		}
		public String getName(){
			return name;
		}
		public Map<String, Integer> getParams(){
			return params;
		}
	}
}
