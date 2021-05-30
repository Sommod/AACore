package cold.fyre.API.Packets.minecraft;

import java.lang.reflect.InvocationTargetException;

import org.bukkit.inventory.ItemStack;

import cold.fyre.API.Packets.PacketHandler;

public class PacketPlayOutSetSlot extends Packet {
	
	private int slot, value;
	private ItemStack ItemStack;
	
	public PacketPlayOutSetSlot() { super("PacketPlayOutSetSlot"); }
	
	public PacketPlayOutSetSlot(int slot, int value, ItemStack item) {
		this.slot = slot;
		this.value = value;
		ItemStack = item;
	}
	
	public void setSlot(int slot) { this.slot = slot; }
	
	public void setValue(int value) { this.value = value; }
	
	public void setItem(ItemStack itemStack) { this.ItemStack = itemStack; }

	@Override
	public Object getPacket() {
		try {
			Object NMSItem = ItemStack.getClass().getMethod("asNMSCopy").invoke(ItemStack);
			PacketHandler pposs = new PacketHandler(getPacketName(), value, slot, NMSItem);
			return pposs.getPacket();
		} catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException | NoSuchMethodException
				| SecurityException e) {
			e.printStackTrace();
		}
		
		return null;
	}

}
