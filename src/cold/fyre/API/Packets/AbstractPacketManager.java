package cold.fyre.API.Packets;

import org.bukkit.Server;
//import org.bukkit.block.Block;
//import org.bukkit.block.Sign;
//import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
//import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.Plugin;
import org.bukkit.scheduler.BukkitTask;

import cold.fyre.API.Packets.minecraft.support.WorldBorder;
//import cold.fyre.API.Packets.minecraft.support.WorldBorder;
import cold.fyre.Usage.IcyHotManager;

/**
 * Contains a list of different methods that use packets to which are sent to either
 * the server of player. The methods found here are SOME of the commonly used actions
 * from the NMS classes, but NOT all. Each method will have details as to what version
 * of Minecraft the method can be used with. Some methods may not be eligible on the
 * current version of Minecraft the server may run. Most of these methods do not allow you to
 * directly interact with the packets, but input data and have the code handle the rest.
 * Some methods do return manipulatable objects.
 * 
 * @author Armeriness
 * @author Sommod
 * @since 2.0
 *
 */
public abstract class AbstractPacketManager {
	
	private Server server;
	private ServerVersion serverVersion;
	private IcyHotManager manager;
	
	/**
	 * Contains the Server version of the server. Note that some of the
	 * versions may not be present, this is because many of the versions
	 * share the same class paths, i.e. the same NMS classes.
	 * 
	 * @author Armeriness
	 * @author Sommod
	 * @since 2.0
	 *
	 */
	public enum ServerVersion {
		V1_16_1("1.16.1", "v1_16_R1", 1.161), V1_16_3("1.16.3", "v1_16_R2", 1.163), ERROR("Error", "Error", 0.0);
		private String version;
		private String packageVersion;
		private double num;
		
		private ServerVersion(String name, String packageVersion, double num) {
			version = name;
			this.packageVersion = packageVersion;
			this.num = num;
		}
		
		/**
		 * Returns the String representation of the server version. Note
		 * that the String version will have the decimal points, i.e.
		 * V1_16_1 will return "1.16.1".
		 */
		@Override
		public String toString() { return version;}
		
		/**
		 * Returns the string in the form of the package name. ie. 1.16.3
		 * will return as V1_16_R2.
		 * @return
		 */
		public String toPackageString() { return packageVersion;}
		
		/**
		 * The number will return a double of the version, i.e. V1_16_1
		 * will return "1.161".
		 * @return
		 */
		public double toNumber() { return num; }
	}
	
	public AbstractPacketManager(Server server, ServerVersion version, IcyHotManager coldfyre) {
		this.server = server;
		serverVersion = version;
		manager = coldfyre;
	}
	
	protected IcyHotManager getManager() { return manager; }
	
	/**
	 * Returns the Server as an Object.
	 * @return Server
	 */
	public Server getServer() { return server; }
	
	/**
	 * Gets the Minecraft Version the server is running.
	 * @return Minecraft Version
	 */
	public ServerVersion getVersion() { return serverVersion; }
	
	/**
	 * Returns the String parameter in JSON format.
	 * @param toJSON - String to change into JSON format.
	 * @return toJSON as JSON format
	 */
	protected String formatJSON(String toJSON) { return "{\"text\":\"" + toJSON + "\"}"; }
	
	/**
	 * Sends a player a title message. This is the message that
	 * appears in big font across a players screen.<br><br>
	 * Versions: 1.16.1+
	 * @param player - Player to send title to.
	 * @param message - Message to display
	 * @param fadeIn - Seconds for title to fade in.
	 * @param showTime - Seconds for title to show.
	 * @param fadeOut - Seconds for title to fade out.
	 */
	public abstract void sendTitle(Player player, String message, int fadeIn, int showTime, int fadeOut);
	
	/**
	 * Sends a player both a Title and Subtitle message. Note that
	 * to send a player a subtitle message, a title is required.
	 * This can be used in substitution of the Title method.<br><br>
	 * Versions: 1.16.1+
	 * 
	 * @param player - Player to send title/subtitle to.
	 * @param title - Title message to display.
	 * @param subtitle - Subtitle to display
	 * @param fadeIn - Seconds for messages to fade into view.
	 * @param showTime - Seconds for messages to show.
	 * @param fadeOut - Seconds for messages to fade out of view.
	 */
	public abstract void sendSubtitle(Player player, String title, String subtitle, int fadeIn, int showTime, int fadeOut);
	
	/**
	 * Sends a player an Actionbar message.<br><br>
	 * Versions: 1.16.1+
	 * 
	 * @param player - Player to send actionbar to.
	 * @param message - Message to send to player
	 */
	public abstract void sendActionbar(Player player, String message);
	
