package cold.fyre.API.Packets.minecraft;

import java.lang.reflect.InvocationTargetException;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import cold.fyre.API.Events.PacketEvent;

public abstract class PacketSender {
	
	public static void sendPacket(Player player, Packet packet) {
		Object nmsPacket = packet.getPacket();
		
		try {
			Object entityPlayer = player.getClass().getMethod("getHandle").invoke(player);
			Object playerConnection = entityPlayer.getClass().getField("playerConnection").get(entityPlayer);
			Object networkManager = playerConnection.getClass().getField("networkManager").get(playerConnection);
			
			Class<?> nmsPacketClass = Class.forName("net.minecraft.server." + packet.getPacketVersion() + ".Packet");
			networkManager.getClass().getMethod("sendPacket", nmsPacketClass).invoke(networkManager, nmsPacket);
			PacketEvent pe = new PacketEvent(packet, player);
			Bukkit.getServer().getPluginManager().callEvent(pe);
		} catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException | NoSuchMethodException
				| SecurityException | ClassNotFoundException | NoSuchFieldException e) {
			e.printStackTrace();
		}
	}

}
