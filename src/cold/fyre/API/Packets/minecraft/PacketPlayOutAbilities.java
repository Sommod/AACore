package cold.fyre.API.Packets.minecraft;

import cold.fyre.API.Packets.Converter;
import cold.fyre.API.Packets.PacketHandler;
import cold.fyre.API.Packets.minecraft.support.PlayerAbilities;

public class PacketPlayOutAbilities extends Packet {
	
	private PlayerAbilities abilities;
	
	public PacketPlayOutAbilities() { this(new PlayerAbilities()); }
	
	public PacketPlayOutAbilities(PlayerAbilities abilities) {
		super("PacketPlayOutAbilities");
		this.abilities = abilities;
	}
	
	public void loadAbilities(PlayerAbilities abilities) { this.abilities = abilities; }
	
	@Override
	public Object getPacket() {
		PacketHandler ppoa = new PacketHandler(getPacketName());
		ppoa.runMethod("a", Converter.convertNBT(abilities.getNBT()));
		return ppoa;
	}

}
