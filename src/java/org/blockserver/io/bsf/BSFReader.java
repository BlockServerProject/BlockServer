package org.blockserver.io.bsf;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.blockserver.Server;
import org.blockserver.entity.Entity;
import org.blockserver.io.BinaryReader;
import org.blockserver.item.Inventory;
import org.blockserver.item.Item;
import org.blockserver.item.ItemType;

public class BSFReader extends BinaryReader{
	private Server server;
	private BSFVersion version;
	private BSFType type;

	public BSFReader(InputStream is, Server server) throws IOException{
		super(is);
		this.server = server;
		init();
	}
	private void init() throws IOException{
		try{
			byte[] b = read(3);
			for(int i = 0; i < 3; i++){
				if(BSFWriter.HEADER[i] != b[i]){
					throw new IOException(String.format("Byte %d is wrong, got %d, expected %d", 
							i, b[i], BSFWriter.HEADER[i]));
				}
			}
		}
		catch(IOException e){
			throw new InvalidBSFFileException("Incorrect header: ".concat(e.getMessage()));
		}
		try{
			version = BSFVersion.get(readByte());
			type = BSFType.get(readByte());
		}
		catch(IOException e){
			throw new InvalidBSFFileException("Version ID or type ID of file not found");
		}
	}

	public <T> List<T> readList(Class<T> clazz) throws IOException{
		List<Object> list = new ArrayList<Object>(1);
		list.add(clazz);
		return readList(list);
	}
	public <T> List<T> readList(List<Object> args)
			throws IOException, ClassCastException, IllegalArgumentException{
		falloc(4);
		int size = readInt();
		List<T> list = new ArrayList<T>(size);
		Object o = args.remove(0);
		if(!(o instanceof Class)){
			throw new ClassCastException(String.format("Object at index 0 of args "
					+ "passed must be instance of Class, %s given", o.getClass().getSimpleName()));
		}
		@SuppressWarnings("unchecked")
		Class<T> clazz = (Class<T>) o;
		for(int i = 0; i < size; i++){
			list.add(readType(clazz, args));
		}
		return list;
	}

	public <K, V> Map<K, V> readMap(Class<K> k, Class<V> v)
			throws IllegalArgumentException, ClassCastException, IOException{
		List<Object> args = new ArrayList<Object>(2);
		args.add(k);
		args.add(v);
		return readMap(args);
	}
	public <K, V> Map<K, V> readMap(List<Object> args)
			throws IOException, IllegalArgumentException, ClassCastException{
		falloc(4);
		Object a = args.remove(0);
		Object b = args.remove(0);
		if(!(b instanceof Class)){
			throw new ClassCastException(String.format("Object at index 0 of args "
					+ "passed must be instance of Class, %s given", a.getClass().getSimpleName()));
		}
		if(!(a instanceof Class)){
			throw new ClassCastException(String.format("Object at index 1 of args "
					+ "passed must be instance of Class, %s given", b.getClass().getSimpleName()));
		}
		@SuppressWarnings("unchecked")
		Class<K> k = (Class<K>) a;
		@SuppressWarnings("unchecked")
		Class<V> v = (Class<V>) b;
		int size = readInt();
		Map<K, V> result = new HashMap<K, V>(size);
		for(int i = 0; i < size; i++){
			K key = readType(k, args);
			V value = readType(v, args);
			result.put(key, value);
		}
		return result;
	}

	public Inventory readInventory(Entity owner) throws IOException{
		List<Object> list = new ArrayList<Object>(1);
		list.add(owner);
		return readInventory(list);
	}

	public Inventory readInventory(List<Object> args) throws IOException{
		falloc(4);
		List<Item> items = readList(Item.class);
		Inventory inventory = new Inventory((Entity) args.remove(0), items.size(), server);
		inventory.addAll(items);
		return inventory;
	}

	public Item readItem() throws IOException{
		falloc(4);
		short id = readShort();
		byte damage = readByte();
		byte count = readByte();
		Map<CharSequence, String> metadata = readMap(CharSequence.class, String.class);
		ItemType type = ItemType.getItemType(id, damage);
		return new Item(type, count, metadata);
	}

	@Override
	@SuppressWarnings("unchecked")
	public <T> T handleUnknownType(Class<T> t, List<Object> args) throws IllegalArgumentException, IOException{
		if(t.equals(List.class)){
			return (T) readList(args);
		}
		if(t.equals(Map.class)){
			return (T) readMap(args);
		}
		if(t.equals(Inventory.class)){
			return (T) readInventory(args);
		}
		if(t.equals(Item.class)){
			return (T) readItem();
		}
		return super.handleUnknownType(t, args);
	}

	@Override
	protected IOException getUEOFException(int needed){
		return new InvalidBSFFileException(needed + BSFWriter.FOOTER.length);
	}

	public BSFVersion getVersion(){
		return version;
	}
	public BSFType getType(){
		return type;
	}
}
