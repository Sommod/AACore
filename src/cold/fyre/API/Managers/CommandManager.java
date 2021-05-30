package cold.fyre.API.Managers;

import org.bukkit.Bukkit;
import org.bukkit.Server;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.command.RemoteConsoleCommandSender;
import org.bukkit.entity.Player;

/**
 * IcyHotManager class for Commands. This class provides several methods
 * that supply easy / simplified use of objects that are commonly used.
 * 
 * @author Armeriness
 * @author Sommod
 * @since 2.0
 *
 * @param <P> - Your PluginManager class.
 */
public abstract class CommandManager<P extends PluginManager<?>> {
	
	// All the Different command senders
	private Player player = null;
	private ConsoleCommandSender console = null;
	private RemoteConsoleCommandSender remote = null;
	private CommandSender sender;
	
	// onCommand parameters
	private String[] args;
	private Command command;
	private P pluginManager = null;
	
	/**
	 * Constructor for Command Handling
	 * @param sender - Entity that sent the command
	 * @param command - Command issued
	 * @param args - Arguments to Command.
	 * @param pluginManager - PluginManager for plugin.
	 */
	protected CommandManager(CommandSender sender, Command command, String[] args, P pluginManager) {
		this.sender = sender;
		this.command = command;
		this.args = args;
		this.pluginManager = pluginManager;
		
		// Stores the sender as the specific sender
		if(sender instanceof Player)
			player = (Player) sender;
		else if(sender instanceof ConsoleCommandSender)
			console = (ConsoleCommandSender) sender;
		else
			remote = (RemoteConsoleCommandSender) sender;
		
		// Runs Empty method.
		// Needs to be overridden to be of any use.
		execute();
	}
	
	/**
	 * Returns the entity that issued the command as the base object of
	 * CommandSender. It's better / more proficient to use method that
	 * is the entity itself.
	 * @return {@link CommandSender}
	 */
	public CommandSender getBaseSender() { return sender; }
	
	/**
	 * Returns the CommandSender as a player. If the sender is NOT a 
	 * player or player object, then this will return null.
	 * @return {@link Player}
	 */
	public Player getPlayer() { return player; }
	
	/**
	 * Returns the CommandSender as a ConsoleCommandSender. If the sender
	 * is not Console, then this will return Null.
	 * @return {@link ConsoleCommandSender}
	 */
	public ConsoleCommandSender getConsole() { return console; }
	
	/**
	 * Returns the CommandSender as a RemoteConsoleCommandSender. If the sender
	 * is not a RemoteConsole, then this will return null. Note that this option
	 * is <b>Rarely</b> used. The RemoteConsole is, by default, disabled and
	 * has to be enabled on the server. The most commonly used program used as
	 * a remote console is Rcon. A RemoteConsoleCommandSender is the same as
	 * Console, but is usable without logging into the server via website.
	 * @return {@link RemoteConsoleCommandSender}
	 */
	public RemoteConsoleCommandSender getRemoteSender() { return remote; }
	
	/**
	 * Returns the command that was issued. This does not contain the 
	 * arguments that may have been attached to the command. This is only
	 * the base command.
	 * @return {@link Command}
	 */
	public Command getCommand() { return command; }
	
	/**
	 * Returns the array of the String arguments that are attached to the
	 * Command.
	 * @return String Array
	 */
	public String[] getArgs() { return args; }
	
	/**
	 * Gets a specified argument from the inputed value. If the value
	 * would throw and OutOfBoundsException (meaning you tried to grab
	 * a element in the array that does not exist) then this will by default
	 * return a Null Value.
	 * @param value - Element to grab.
	 * @return String element, otherwise null
	 */
	public String getArg(int value) {
		if(value < 0 | value >= args.length)
			return null;
		return args[value];
	}
	
	/**
	 * Returns the PluginManager. If the PluginManager object
	 * was not given, then this will return null. If you passed
	 * a sub-class of the PluginManager, then that sub-class
	 * will be returned.
	 * @return {@link PluginManager}
	 */
	public P getPluginManager() { return pluginManager; }
	
	/**
	 * Gets the server this plugin is running on.
	 * @return Server
	 */
	public Server getServer() { return Bukkit.getServer(); }
	
	/**
	 * Checks if the CommandSender was a player entity.
	 * @return True - if CommandSender was a player entity
	 */
	public boolean isPlayer() { return player != null; }
	
	/**
	 * Checks if the CommandSender was Console.
	 * @return True - if CommandSender was console
	 */
	public boolean isConsole() { return console != null; }
	
	/**
	 * Checks if the CommandSender was a RemoteConsole.
	 * @return True - if Console and Remote
	 */
	public boolean isRemoteConsole() { return remote != null; }
	
	/**
	 * Checks if the server is running the Spigot Jar type.
	 * @return True - if spigot
	 */
	public boolean isSpigot() {
		
		try {
			Class.forName("org.spigotmc.SpigotConfig");
			return true;
		} catch (ClassNotFoundException e) {
			return false;
		}
	}
	
	/**
	 * Will display the text given from the parameter and
	 * add the plugin name and color formatting to the message.
	 * Color code based in Minecraft Color Codes.
	 * <br><br>
	 * Format: &7[&cNAME&7] &cMESSAGE
	 * @param toDisplay - Message to display
	 * @return Formated String
	 */
	public String getPluginMessage(String toDisplay) {
		if(pluginManager != null)
			return "§7[§c" + pluginManager.getPlugin().getName() + "§7] §l§8>> §c" + toDisplay;
		else
			return "";
	}
	
	/**
	 * Method that is ran when the constructor of the class
	 * is initialized. While it is not necessary to use this
	 * method, it is already being called and ran.
	 */
	public void execute() {	}
}