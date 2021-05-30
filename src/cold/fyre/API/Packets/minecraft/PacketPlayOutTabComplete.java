package cold.fyre.API.Packets.minecraft;

import com.mojang.brigadier.suggestion.Suggestions;

import cold.fyre.API.Packets.PacketHandler;

public class PacketPlayOutTabComplete extends Packet {
	
	private int value;
	private Suggestions suggestions;
	
	public PacketPlayOutTabComplete() { super("PacketPlayOutTabComplete"); }
	
	public PacketPlayOutTabComplete(int value, Suggestions suggestions) {
		this.value = value;
		this.suggestions = suggestions;
	}
	
	public void setInt(int value) { this.value = value; }
	
	public void setSuggestions(Suggestions suggestions) { this.suggestions = suggestions; }

	@Override
	public Object getPacket() {
		PacketHandler ppotc = new PacketHandler(getPacketName(), value, suggestions);
		return ppotc.getPacket();
	}

}
