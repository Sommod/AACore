package cold.fyre.CMD.Commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.plugin.Plugin;

import cold.fyre.API.PermissionChecker;
import cold.fyre.CMD.CommandManagerHandler;
import cold.fyre.Events.AADisable;
import cold.fyre.Usage.IcyHotManager;
import cold.fyre.Usage.Perms;

public class CommandDisable extends CommandManagerHandler {

	public CommandDisable(CommandSender sender, Command command, String[] args, IcyHotManager pluginManager) {
		super(sender, command, args, pluginManager);
	}
	
	@Override
	public void execute() {
		if(isPlayer() && (!PermissionChecker.hasOneOfPermissions(getPlayer(), Perms.OP, Perms.DISABLE) || !getPlayer().isOp())) {
			getPlayer().sendMessage("§c§lAACore §8§l>> §7Sorry, but you do not have permission to perform this command.");
			return;
		}
		
		Plugin target = getServer().getPluginManager().getPlugin(getArg(1));
		
		if(target == null) {
			getBaseSender().sendMessage("§c§lAACore §8§l>> §7Error, plugin §b" + getArg(1) + "§7 does not exist.");
			return;
		} else if(!target.getDescription().getName().contains("AA") || !target.getDescription().getAuthors().contains("Sommod")) {
			getBaseSender().sendMessage("§c§lAACore §8§l>> §7Error, plugin §b" + getArg(1) + "§7 is not an AA-plugin.");
			return;
		} else if(!target.isEnabled()) {
			getBaseSender().sendMessage("§c§lAACore §8§l>> §7Error, plugin is already disabled.");
			return;
		}
		
		AADisable event = new AADisable(getPluginManager(), target);
		getServer().getPluginManager().callEvent(event);
		
		if(event.isCancelled()) {
			getBaseSender().sendMessage("§c§lAACore §8§l>> §7Notice, §b" + getArg(1) + "§7 was cancelled from being disabled.");
			return;
		}
		
		getBaseSender().sendMessage("§c§lAACore §8§l>> §7Disabling plugin...");
		getServer().getPluginManager().disablePlugin(target);
		
		if(!target.isEnabled())
			getBaseSender().sendMessage("§c§lAACore §8§l>> §cPlugin Disabled");
		else
			getBaseSender().sendMessage("§c§lAACore §8§l>> §cError, a problem occurred when attempting to disable plugin.");
		
	}

}
