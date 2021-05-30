package cold.fyre.Events;

import org.bukkit.event.Cancellable;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;
import org.bukkit.plugin.Plugin;

import cold.fyre.Usage.IcyHotManager;

/**
 * Event that handles the Enabling of an AA-plugin.
 * 
 * @author Armeriness
 * @author Sommod
 * @version 2.0
 *
 */
public class AAEnable extends Event implements Cancellable {
	
	private Plugin plugin;
	@SuppressWarnings("unused")
	private IcyHotManager manager;
	private boolean cancel = false;
	
	public AAEnable(IcyHotManager manager, Plugin plugin) {
		this.manager = manager;
		this.plugin = plugin;
	}
	
	/**
	 * Gets the plugin that is being enabled.
	 * @return Plugin
	 */
	public Plugin getPlugin() { return plugin; }

	@Override
	public HandlerList getHandlers() { return new HandlerList(); }

	@Override
	public boolean isCancelled(){ return cancel; }

	@Override
	public void setCancelled(boolean cancel) { this.cancel = cancel; }
	
	

}
