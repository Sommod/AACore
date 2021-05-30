package cold.fyre.CMD.Commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;

import cold.fyre.CMD.CommandManagerHandler;
import cold.fyre.Usage.IcyHotManager;

public class CommandVersion extends CommandManagerHandler {

	public CommandVersion(CommandSender sender, Command command, String[] args, IcyHotManager pluginManager) {
		super(sender, command, args, pluginManager);
	}
	
	@Override
	public void execute() {
		String[] toSend = {"§c§m-----§f§l AACore §c§m-----", "§6Version: §7" + getPluginManager().getPlugin().getDescription().getVersion(), "§6Authors: §7Armeriness, Sommod",
				"§6Description: §7A library for plugins.", "§6Aliases: §7aac, ac, core", "§c§m------------------"};
		
		getBaseSender().sendMessage(toSend);
	}

}
