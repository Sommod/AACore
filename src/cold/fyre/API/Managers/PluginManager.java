package cold.fyre.API.Managers;

import java.io.File;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.util.Calendar;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.bukkit.Bukkit;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.RegisteredServiceProvider;
import org.bukkit.plugin.ServicePriority;
import org.bukkit.plugin.java.JavaPlugin;

import cold.fyre.API.Configuration;

/**
 * Contains methods that are useful for common plugin use. This helps
 * maintain data within your plugin, allowing quick actions to be done
 * that would normally required an entire method or action to be
 * constructed.
 * 
 * @author Armeriness
 * @author Sommod
 * @since 2.0
 * @param J - Your Plugin Class
 *
 */
public abstract class PluginManager<J extends JavaPlugin> {
	
	private J plugin;
	private String[] headerMessage;
	private String[] footerMessage;
	
	/**
	 * Stores the main JavaPlugin class file and initializes any
	 * data that is used with such class.
	 * @param plugin - Main Class of Plugin.
	 */
	public PluginManager(final J plugin) {
		this.plugin = plugin;
		headerMessage = new String[3];
		footerMessage = new String[3];
		initMessages();
		onStartup();
	}
	
	// Creates the default messages that will appear if they are not changed.
	@SuppressWarnings("unused")
	private void initMessages() {
		headerMessage[0] = "";
		headerMessage[1] = "############";
		footerMessage[2] = "";
		footerMessage[1] = "############";
		for(char c : plugin.getName().toCharArray()) {
			headerMessage[1] += "#";
			footerMessage[1] += "#";
		}
		
		headerMessage[2] = "+---- " + plugin.getName() + " ----+";
		footerMessage[0] = "+---- " + plugin.getName() + " ----+";
	}
	
	/**
	 * This is ran when the constructor is called. This can be used
	 * for initializing any data and objects for the plugin.
	 */
	public void onStartup() { }
	
	/**
	 * This function is NOT ran when the plugin disables. Can be used for when
	 * the plugin is being disabled, mainly for saving and data via file or
	 * server. This must be called in the onDisable() function within the main
	 * plugin class.
	 */
	public void onShutdown() { }
	
	/**
	 * Can be used to reload any data within the plugin. By default if this function
	 * is not overridden, then it will return FALSE.
	 * @return false - if not overridden
	 */
	public boolean onReload() { return false; }
	
	/**
	 * Gets the stored plugin. This allows getting a wide selection of data and functions
	 * including the server, PluginDataFolder and Loggers.
	 * @return Stored JavaPlugin
	 */
	public J getPlugin() { return plugin; }
	
	/**
	 * Returns the default plugin logger.
	 * @return Logger
	 */
	public Logger getLogger() { return plugin.getLogger(); }
	
	/**
	 * Logs a Standard info message to the Console. This uses the default
	 * logger of the server obtained from the plugin stored within this class.
	 * If you want to use the Minecraft Color Codes, use the method {@link #getConsole()}.
	 * @param message - Text to display via console.
	 */
	public void logMessage(String message) { getLogger().log(Level.INFO, message); }
	
	/**
	 * Logs all messages to the Console. This uses the default
	 * logger of the server obtained from the plugin stored within this class.
	 * If you want to use the Minecraft Color Codes, use the method {@link #getConsole()}.
	 * @param messages - Text to display via console.
	 */
	public void logMessage(String[] messages) {
		for(String m : messages)
			getLogger().log(Level.INFO, m);
	}
	
	/**
	 * Similar to the {@link #logMessage(String)} method, this allows you to set the Level of the text
	 * when sending it to the Console. To use the Minecraft Color Codes, use the {@link #getConsole()}
	 * method.
	 * @param level - Level of message
	 * @param message - Text to display via console.
	 */
	public void logMessage(Level level, String message) { getLogger().log(level, message); }
	
	/**
	 * This obtains a new instance of the default config.yml file found within the base folder
	 * of the plugin. This means that when data is changed via Console or in-game, the data
	 * will be updated in the plugin as well. This avoids the need to reload the plugin constantly
	 * to get the newest data into the plugin.
	 * @return {@link YamlConfiguration}
	 */
	@Deprecated
	public YamlConfiguration getConfig() { return YamlConfiguration.loadConfiguration(new File(plugin.getDataFolder(), "config.yml")); }
	
	/**
	 * Similar to the {@link #getConfig()} method, this obtains a new instance of the file in the format
	 * of YML. If the file is not of a YML format, then this will throw an exception of an invalid format.
	 * @param file - YML file to load
	 * @return {@link YamlConfiguration}
	 */
	@Deprecated
	public YamlConfiguration getCustomConfig(File file) { return YamlConfiguration.loadConfiguration(file); }
	
	/**
	 * This obtains a new instance of the default config.yml file found within the base folder
	 * of the plugin. This means that when data is changed via Console or in-game, the data
	 * will be updated in the plugin as well. This avoids the need to reload the plugin constantly
	 * to get the newest data into the plugin.
	 * @return {@link Configuration}
	 */
	public Configuration getConfiguration() { return new Configuration(new File(plugin.getDataFolder(), "config.yml")); }
	
