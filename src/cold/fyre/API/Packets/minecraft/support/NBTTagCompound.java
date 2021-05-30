package cold.fyre.API.Packets.minecraft.support;

import java.io.Serializable;
import java.lang.reflect.InvocationTargetException;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import org.bukkit.Bukkit;

import cold.fyre.API.Managers.FileManager;

public class NBTTagCompound implements Serializable {

	private static final long serialVersionUID = 2674704430879896672L;
	protected final Map<String, Object> map;
	
	public NBTTagCompound() { map = new HashMap<String, Object>(); }
	
	public NBTTagCompound(NBTTagCompound clone) { map = clone.map; }
	
	public NBTTagCompound(Object nmsNBTTagCompound) {
		map = getCompound(nmsNBTTagCompound);
	}
	
	private Map<String, Object> getCompound(Object nmsNBTTagCompound) {
		Map<String, Object> values = new HashMap<String, Object>();
		try {
			@SuppressWarnings("unchecked")
			Set<String> keys = (Set<String>) nmsNBTTagCompound.getClass().getMethod("getKeys").invoke(nmsNBTTagCompound);
			
			if(keys.isEmpty())
				return values;
			
			String version = "net.minecraft.server." + Bukkit.getServer().getClass().getPackage().getName().split("\\.")[3] + ".";
			
			for(String key : keys) {
				Object nbtBase = nmsNBTTagCompound.getClass().getMethod("get", String.class).invoke(nmsNBTTagCompound, key);
				
				if(nbtBase.getClass().equals(Class.forName(version + "NBTTagInt")))
					map.put(key, (int) nbtBase.getClass().getMethod("asInt").invoke(nbtBase));
				else if(nbtBase.getClass().equals(Class.forName(version + "NBTTagDouble")))
					map.put(key, (double) nbtBase.getClass().getMethod("asDouble").invoke(nbtBase));
				else if(nbtBase.getClass().equals(Class.forName(version + "NBTTagFloat")))
					map.put(key, (float) nbtBase.getClass().getMethod("asFloat").invoke(nbtBase));
				else if(nbtBase.getClass().equals(Class.forName(version + "NBTTagShort")))
					map.put(key, (short) nbtBase.getClass().getMethod("asShort").invoke(nbtBase));
				else if(nbtBase.getClass().equals(Class.forName(version + "NBTTagLong")))
					map.put(key, (long) nbtBase.getClass().getMethod("asLong").invoke(nbtBase));
				else if(nbtBase.getClass().equals(Class.forName(version + "NBTTagByte")))
					map.put(key, (byte) nbtBase.getClass().getMethod("asByte").invoke(nbtBase));
				else if(nbtBase.getClass().equals(Class.forName(version + "NBTTagIntArray")))
					map.put(key, (int[]) nbtBase.getClass().getMethod("getInts").invoke(nbtBase));
				else if(nbtBase.getClass().equals(Class.forName(version + "NBTTagByteArray")))
					map.put(key, (byte[]) nbtBase.getClass().getMethod("getBytes").invoke(nbtBase));
				else if(nbtBase.getClass().equals(Class.forName(version + "NBTTagString")))
					map.put(key, (String) nbtBase.getClass().getMethod("asString").invoke(nbtBase));
				else if(nbtBase.getClass().equals(Class.forName(version + "NBTTagCompound")))
					map.put(key, new NBTTagCompound(nbtBase));
				
				return map;
			}
		} catch (ClassNotFoundException | IllegalAccessException | IllegalArgumentException | InvocationTargetException | NoSuchMethodException | SecurityException e) {
			FileManager.logExceptionToFile("", e);
		}
		return values;
	}
	
	public Set<String> getKeys() { return map.keySet(); }
	
	public Collection<Object> getValues() { return map.values(); }
	
	public byte getTypeID() { return 10; }
	
	public int getSize() { return map.size(); }
	
	public void setByte(final String name, final byte value) { map.put(name, value); }
	
	public void setShort(final String name, final short value) { map.put(name, value); }
	
	public void setInt(final String name, final int value) { map.put(name, value); }
	
	public void setLong(final String name, final long value) { map.put(name, value); }
	
	public void setFloat(final String name, final float value) { map.put(name, value); }
	
	public void setDouble(final String name, final double value) { map.put(name, value); }
	
	public void setString(final String name, final String value) { map.put(name, value); }
	
	public void setByteArray(final String name, final byte[] value) { map.put(name, value); }
	
	public void setIntArray(final String name, final int[] value) { map.put(name, value); }
	
	public void setBoolean(final String name, final boolean value) { map.put(name, value); }
	
	public void set(final String name, final NBTTagCompound value) { map.put(name, value); }
	
	public boolean hasKey(final String key) { return map.containsKey(key); }
	
	public byte getByte(final String key) {
		if(map.get(key) == null || !(map.get(key) instanceof Byte))
			return 0;
		
		return (byte) map.get(key);
	}
	
	public int getInt(final String key) {
		if(map.get(key) == null || !(map.get(key) instanceof Integer))
			return 0;
		
		return (int) map.get(key);
	}
	
	public short getShort(final String key) {
		if(map.get(key) == null || !(map.get(key) instanceof Short))
			return 0;
		
		return (short) map.get(key);
	}
	
	public long getLong(final String key) {
		if(map.get(key) == null || !(map.get(key) instanceof Long))
			return 0L;
		
		return (long) map.get(key);
	}
	
	public float getFloat(final String key) {
		if(map.get(key) == null ||  !(map.get(key) instanceof Float))
			return 0F;
		
		return (float) map.get(key);
	}
	
	public double getDouble(final String key) {
		if(map.get(key) == null || !(map.get(key) instanceof Double))
			return 0D;
		
		return (double) map.get(key);
	}
	
	public String getString(final String key) {
		if(map.get(key) == null || !(map.get(key) instanceof String))
			return null;
		
		return (String) map.get(key); 
	}
	
	public byte[] getByteArray(final String key) {
		if(map.get(key) == null || !(map.get(key) instanceof Byte[]))
			return new byte[0];
		
		return (byte[]) map.get(key);
	}
	
	public int[] getIntArray(final String key) {
		if(map.get(key) == null || !(map.get(key) instanceof Integer[]))
			return new int[0];
		
		return (int[]) map.get(key);
	}
	
	public boolean getBoolean(final String key) {
		if(map.get(key) == null || !(map.get(key) instanceof Boolean))
			return false;
		
		return (boolean) map.get(key);
	}
	
	public NBTTagCompound getNBTTagCompound(final String key) {
		if(map.get(key) == null || !(map.get(key) instanceof NBTTagCompound))
			return null;
		
		return (NBTTagCompound) map.get(key);
	}
	
	public Object get(final String key) { return map.get(key); }

}