	/**
	 * Creates a default Player NPC. There is no interaction of this NPC,
	 * and a default skin of either Steve or Alex will be used. For a better
	 * method, please look for an updated IcyHot. Returns the GameProfile of the
	 * NPC so that it can be stored and respawned if someone joins, or the server
	 * reloads.<br><br>
	 * Versions: 1.16.1+
	 * @param location - Location to spawn NPC
	 * @param uuid - UUID of NPC (Can be random).
	 * @param name - Name of NPC.
	 * @return GameProfile.
	 */
	//public abstract GameProfile createNPCPlayer(Location location, UUID uuid, String name);
	
	/**
	 * Creates an NPC that has no interaction with the world. The EntityType of the entity
	 * that is given is the entity type that will spawn in the world. Note that the
	 * NPC will start of with NoAI, Invulnerable, Silent, and NoGravity. These values
	 * are NBTTags. To edit NBTTags of the spawned NPC, use the setNBTTags Methods<br>
	 * <b>NOTE:</b> The NPC can still die from <b>The Void</b> and <b>Creative</b> Entities,
	 * (which includes mobs and players). The Name will be visible.<br><br>
	 * Versions: 1.16.1+
	 * @param type - Type of entity to spawn.
	 * @param location - Location to spawn entity at.
	 * @param name - Name of entity
	 * @return Entity
	 */
	//public abstract Entity createNPC(EntityType type, Location location, String name);
	
	/**
	 * Versions: 1.16.1+<br>
	 * Set the given NBTTag to the value towards the entity. For a list of NBTTags,
	 * visit the website:<br>
	 * <a href="https://minecraft.gamepedia.com/Tutorials/Command_NBT_tags">https://minecraft.gamepedia.com/Tutorials/Command_NBT_tags</a>
	 * @param entity - Entity to affect
	 * @param tag - Tag to edit
	 * @param value - Value to set tag to.
	 */
	//public abstract Entity editNBTTag(Entity entity, String tag, Object value);
	
	/**
	 * Versions: 1.16.1+<br>
	 * Set the given NBTTag to the value towards the ItemStack. For a list of NBTTags,
	 * visit the website:<br>
	 * <a href="https://minecraft.gamepedia.com/Tutorials/Command_NBT_tags">https://minecraft.gamepedia.com/Tutorials/Command_NBT_tags</a>
	 * @param itemStack - ItemStack to affect
	 * @param tag - tag to edit
	 * @param value - Value to set tag to.
	 */
	//public abstract ItemStack editNBTTag(ItemStack itemStack, String tag, Object value);
	
	/**
	 * Versions: 1.16.1+<br>
	 * Set the given NBTTag to the value towards the Block. For a list of NBTTags,
	 * visit the website:<br>
	 * <a href="https://minecraft.gamepedia.com/Tutorials/Command_NBT_tags">https://minecraft.gamepedia.com/Tutorials/Command_NBT_tags</a>
	 * @param Block - Block to affect
	 * @param tag - tag to edit
	 * @param value - Value to set tag to.
	 */
	//public abstract Block editNBTTag(Block block, String tag, Object value);
	
	/**
	 * Creates a tablist that will be sent to all players. Note that this
	 * is not animated, just simple a list that will be sent to players.<br>
	 * <b>Note</b>: If you want multiple lines in either the header or footer,
	 * then simply put a <i>\n</i> after the end of the line, then you can set
	 * the next line. This returns the BukkitTask so that you can cancel it at
	 * any time. Note that if you want to use the Animated tablist, it's best
	 * to stop this task first as they will be performing at the same time.<br><br>
	 * Versions: 1.16.1+
	 * 
	 * @param plugin - The plugin to handle this method.
	 * @param header - Top portion of the tablist
	 * @param footer - Bottom portion of the tablist.
	 * @return BukkitTask
	 */
	public abstract BukkitTask createTablist(Plugin plugin, String header, String footer);
	
	/**
	 * Creates a Tablist that will be sent to all players. Note that
	 * the different arguments of the header and footer, are the next
	 * message that will be sent. Similar to {@link #createTablist(Plugin, String, String)},
	 * if you want to have multiple lines in either the header or footer,
	 * you need to add a \n at the end of the line. The BukkitTask is returned
	 * so that it is possible to control whether you want it to stop it.
	 * Note that if you want to use the normal tablist, or send a different
	 * tablist, it's best to stop this task first as they will be performing
	 * at the same time. The seconds is how often the list should change.<br><br>
	 * Versions: 1.16.1+
	 * 
	 * @param plugin - The plugin to handle this method.
	 * @param header - Array of messages to show every (X) seconds.
	 * @param footer - Array of message to show every (X) seconds.
	 * @param seconds - The amount of seconds to pass before the list changes (updates)
	 * @return BukkitTask - The runnable that is being executed.
	 */
	public abstract BukkitTask createAnimatedTablist(Plugin plugin, String[] header, String[] footer, int seconds);
	
