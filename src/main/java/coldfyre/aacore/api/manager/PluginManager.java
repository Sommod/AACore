package coldfyre.aacore.api.manager;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.server.PluginDisableEvent;
import org.bukkit.plugin.java.JavaPlugin;

public class PluginManager<J extends JavaPlugin> implements Listener {
	
	private final J plugin;
	private FilesManager filesManager;
	
	public PluginManager(final J plugin) {
		this(plugin, null);
	}
	
	public PluginManager(final J plugin, FilesManager filesManager) {
		this.plugin = plugin;
		this.filesManager = filesManager;
		onStartup();
	}
	
	public J getPlugin() { return plugin; }
	
	public void onStartup() { }
	public void onShutdown() { }
	
	public void loadFilesManager(FilesManager filesManager) {
		this.filesManager = filesManager;
	}
	
	public FilesManager getFilesManager() {
		return filesManager;
	}
	
	@EventHandler
	private void onDisable(PluginDisableEvent event) {
		if(event.getPlugin() != plugin)
			return;
		
		PluginDisableEvent.getHandlerList().unregister(this);
		onShutdown();
	}
	
	@Override
	protected void finalize() throws Throwable {
		PluginDisableEvent.getHandlerList().unregister(this);
		super.finalize();
	}
}
