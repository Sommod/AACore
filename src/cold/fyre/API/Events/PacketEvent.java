package cold.fyre.API.Events;

import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

import cold.fyre.API.Packets.minecraft.Packet;

public class PacketEvent extends Event {

	private Player player;
	private Packet packet;
	
	public PacketEvent(Packet packet, Player player) {
		this.packet = packet;
		this.player = player;
	}
	
	public Player getPlayer() { return player; }
	
	public Packet getPacket() { return packet; }

	@Override
	public HandlerList getHandlers() { return new HandlerList(); }

}
