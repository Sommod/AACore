package cold.fyre.API.Packets.minecraft;

import java.lang.reflect.InvocationTargetException;

import cold.fyre.API.Packets.PacketHandler;

public class PacketPlayOutOpenWindow extends Packet {
	
	private int value1, value2;
	private String IChat;
	
	public PacketPlayOutOpenWindow() { }
	
	public PacketPlayOutOpenWindow(int value1, int value2, String windowName) {
		this.value1 = value1;
		this.value2 = value2;
		IChat = windowName;
	}
	
	public void setWindowName(String name) { IChat = name; }

	@Override
	public Object getPacket() {
		try {
			Object ICBC = PacketHandler.getClass("IChatBaseComponent$ChatSerializer").getMethod("a", String.class).invoke(null, IChat == null ? " " : IChat);
			PacketHandler ppoow = new PacketHandler("PacketPlayOutOpenWindow");
			ppoow.setFieldValue("a", value1);
			ppoow.setFieldValue("b", value2);
			ppoow.setFieldValue("c", ICBC);
			return ppoow.getPacket();
		} catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException | NoSuchMethodException
				| SecurityException e) {
			e.printStackTrace();
			return null;
		}
	}

}