	/**
	 * Similar to the {@link #getConfigration()} method, this obtains a new instance of the file in the format
	 * of YML. If the file is not of a YML format, then this will throw an exception of an invalid format.
	 * @param file - YML file to load
	 * @return {@link Configuration}
	 */
	public Configuration getCustomConfiguration(File file) { return new Configuration(file); }
	
	/**
	 * Completes the same action as the {@link #getCustomConfig(File)}, but allows inputting the file path
	 * and file name of the path.
	 * @param path - Folder to get from
	 * @param fileName - Name of file.
	 * @return {@link YamlConfiguration}
	 */
	public YamlConfiguration getCustomConfig(String path, String fileName) {
		if(path == null || fileName == null) return null;
		
		if(!path.endsWith("/"))
			path += "/";
		
		if(!fileName.endsWith(".yml"))
			fileName += ".yml";
		
		File file = new File(path + fileName);
		YamlConfiguration config = new YamlConfiguration();
		
		try {
			config.load(file);
			return config;
		} catch (IOException | InvalidConfigurationException e) {
			logMessage(Level.WARNING, "WARNING: Could not load custom YML file (" + fileName + ").\n"
					+ "Exception has been logged to file in the Exception Log Folder.");
			logExceptionToFile(e);
			return null;
		}
	}
	
	/**
	 * Completes the same action as {@link #getCustomConfig(String, String)}.
	 * @param parent - Folder to get file from.
	 * @param fileName - Name of file.
	 * @return {@link YamlConfiguration}
	 */
	public YamlConfiguration getCustomConfig(File parent, String fileName) {
		if(parent == null || fileName == null) return null;
		
		if(!fileName.endsWith(".yml"))
			fileName += ".yml";
		
		File toLoad = new File(parent, fileName);
		YamlConfiguration toReturn = new YamlConfiguration();
		
		try {
			toReturn.load(toLoad);
			return toReturn;
		} catch (IOException | InvalidConfigurationException e) {
			logMessage(Level.WARNING, "WARNING: Could not load custom YML file (" + fileName + ").\n"
					+ "Exception has been logged to file in the Exception Log Folder.");
			logExceptionToFile(e);
			return null;
		}
	}
	
	/**
	 * Gets a YML config from a custom location. The path starts at the plugins' dataFolder;
	 * so you can input the file path through the folders.
	 * @param path
	 * @return Configuration
	 */
	public Configuration getCustomConfig(String path) {
		if(path == null || path.isEmpty())
			throw new NullPointerException("Error: Path to config cannot be null or empty.");
		
		if(!path.endsWith(".yml")) {
			logMessage(Level.WARNING, "Notice, file [" + path.split("/")[path.split("/").length - 1] + "] is not of YML format, generating new file.");
			path += ".yml";
		} else if(path.startsWith("/"))
			path = path.substring(1);
		
		File ymlFile = new File(getPlugin().getDataFolder(), path);
		return new Configuration(ymlFile);
	}
	
	/**
	 * Registers a class instance into the ServiceManager. This registers the instance under the
	 * plugin that is stored in this class, not the Core Plugin.<br>
	 * Ex. registerClass(myClass.class, myClass, ServicePriority.normal);
	 * @param clazz - {@link Class}, can be an Interface of class if classes implements an interface.
	 * @param instance - object of clazz, instance of class.
	 * @param priority - Sets the priority of the registered class.
	 */
	public <T> void registerClass(Class<T> clazz, T instance, ServicePriority priority) {
		getPlugin().getServer().getServicesManager().register(clazz, instance, plugin, priority);
	}
	
	/**
	 * Obtains the RSP of the given class. Note that if the class is not registered, then
	 * this will return null.
	 * @param clazz - RSP of class to obtain.
	 * @return {@link RegisteredServiceProvider}, otherwise null
	 */
	public <C> RegisteredServiceProvider<C> getServiceProvider(Class<C> clazz) {
		return plugin.getServer().getServicesManager().getRegistration(clazz);
	}
	
	/**
	 * Gets the Registered class stored witin the RSP.
	 * @param clazz - Class instance to obtain stored within the RSP.
	 * @return Object of class.
	 */
	public <C> C getRegisteredClass(Class<C> clazz) {
		return getServiceProvider(clazz).getProvider();
	}
	
	/**
	 * Gets the RSP of the given class. Note that if the class is not registered, then this
	 * will return a null value.
	 * @param clazz - Gets the RSP of the class given.
	 * @return RSP of the Class.
	 */
	public static <C> RegisteredServiceProvider<C> getService(Class<C> clazz) {
		return Bukkit.getServer().getServicesManager().isProvidedFor(clazz) ?
				Bukkit.getServer().getServicesManager().getRegistration(clazz) : null;
	}
	
	/**
	 * Gets the object of the class stored within the RSP.
	 * @param clazz - Class to object object of.
	 * @return Object of class.
	 */
	public static <C> C getProvider(Class<C> clazz) {
		return getService(clazz).getProvider();
	}
	
