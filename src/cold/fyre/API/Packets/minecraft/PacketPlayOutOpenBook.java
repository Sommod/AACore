package cold.fyre.API.Packets.minecraft;

import cold.fyre.API.Packets.PacketHandler;
import cold.fyre.API.Packets.minecraft.support.EnumHand;

public class PacketPlayOutOpenBook extends Packet {
	
	private EnumHand hand;
	
	public PacketPlayOutOpenBook() { }
	
	public PacketPlayOutOpenBook(EnumHand hand) { this.hand = hand; }
	
	public void setHand(EnumHand hand) { this.hand = hand; }
	
	public EnumHand getHand() { return hand; }
	
	public boolean isMainHand() { return hand == EnumHand.MAIN_HAND; }

	@Override
	public Object getPacket() {
		PacketHandler ppoob = new PacketHandler("PacketPlayOutOpenBook", PacketHandler.getEnum("EnumHand", hand.name()));
		return ppoob.getPacket();
	}

}
