package cold.fyre.API.Packets.minecraft;

import cold.fyre.API.Packets.PacketHandler;

public class PacketPlayOutUpdateHealth extends Packet {
	
	private float health, saturation;
	private int food;
	
	public PacketPlayOutUpdateHealth() { super("PacketPlayOutUpdateHealth"); }
	
	public PacketPlayOutUpdateHealth(float health, int food, float saturation) {
		super("PacketPlayOutUpdateHealth");
		this.health = health;
		this.food = food;
		this.saturation = saturation;
	}
	
	public void setHealth(float health) { this.health = health; }
	
	public void setFood(int food) {	this.food = food; }
	
	public void setSauration(float saturation) { this.saturation = saturation; }

	@Override
	public Object getPacket() {
		PacketHandler ppouh = new PacketHandler(getPacketName(), health, food, saturation);
		return ppouh.getPacket();
	}

}