	/**
	 * Gets the Message that can be used to display when the plugin is being enabled / disabled.
	 * @return String array
	 */
	public String[] getHeaderMessage() { return headerMessage; }
	
	/**
	 * Gets the Message that can be used to display on the bottom of the plugin when
	 * being enabled / disabled.
	 * @return String array
	 */
	public String[] getFooterMessage() { return footerMessage; }
	
	/**
	 * Sets the Header message to the given String Array
	 * @param message - String array to set.
	 */
	public void setHeaderMessage(String[] message) { headerMessage = message; }
	
	/**
	 * Sets the Footer message to the given String Array.
	 * @param message - String array to set.
	 */
	public void setFooterMessage(String[] message) { footerMessage = message; }
	
	/**
	 * Creates a new file by the inputed stream file information. This
	 * can be used to make a file in a folder with all the information
	 * and comments still intact. Note that if the file already exists,
	 * it will be overridden with the new file from this function. If
	 * the file is <tt>Null</tt>, then nothing will be created. This
	 * allows creating files based on the provided file within the plugin.
	 * ie, you can create a file without losing comments.
	 * @param fis - an input stream of a file to copy data from.
	 * @param folder - File to use as the path to File.
	 * @param fileName - Name of File
	 */
	public void createFile(InputStream fis, File folder, String fileName) {
		createFile(fis, new File(folder, fileName));
	}
	
	/**
	 * Creates a new file by the inputed stream file information. This
	 * can be used to make a file in a folder with all the information
	 * and comments still intact. Note that if the file already exists,
	 * it will be overridden with the new file from this function. If
	 * The file is <tt>Null</tt>, then nothing will be created. This
	 * allows creating files based on the provided file within the plugin.
	 * ie, you can create a file without losing comments.
	 * @param fis - an input stream of a file to copy data from.
	 * @param toLoad - file to have data in.
	 */
	public void createFile(InputStream fis, File toLoad) {
		if(fis == null || toLoad == null) {
			logMessage(Level.WARNING, "ERROR: Could not create file (" + toLoad.getName() + ") as either the"
					+ "data, folder or file was incorrect.");
			return;
		}
		
		try {
			FileOutputStream fos = new FileOutputStream(toLoad);
			
			if(!toLoad.exists())
				toLoad.createNewFile();
			
			int i = 0;
			byte[] buffer = new byte[1024];
			
			while((i = fis.read(buffer)) != -1)
				fos.write(buffer, 0, i);
			
			fis.close();
			fos.close();
			
		} catch (IOException e) {
			logMessage(Level.WARNING, "WARNING: Could not write data into file (" + toLoad.getName() + ")."
					+ "Exception logged to Exception Log Folder.");
			logExceptionToFile(e);
		}
	}
	
	/**
	 * Logs the Exception to the Console via the PrintStackTrace from the Thowable class.
	 * A 'header' and 'footer' are also logged to help organize all the text within.
	 * @param e - Exception to log.
	 */
	public void logException(Exception e) {
		logMessage(Level.WARNING, "======== Stacktrace ========");
		e.printStackTrace();
		logMessage(Level.WARNING, "============================");
	}
	
	/**
	 * Logs the exception to a text file. The file is stored in the <b>Logs</b> folder
	 * within the Plugin Folder. A new Folder, if not already created will be created
	 * called 'Logs'. The name of the file will be the <b>date and time</b> the exception
	 * occurred. The format of the name is: <i>YYYY-MM-DD-HMS</i>. This is good if the Server
	 * is having issuses with the plugin and can be given to the Developer(s) to find and
	 * fix the issue.
	 * @param e - Exception to log into file.
	 */
	public void logExceptionToFile(Exception e) {
		File logFolder = FileManager.getFolder(plugin.getDataFolder() + "/Logs");
		File toLog = new File(logFolder, getDate() + ".txt");
		FileManager.NotNull(toLog);
		toLog.setWritable(true);
		
		try {
			FileWriter fw = new FileWriter(toLog);
			for(StackTraceElement element : e.getStackTrace())
				fw.append(element.toString() + "\n");
			fw.close();
		} catch (IOException e1) {
			logException(e1);
			getConsole().sendMessage("§cError, could not write error data to file, another error occurred.");
		}
	}
	
	/**
	 * Obtains the Console in the form of a Mineraft Sender which allows the
	 * Minecraft Color Codes to be used.
	 * @return {@link ConsoleCommandSender}
	 */
	public ConsoleCommandSender getConsole() { return plugin.getServer().getConsoleSender(); }
	
	// Used for formating the date used in the LogExceptionToFile method.
	private String getDate() {
		String toReturn = "";
		toReturn += Calendar.YEAR + "-" + Calendar.MONTH + "-" + Calendar.DAY_OF_MONTH + "-" + Calendar.HOUR_OF_DAY + Calendar.MINUTE + Calendar.SECOND;
		return toReturn;
	}

}




