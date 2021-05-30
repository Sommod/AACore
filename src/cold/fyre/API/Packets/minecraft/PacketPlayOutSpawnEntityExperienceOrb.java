package cold.fyre.API.Packets.minecraft;

import org.bukkit.Location;
import org.bukkit.entity.ExperienceOrb;

import cold.fyre.API.Packets.PacketHandler;

public class PacketPlayOutSpawnEntityExperienceOrb extends Packet {
	
	private int id;
	private double x, y, z;
	private int amount;
	
	public PacketPlayOutSpawnEntityExperienceOrb() { super("PacketPlayOutExperienceOrb"); }
	
	public PacketPlayOutSpawnEntityExperienceOrb(ExperienceOrb orb) {
		super("PacketPlayOutSpawnEntityExperienceOrb");
		id = orb.getEntityId();
		x = orb.getLocation().getX();
		y = orb.getLocation().getY();
		z = orb.getLocation().getZ();
		amount = orb.getExperience();
	}
	
	public PacketPlayOutSpawnEntityExperienceOrb(int id, int amount, double x, double y, double z) {
		super("PacketPlayOutSpawnEntityExperienceOrb");
		this.id = id;
		this.amount = amount;
		this.x = x;
		this.y = y;
		this.z = z;
	}
	
	public void setEntityID(int id) { this.id = id; }
	
	public void setAmount(int amount) {this.amount = amount; }
	
	public void setLocation(Location location) {
		x = location.getX();
		y = location.getY();
		z = location.getZ();
	}
	
	public void setLocation(double x, double y, double z) {
		this.x = x;
		this.y = y;
		this.z = z;
	}

	@Override
	public Object getPacket() {
		PacketHandler pposeeo = new PacketHandler(getPacketName());
		pposeeo.setFieldValue("a", id);
		pposeeo.setFieldValue("b", x);
		pposeeo.setFieldValue("c", y);
		pposeeo.setFieldValue("d", z);
		pposeeo.setFieldValue("e", amount);
		return pposeeo.getPacket();
	}

}
