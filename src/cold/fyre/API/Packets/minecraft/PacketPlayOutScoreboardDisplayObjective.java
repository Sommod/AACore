package cold.fyre.API.Packets.minecraft;

import cold.fyre.API.Packets.PacketHandler;

public class PacketPlayOutScoreboardDisplayObjective extends Packet {
	
	private int displayLocation;
	private String display;
	
	public PacketPlayOutScoreboardDisplayObjective() { super("PacketPlayOutScoreboardDisplayObjective"); }
	
	public PacketPlayOutScoreboardDisplayObjective(int displayLocation, String display) {
		this.display = display;
		this.displayLocation = displayLocation;
	}
	
	@Override
	public Object getPacket() {
		PacketHandler pposdo = new PacketHandler(getPacketName());
		pposdo.setFieldValue("a", displayLocation);
		pposdo.setFieldValue("b", display);
		return pposdo.getPacket();
	}

}
