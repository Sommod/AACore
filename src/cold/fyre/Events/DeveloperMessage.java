package cold.fyre.Events;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.plugin.Plugin;

import cold.fyre.Usage.IcyHotManager;

/**
 * A small event that is used to detect an AA-plugin developer.
 * Sends a message to the developer if the server uses AA-plugins.
 * 
 * @author Armeriness
 * @author Sommod
 * @version 2.0
 *
 */
public class DeveloperMessage implements Listener {
	
	private IcyHotManager manager;
	
	public DeveloperMessage(IcyHotManager manager) {
		this.manager = manager;
		manager.getPlugin().getServer().getPluginManager().registerEvents(this, manager.getPlugin());
	}
	
	@EventHandler(priority = EventPriority.LOW)
	public void onDeveloperJoin(PlayerJoinEvent event) {
		if(event.getPlayer().getName().equalsIgnoreCase("Armeriness") ||
				event.getPlayer().getName().equalsIgnoreCase("Sommod")) {
			Player player = event.getPlayer();
			
			player.sendMessage("");
			player.sendMessage("§c§lNotice §8§l>> §7This server uses AACore plugins.");
			
			player.sendMessage("§6Plugins:");
			for(Plugin p : manager.getPlugin().getServer().getPluginManager().getPlugins()) {
				if(p.getDescription().getAuthors().contains("Sommod") || p.getDescription().getAuthors().contains("Armeriness"))
					player.sendMessage("§e" + p.getName() + " §7[§a" + p.getDescription().getVersion() + "§7]");
			}
			
		} else return;
	}

}
