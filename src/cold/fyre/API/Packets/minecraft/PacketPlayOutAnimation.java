package cold.fyre.API.Packets.minecraft;

import org.bukkit.entity.Entity;

import cold.fyre.API.Packets.PacketHandler;

public class PacketPlayOutAnimation extends Packet {
	
	private int id;
	private int value;
	
	public PacketPlayOutAnimation() { }
	
	public PacketPlayOutAnimation(Entity id, int value) {
		this.id = id.getEntityId();
		this.value = value;
	}
	
	public void setID(int id) { this.id = id; }
	
	public void setValue(int value) { this.value = value; }
	
	public int getId() { return id; }
	
	public int getValue() { return value; }

	@Override
	public Object getPacket() {
		PacketHandler ppoa = new PacketHandler("PacketPlayOutAnimation");
		ppoa.setFieldValue("a", id);
		ppoa.setFieldValue("b", value);
		
		return ppoa.getPacket();
	}

}
