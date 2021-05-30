package cold.fyre.API.Packets.minecraft;

import cold.fyre.API.Packets.PacketHandler;
import cold.fyre.API.Packets.minecraft.support.WorldBorder;

public class PacketPlayOutWorldBorder extends Packet {
	
	private EnumWorldBorderAction action;
	private WorldBorder border;
	
	public PacketPlayOutWorldBorder(WorldBorder border, EnumWorldBorderAction action) {
		super("PacketPlayOutWorldBorder");
		this.border = border;
		this.action = action;
		WorldBorder b = new WorldBorder();
		b.setCenter(0D, 0D);
	}

	@Override
	public Object getPacket() {
		PacketHandler WorldBorder = new PacketHandler("WorldBorder");
		WorldBorder.runMethod("setCenter", border.getCenterX(), border.getCenterZ());
		WorldBorder.runMethod("setSize", border.getSize());
		WorldBorder.runMethod("setDamageAmount", border.getDamageAmount());
		WorldBorder.runMethod("setDamageBuffer", border.getDamageBuffer());
		WorldBorder.runMethod("setWarningTime", border.getWarningTime());
		WorldBorder.runMethod("setWarningDistance", border.getWarningDistance());
		
		//PacketHandler ppowb = new PacketHandler(getPacketName(), WorldBorder.getPacket(), PacketHandler.getEnum(getPacketName() + "$EnumWorldBorderAction", action.name()));
			PacketHandler ppowb = new PacketHandler(getPacketName());
			ppowb.setFieldValue("a", PacketHandler.getEnum(getPacketName() + "$EnumWorldBorderAction", action.name()));
			ppowb.setFieldValue("c", (double) WorldBorder.runMethod("getCenterX"));
			ppowb.setFieldValue("d", (double) WorldBorder.runMethod("getCenterZ"));
			ppowb.setFieldValue("f", (double) WorldBorder.runMethod("getSize"));
			ppowb.setFieldValue("i", (int) WorldBorder.runMethod("getWarningDistance"));
			ppowb.setFieldValue("h", (int) WorldBorder.runMethod("getWarningTime"));
			ppowb.setFieldValue("e", (double) WorldBorder.runMethod("k"));
			ppowb.setFieldValue("g", (long) WorldBorder.runMethod("j"));
			ppowb.setFieldValue("b", (int) WorldBorder.runMethod("m"));

			return ppowb.getPacket();
	}
	
	public enum EnumWorldBorderAction {
		SET_SIZE, LERP_SIZE, SET_CENTER, INITIALIZE, SET_WARNING_TIME, SET_WARNING_BLOCKS;
	}

}
