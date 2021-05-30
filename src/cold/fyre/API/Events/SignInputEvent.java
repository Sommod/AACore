package cold.fyre.API.Events;

import org.bukkit.Material;
import org.bukkit.block.Sign;
import org.bukkit.entity.Player;
import org.bukkit.event.Cancellable;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

import cold.fyre.API.Managers.PacketManager;

/**
 * Used in conjunction with the {@link PacketManager} class. This event is ran
 * when a plugin issues the {@link PacketManager#sendSign(Player)} method is called
 * upon and the player exits the sign.
 * 
 * @author Armeriness
 * @author Sommod
 * @since 2.0
 *
 */
public class SignInputEvent extends Event implements Cancellable {
	
	private Player player;
	private Sign sign;
	private String[] lines;
	private boolean isCancelled = false;
	
	public SignInputEvent(PacketManager manager, String[] lines, Player player, Sign sign) {
		this.lines = lines;
		this.player = player;
		this.sign = sign;
		//manager.r(player);
	}
	
	public Player getPlayer() { return player; }
	
	public Sign getSign() { return sign; }
	
	public String getLine(int lineNumber) {
		if(lineNumber > 3 || lineNumber < 0)
			return "";
		else return lines[lineNumber];
	}
	
	public String[] getLines() { return lines; }
	
	public void deleteSign() {
		if(sign != null) {
			sign.getBlock().setType(Material.AIR);
			sign.getBlock().getState().update();
		}
	}
	
	public boolean hasText(int lineNumber) {
		if(getLine(lineNumber) != null || !getLine(lineNumber).isEmpty())
			return true;
		return false;
	}
	
	@Override
	public HandlerList getHandlers() { return new HandlerList(); }

	@Override
	public boolean isCancelled() { return isCancelled; }

	@Override
	public void setCancelled(boolean cancel) { isCancelled = cancel; }

}
