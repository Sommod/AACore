package cold.fyre.API.Packets.minecraft;

import cold.fyre.API.Packets.PacketHandler;

public class PacketPlayOutExperience extends Packet {
	
	private float exp;
	private int a, b;
	
	public PacketPlayOutExperience() { }
	
	public PacketPlayOutExperience(float exp, int value1, int value2) {
		this.exp = exp;
		a = value1;
		b = value2;
	}
	
	public void setExperience(float value) { exp = value; }
	
	public void setFirstValue(int value) { a = value; }
	
	public void setSecondValue(int value) { b = value; }
	
	public float getExperience() { return exp; }
	
	public int getFirstValue() { return a; }
	
	public int getSecondValue() { return b; }

	@Override
	public Object getPacket() {
		PacketHandler ppoe = new PacketHandler("PacketPlayOutExperience", exp, a, b);
		return ppoe.getPacket();
	}

}