	/**
	 * Kicks the player for a 'disconnection' reason, and sends the player
	 * the given message. To use multiple lines, you need to use the String
	 * escape sequence (\n), then you can start the new line. Each new line
	 * will be automatically centered base on the text provided. This means that
	 * if the second line is larger than the first, then the <i>beginning</i>
	 * of the first line and second line will not <i>line up</i>.<br><br>
	 * Versions: 1.16.1+
	 * 
	 * @param player - Player to send message to.
	 * @param message - Message to be disaplayed.
	 */
	public abstract void sendKickMessage(Player player, String message);
	
	/**
	 * Sets a worldBorder to the specific player. This acts like a the real WorldBorder,
	 * but it only affects the specific player. Note that if the player leaves and joins
	 * back, then the world border will disappear. You will need to send the world border
	 * each time the player joins the server. Note that the border does not line up with the
	 * the block(s) of the map. To make the WorldBorder be in-line with the blocks, then the
	 * double values need to be exact numbers, ie. 10.0 or 15.0<br><br>
	 * Versions: 1.16.1+
	 * 
	 * @param player - Player to affect.
	 * @param xCenter - X location of Center
	 * @param zCenter - Z location of Center
	 * @param size - Size of the the border
	 * @param damage - Damage when outside of world border
	 * @param damageBuffer - Buffer of damage when outside border
	 * @param warnDistance - Warning distance to world border (in blocks)
	 * @param warnTime - Warning time to world border
	 */
	public abstract void setPlayersWorldBorder(Player player, double xCenter, double zCenter, double size, double damage, double damageBuffer, int warnDistance, int warnTime);
	
	/**
	 * Sets a worldBorder to the specific player. This acts like a the real WorldBorder,
	 * but it only affects the specific player. Note that if the player leaves and joins
	 * back, then the world border will disappear. You will need to send the world border
	 * each time the player joins the server. Note that the border does not line up with the
	 * the block(s) of the map. To make the WorldBorder be in-line with the blocks, then the
	 * double values need to be exact numbers, ie. 10.0 or 15.0<br><br>
	 * Versions: 1.16.1+
	 * 
	 * @param player - Player to affect.
	 * @param border - WorldBorder to place on player
	 * */
	public abstract void setPlayersWorldBorder(Player player, WorldBorder border);
	
	/**
	 * This sends a Edit Sign to the player where they can input like
	 * they were placing a sign normally. This will NOT change any sign,
	 * but will trigger the Event <b>SignInputEvent</b>. Note that
	 * the event is called ONLY when this method is used. A normal placement
	 * of a sign will not trigger the SignInputEvent.<br>
	 * Versions: 1.16.1+
	 * @param player - Player to send sign.
	 */
	//public abstract void sendSign(Player player, Sign sign);
	
	/**
	 * Sets the current world time for the given player. If you want to set the time
	 * for all players, then set the player object to NULL. The day is set in a long
	 * value; to stop time, put in a value of (-1).<br>
	 * Versions: 1.16.1+
	 * @param player
	 * @param time
	 */
	//public abstract void setTime(Player player, long worldAge, long time);
	
	/**
	 * <b>Warning:</b> This is not a method to be used. This is ONLY
	 * for the PacketHandler as it deals with packets that are specific
	 * to the server version. The method is automatic. Using this method
	 * without knowledge of what it's doing <b>WILL</b> cause errors and
	 * could potentionally crash the server. This is <b>NOT</b> thread
	 * safe.<br><br>
	 * Versions: 1.16.1+
	 */
	//@Deprecated
	//public abstract void r(Player player);
	
	/**
	 * <b>Warning:</b> This is not a method to be used. This is ONLY
	 * for the PacketHandler as it deals with packets that are specific
	 * to the server version. The method is automatic. Using this method
	 * without knowledge of what it's doing <b>WILL</b> cause errors and
	 * could potentially crash the server. This is <b>NOT</b> thread
	 * safe.<br><br>
	 * Versions: 1.16.1+
	 */
	//@Deprecated
	//protected abstract void i(Player player, Sign sign);


}
