package cold.fyre.API.Packets.minecraft;

import java.util.EnumSet;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

import cold.fyre.API.Packets.PacketHandler;

public class PacketPlayOutPosition extends Packet {
	
	private double x, y, z;
	private float yaw, pitch;
	private int value;
	private Set<EnumPlayerTeleportFlags> flags;
	
	public PacketPlayOutPosition() { }
	
	public PacketPlayOutPosition(double x, double y, double z, float yaw, float pitch, Set<EnumPlayerTeleportFlags> flags, int value) {
		this.x = x;
		this.y = y;
		this.z = z;
		this.yaw = yaw;
		this.pitch = pitch;
		this.flags = flags;
		this.value = value;
	}
	
	public void setX(double x) { this.x = x; }
	
	public void setY(double y) { this.y = y; }
	
	public void setZ(double z) { this.z = z; }
	
	public void setYaw(float yaw) { this.yaw = yaw; }
	
	public void setPitch(float pitch) { this.pitch = pitch; }
	
	public void setFlags(Set<EnumPlayerTeleportFlags> flags) { this.flags = flags; }

	@Override
	public Object getPacket() {
		PacketHandler ppop = new PacketHandler("PacketPlayOutPosition");
		ppop.setFieldValue("a", x);
		ppop.setFieldValue("b", y);
		ppop.setFieldValue("c", z);
		ppop.setFieldValue("d", yaw);
		ppop.setFieldValue("e", pitch);
		ppop.setFieldValue("g", value);
		
		Set<Object> flagSet = new HashSet<>();
		
		if(flags != null && !flags.isEmpty()) {
			for(Iterator<EnumPlayerTeleportFlags> i = flags.iterator(); i.hasNext(); i.next())
				flagSet.add(PacketHandler.getEnum("PacketPlayOutPosition$EnumPlayerTeleportFlags", i.next().name()));
		}
		
		ppop.setFieldValue("f", flagSet);
		return ppop.getPacket();
	}
	
	public enum EnumPlayerTeleportFlags {
		X(0), Y(1), Z(2), Y_ROT(3), X_ROT(4);
		
		private int a;
		
		private EnumPlayerTeleportFlags(int value) { a = value; }
		
		private int shift() { return 1 << a; }
		
		private boolean check(int value) { return (value & shift()) == shift(); }
		
		public static Set<EnumPlayerTeleportFlags> getSet(int value) {
			Set<EnumPlayerTeleportFlags> set = EnumSet.noneOf(EnumPlayerTeleportFlags.class);
			EnumPlayerTeleportFlags[] flags = values();
			int flagCount = flags.length;
			
			for(int i = 0; i < flagCount; i++) {
				EnumPlayerTeleportFlags temp = flags[i];
				
				if(temp.check(value))
					set.add(temp);
			}
			
			return set;
		}
	}

}
