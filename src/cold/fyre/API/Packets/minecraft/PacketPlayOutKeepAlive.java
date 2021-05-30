package cold.fyre.API.Packets.minecraft;

import cold.fyre.API.Packets.PacketHandler;

public class PacketPlayOutKeepAlive extends Packet {
	
	private long value;
	
	public PacketPlayOutKeepAlive() { }
	
	public PacketPlayOutKeepAlive(long value) { this.value = value; }
	
	public void setValue(long value) { this.value = value; }
	
	public long getValue() { return value; }

	@Override
	public Object getPacket() {
		PacketHandler ppoka = new PacketHandler("PacketPlayOutKeepAlive", value);
		return ppoka.getPacket();
	}

}
