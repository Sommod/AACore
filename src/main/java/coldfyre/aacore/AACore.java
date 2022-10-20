/**
 * 
 */
package coldfyre.aacore;

import org.bukkit.plugin.java.JavaPlugin;

import coldfyre.aacore.api.manager.PluginManager;

public class AACore extends JavaPlugin {
	
	@Override
	public void onEnable() {
		new PluginManager<AACore>(this);
	}

}
