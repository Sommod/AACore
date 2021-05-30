package cold.fyre.API.Packets.minecraft;

import cold.fyre.API.Packets.Converter;
import cold.fyre.API.Packets.PacketHandler;
import cold.fyre.API.Packets.minecraft.support.MinecraftKey;

public class PacketPlayOutAutoRecipe extends Packet {
	
	private int value;
	private MinecraftKey key;
	
	public PacketPlayOutAutoRecipe() { }
	
	public PacketPlayOutAutoRecipe(int value, MinecraftKey key) {
		this.value = value;
		this.key = key;
	}
	
	public int getValue() { return value; }
	
	public MinecraftKey getKey() { return key; }
	
	public void setValue(int value) { this.value = value; }
	
	public void setMinecraftKey(MinecraftKey key) { this.key = key; }

	@Override
	public Object getPacket() {
		PacketHandler ppoar = new PacketHandler("PacketPlayOutAutoRecipe");
		ppoar.setFieldValue("a", value);
		ppoar.setFieldValue("b", Converter.convertMinecraftKey(key));
		
		return ppoar.getPacket();
	}

}
