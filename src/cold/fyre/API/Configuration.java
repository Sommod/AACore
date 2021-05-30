package cold.fyre.API;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;

import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.YamlConfiguration;

import cold.fyre.API.Managers.FileManager;

/**
 * This contains all the methods and actions to the YamlConfiguration
 * class that bukkit uses to handle .YML files. However this extension
 * adds some methods that allow saving the .YML file <b>with comments</b>!
 * This means that the file will not lose any of it's data when saving.
 * Please note that the saving method is not perfect and still freshly
 * new to the addition of AACore. If you experience or find any bugs
 * with this, please let us know.
 * 
 * 
 * @author Armeriness
 * @author Sommod
 * @since 2.1
 *
 */
public class Configuration extends YamlConfiguration {
	
	// File attached to the Yaml
	private File file;
	
	/**
	 * Allows using the normal bukkit YamlConfiguration
	 * methods for handling .YML files and data found
	 * within them, but here added methods allow to save
	 * the data and preserve the comments found within
	 * the file.
	 * @param yamlFile - File to save data to
	 */
	public Configuration(File yamlFile) {
		file = yamlFile;
		try {
			load(yamlFile);
		} catch (IOException | InvalidConfigurationException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * Loads a file as this configs file to use for saving data.
	 * Note that if the config and data found within the file
	 * are too different from each other, then improper saving
	 * or errors may occur.
	 * @param file - file to set as saving
	 */
	public void loadFile(File file) { this.file = file; }
	
	/**
	 * Gets the file that is associated with this config.
	 * @return File
	 */
	public File getFile() { return file; }
	
	/**
	 * Saves the changes made here into the file associated with this
	 * config. Note that if the file and the config that the file as stored
	 * are different, then improper saving or errors may occur.
	 */
	public void saveData() {
		String toSave = prepareSave(convertFile(getFileAsString()));
		
		try {
			BufferedWriter writer = new BufferedWriter(new FileWriter(file));
			writer.write(toSave);
			writer.flush();
			writer.close();
		} catch (IOException | NullPointerException e) {
			FileManager.logExceptionToFile("", e);
		}
	}
	
	/**
	 * Saves the data and comments from this config into a new file. This is
	 * useful for '<i>moving</i>' files to different locations.
	 * @param file - file to copy data to.
	 */
	public void copyTo(File file) {
		String toCopy = prepareSave(convertFile(getFileAsString()));
		
		try {
			BufferedWriter writer = new BufferedWriter(new FileWriter(file));
			writer.write(toCopy);
			writer.flush();
			writer.close();
		} catch (IOException | NullPointerException e) {
			FileManager.logExceptionToFile("", e);
		}
	}
	
	/**
	 * Gets the data found within the file as a String. Each line ends
	 * with the string escape sequence "\n". This can be used to turn
	 * this single string into an array.
	 * @return File data as string
	 */
	public String getFileAsString() {
		StringBuilder builder = new StringBuilder();
		
		try {
			BufferedReader reader = new BufferedReader(new FileReader(file));
			String currentLine = "";
			
			while((currentLine = reader.readLine()) != null) {
				builder.append(currentLine + "\n");
			}
			
			reader.close();
			return builder.toString();
			
		} catch (IOException | NullPointerException e) {
			FileManager.logExceptionToFile("", e);
		}
		
		return null;
	}
	
	// Converts the String into a data processing form.
	private String convertFile(String fileAsString) {
		String[] toConvert = fileAsString.split("\n");
		StringBuilder builder = new StringBuilder();
		
		for(int i = 0; i < toConvert.length; i++) {
			if(toConvert[i].trim().startsWith("#"))
				builder.append(toConvert[i].replace("#", "_COMMENT_") + "\n");
			else if(toConvert[i].isEmpty())
				builder.append("_SPACE_\n");
			else
				builder.append(toConvert[i] + "\n");
		}
		
		return builder.toString();
	}
	
	// Runs through the YAML config and the File.
	// Finds comments and data, then determines where
	// the data/comments should be.
	// NOTE: This could use some revising to make more
	// efficient and effective, this is too cluttered.
	private String prepareSave(String file) {
		StringBuilder builder = new StringBuilder();
		StringBuilder comments = new StringBuilder();
		StringBuilder data = new StringBuilder();
		List<String> configAsString = Arrays.asList(saveToString().split("\n"));
		List<String> fileAsString = Arrays.asList(convertFile(getFileAsString()).split("\n"));
		String nextData = "";
		boolean skipData = false;
		boolean firstUse = true;
		int configLine = 0;
		
		for(int fileLine = 0; fileLine < fileAsString.size() + 1; fileLine++) {
			
			// Catches footer comments if no data is left.
			// Exits the loop.
			if(fileLine == fileAsString.size()) {
				builder.append(comments.toString());
				break;
			}
			
			// Checks if the line is either a Comment or Space. If so, then it will add it to the
			// StringBuilder to add later.
			// Also checks if the skipData is True and sets it to false.
			if(fileAsString.get(fileLine).startsWith("_COMMENT") || fileAsString.get(fileLine).startsWith("_SPACE")) {
				if(skipData)
					skipData = false;
				
				if(fileLine != 0)
					comments.append("\n" + fileAsString.get(fileLine));
				else
					comments.append(fileAsString.get(fileLine) + "\n");
			
			// When the comments and Data have been added (found in the section below this),
			// Then this skips all the data until another comment section has been found.
			// Does not check spaces as there can be spaces between pieces of data, so it
			// will only try and find comments.
			} else if(skipData) {
				if(!fileAsString.get(fileLine).startsWith("_COMMENT"))
					continue;
				else {
					skipData = false;
					fileLine--;
					continue;
				}
			
			// When the line number of the file lands on a non-comment
			// or space, then this will store the data in which to stop
			// at, then add all the data prior to said placeholder and
			// add comments as well.
			} else {
				nextData = fileAsString.get(fileLine).split(":")[0];
				skipData = true;
				int check = 0;
				
				for(/**configLine*/; configLine < configAsString.size() + 1; configLine++) {
					if(configLine == configAsString.size() || configAsString.get(configLine).startsWith(nextData)) {
						if(firstUse) {
							builder.append(comments.toString());
							firstUse = false;
						} else {
							builder.append(data.toString());
							builder.append(comments.toString());
						}
						
						comments = new StringBuilder();
						data = new StringBuilder();
						nextData = "";
						break;
					} else {
						if(!configAsString.get(configLine).startsWith("#") && !configAsString.get(configLine).isEmpty()) {
							if(check == 0) {
								data.append("\n\n" + configAsString.get(configLine) + "\n");
								check++;
							} else if(configAsString.get(configLine).trim().startsWith("-"))
								data.append("\n  " + configAsString.get(configLine) + "\n");
							else
								data.append("\n" + configAsString.get(configLine) + "\n");
						}
					}
				}
			}
		}
		
		// Check if the Yaml still has data to be loaded
		if(configLine < configAsString.size()) {
			data = new StringBuilder();
			
			for(; configLine < configAsString.size(); configLine++) {
				if(configAsString.get(configLine).trim().startsWith("-"))
					data.append("\n  " + configAsString.get(configLine) + "\n");
				else
					data.append("\n" + configAsString.get(configLine) +"\n");
			}
		}
		
		// Removes the last escape sequence
		builder.append(data.toString().subSequence(0, data.toString().length() - 1));
		
		// Convert Comments and Spaces back to YML filing standard.
		String[] reform = builder.toString().split("\n");
		builder = new StringBuilder();
		firstUse = true;
		
		for(int i = 0; i < reform.length; i++) {
			if(reform[i].startsWith("_COMMENT"))
				reform[i] = reform[i].replace("_COMMENT_", "#");
			else if(reform[i].startsWith("_SPACE") && firstUse) {
				reform[i] = reform[i].replace("_SPACE_", "");
				firstUse = false;
			} else if(reform[i].startsWith("_SPACE"))
				reform[i] = reform[i].replace("_SPACE_", "\n");
			else if(reform[i] == null || reform[i].isEmpty())
				reform[i] = "\n";
			
			builder.append(reform[i]);
		}
		
		return builder.toString();
	}

}
