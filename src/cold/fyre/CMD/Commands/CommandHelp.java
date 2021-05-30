package cold.fyre.CMD.Commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;

import cold.fyre.API.PermissionChecker;
import cold.fyre.API.TextComponentHandler;
import cold.fyre.API.TextComponentHandler.Action;
import cold.fyre.API.Utilities;
import cold.fyre.CMD.CommandManagerHandler;
import cold.fyre.Usage.IcyHotManager;
import cold.fyre.Usage.Messages;
import cold.fyre.Usage.Perms;

public class CommandHelp extends CommandManagerHandler {

	public CommandHelp(CommandSender sender, Command command, String[] args, IcyHotManager pluginManager) {
		super(sender, command, args, pluginManager);
	}
	
	@Override
	public void execute() {
		if(isPlayer() && (!getPlayer().isOp() || !PermissionChecker.hasOneOfPermissions(getPlayer(), Perms.values()))) {
			getPlayer().sendMessage("§c§lAACore §8§l>> §7Sorry, but you do not have permission to perform this command.");
			return;
		}
		
		if(getArgs().length < 2) {
			if(isSpigot()) {
				if(isPlayer()) {
					getPlayer().sendMessage("§c§m-----§f§l Help §c§m-----");
					
					for(Messages m : Messages.values())
						getPlayer().spigot().sendMessage(TextComponentHandler.createClickableMessage(m.toCommand(), Utilities.wordWrapperSingle(m.toString(), 13), m.toCommand(), Action.SUGGEST_COMMAND));
					
					getPlayer().sendMessage("§c§m----------------");
				} else {
					getBaseSender().sendMessage("§c§m-----§f§l Help §c§m-----");
					getBaseSender().sendMessage(basicMessages());
					getBaseSender().sendMessage("§c§m----------------");
				}
			} else {
				getBaseSender().sendMessage("§c§m-----§f§l Help §c§m-----");
				getBaseSender().sendMessage(basicMessages());
				getBaseSender().sendMessage("§c§m-----§f§l Help §c§m-----");
			}
		} else {
			if(Utilities.isAny(getArg(1), "help", "h"))
				sendMessage(Messages.HELP);
			else if(Utilities.isAny(getArg(1), "enable", "e"))
				sendMessage(Messages.ENABLE);
			else if(Utilities.isAny(getArg(1), "disable", "d"))
				sendMessage(Messages.DISABLE);
			else if(Utilities.isAny(getArg(1), "list", "l"))
				sendMessage(Messages.LIST);
			else if(Utilities.isAny(getArg(1), "reload", "rl", "r"))
				sendMessage(Messages.RELOAD);
			else
				getBaseSender().sendMessage("§c§lAACore §8§l>> §7Error, no command help for §b" + getArg(1));
				
		}
	}
	
	// Used to Send a message to a sender based on the type of help message
	// they are wanting.
	private void sendMessage(Messages message) {
		getBaseSender().sendMessage("§c§m-----§f§l Help: " + message.toHeader() + " §c§m-----");
		getBaseSender().sendMessage(Utilities.wordWrapper(message.toString(), 13));
		getBaseSender().sendMessage(dashes("§c§m------------------", message.toHeader().length()));
	}
	
	// Recursive function
	private String dashes(String dashes, int value) {
		if(value != 1)
			return dashes + dashes(dashes, value - 1);
		else
			return "-";
	}
	
	// Generic Help menu.
	private String[] basicMessages() {
		String[] toReturn = {"§7Optional §f- §b[ ] §f| Required §f- §b< >" ,"§6/AACore: §7Shows version information.", "§6/AACore Help [command]:§7 Shows this menu, or details about command.",
				"§6/AACore Enable <plugin>:§7 Enables an AA-plugin.", "§6/AACore Disable <plugin>:§7 Disables an AA-plugin.", "§6/AACore Reload: §7Reloads AACore plugin.",
				"§6/AACore List: §7Shows all AA-plugins and if they are §aenabled§f/§cdisabled§7."};
		
		return toReturn;
	}

}
