package cold.fyre.API.Packets.minecraft;

import cold.fyre.API.Packets.PacketHandler;

public class PacketPlayOutCollect extends Packet {
	
	private int a, b, c;
	
	public PacketPlayOutCollect() { }
	
	public PacketPlayOutCollect(int value1, int value2, int value3) {
		a = value1;
		b = value2;
		c = value3;
	}
	
	public void setFirstValue(int value) { a = value; }
	
	public void setSecondvalue(int value) { b = value; }
	
	public void setThirdValue(int value) { c = value; }
	
	public int getFirstvalue() { return a; }
	
	public int getSecondValue() { return b; }
	
	public int getThirdValue() { return c; }

	@Override
	public Object getPacket() {
		PacketHandler ppoc = new PacketHandler("PacketPlayOutCollect", a, b, c);
		return ppoc.getPacket();
	}

}
