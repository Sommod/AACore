package cold.fyre.API.Packets.minecraft;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import cold.fyre.API.Packets.Converter;
import cold.fyre.API.Packets.PacketHandler;
import cold.fyre.API.Packets.minecraft.support.MinecraftKey;

public class PacketPlayOutRecipes extends Packet {
	
	private Action action;
	private List<MinecraftKey> keys1;
	private List<MinecraftKey> keys2;
	private boolean value1, value2, value3, value4;
	
	public PacketPlayOutRecipes() { }
	
	public PacketPlayOutRecipes(Action action, Collection<MinecraftKey> keys1, Collection<MinecraftKey> keys2, boolean bool1, boolean bool2, boolean bool3, boolean bool4) {
		this.action = action;
		keys1 = new ArrayList<MinecraftKey>(keys1);
		keys2 = new ArrayList<MinecraftKey>(keys2);
		value1 = bool1;
		value2 = bool2;
		value3 = bool3;
		value4 = bool4;
	}

	@Override
	public Object getPacket() {
		PacketHandler ppor = new PacketHandler("PacketPlayOutRecipes", PacketHandler.getEnum("PacketPlayOutRecipes$Action", action.name()), getKeys(keys1), getKeys(keys2), value1, value2, value3, value4);
		return ppor.getPacket();
	}
	
	private Collection<Object> getKeys(List<MinecraftKey> keys) {
		List<Object> getter = new ArrayList<Object>();
		
		for(MinecraftKey mk : keys)
			getter.add(Converter.convertMinecraftKey(mk));
		
		return getter;
		
	}
	
	public enum Action {
		INIT, ADD, REMOVE;
	}

}
