package cold.fyre.API.Packets.minecraft;

import cold.fyre.API.Packets.PacketHandler;
import cold.fyre.API.Packets.minecraft.support.EnumGamemode;

public class PacketPlayOutRespawn extends Packet {
	
	private long value;
	private EnumGamemode mode1, mode2;
	private boolean canFly, canBuild, isInvulnerable;
	
	public PacketPlayOutRespawn() { super("PacketPlayOutRespawn"); }
	
	public PacketPlayOutRespawn(long value, EnumGamemode mode1, EnumGamemode mode2, boolean canFly, boolean canBuild, boolean isInvulnerable) {
		super("PacketPlayOutRespawn");
		this.value = value;
		this.mode1 = mode1;
		this.mode2 = mode2;
		this.canFly = canFly;
		this.canBuild = canBuild;
		this.isInvulnerable = isInvulnerable;
	}
	
	public void setLong(Long value) { this.value = value; }
	
	public void setMode1(EnumGamemode mode1) { this.mode1 = mode1; }
	
	public void setMode2(EnumGamemode mode2) { this.mode2 = mode2; }
	
	public void setCanFly(boolean canFly) { this.canFly = canFly; }
	
	public void setCanBuild(boolean canBuild) { this.canBuild = canBuild; }
	
	public void setisIinvulnerable(boolean isInvulnerable) { this.isInvulnerable = isInvulnerable; }

	@Override
	public Object getPacket() {
		PacketHandler ppor = new PacketHandler(getPacketName());
		ppor.setFieldValue("c", value);
		ppor.setFieldValue("d", PacketHandler.getEnum("EnumGamemode", mode1.name()));
		ppor.setFieldValue("e", PacketHandler.getEnum("EnumGamemode", mode2.name()));
		ppor.setFieldValue("f", canFly);
		ppor.setFieldValue("g", canBuild);
		ppor.setFieldValue("h", isInvulnerable);
		return ppor.getPacket();
	}

}
