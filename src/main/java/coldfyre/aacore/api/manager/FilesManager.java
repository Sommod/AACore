package coldfyre.aacore.api.manager;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.Collection;
import java.util.Date;
import java.util.Map;
import java.util.Set;

import javax.annotation.Nonnull;

import org.bukkit.Bukkit;

public class FilesManager {

	private Map<String, File> files;
	private File basePath;
	private File selectedPath;

	public static final File PLUGINS_FOLER;
	public static final File ERROR_LOGS_FOLDER;

	public FilesManager(File basePath) {
		this.basePath = basePath;
	}

	static {
		PLUGINS_FOLER = Bukkit.getPluginManager().getPlugin("AACore").getDataFolder().getParentFile();
		ERROR_LOGS_FOLDER = new File(PLUGINS_FOLER, "Error Logs");

		if (!ERROR_LOGS_FOLDER.exists())
			ERROR_LOGS_FOLDER.mkdir();
	}

	public boolean contains(String name) {
		return files.containsKey(name);
	}

	public File getDirectory(String name) {
		return getFile("dir_" + name);
	}

	public File getFile(String name) {
		return files.get(name);
	}

	public boolean exists(String name) {
		return files.get(name).exists();
	}

	public boolean mkdir(String name) {
		return mkdirDeep(selectedPath, name);
	}

	public boolean mkdirDeep(String path, String name) {
		return mkdirDeep(new File(path), name);
	}

	public boolean mkdirDeep(File path, String name) {
		if(contains("dir_" + name))
			return false;
		
		File dir = new File(path, name);
		files.put("dir_" + name, dir);

		return dir.mkdir();
	}

	private void createFile(String name) {
		if(contains(name))
			return;
		
		try {
			files.get(name).createNewFile();
		} catch (IOException e) {
			logException(e);
		}
	}

	public boolean createFile(String name, String fileName) {
		return createFileDeep(selectedPath, name, fileName);
	}

	public boolean createFileDeep(String path, String name, String file) {
		return createFileDeep(new File(path), name, file);
	}

	public boolean createFileDeep(File path, String name, String file) {
		File mkFile = new File(path, file);

		if (mkFile.exists())
			return true;

		if (!files.containsKey(name))
			files.put(name, mkFile);

		try {
			return mkFile.createNewFile();
		} catch (IOException e) {
			logException(e);
			return false;
		}
	}

	public boolean changeDirectory(String path) {
		return changeDirectory(new File(path));
	}

	public boolean changeDirectory(File path) {
		if (path == null || !path.isDirectory())
			return false;

		selectedPath = path;

		return true;
	}

	public boolean dive(String name) {
		return changeDirectory(selectedPath + "/" + name);
	}

	public boolean rise() {
		if (selectedPath.equals(basePath))
			return false;

		return changeDirectory(selectedPath.getParentFile());
	}

	public File getBasePath() {
		return basePath;
	}

	public File getCurrentDirectory() {
		return selectedPath;
	}

	public void loadData(@Nonnull InputStream is, String name) {
		if (!contains(name))
			throw new NullPointerException("Error, the given name of the file does not exist.");

		try {
			createFile(name);

			FileOutputStream fos = new FileOutputStream(files.get(name));

			int i = 0;
			byte[] buffer = new byte[1024];

			while ((i = is.read(buffer)) != -1)
				fos.write(buffer, 0, i);

			is.close();
			fos.flush();
			fos.close();
		} catch (IOException e) {
			logException(e);
		}
	}

	public boolean deleteFile(String name) {
		return files.get(name).delete();
	}

	public boolean deleteDirectory(String name) {
		return files.get("dir_" + name).delete();
	}
	
	public void addFile(String name, String path, String fileName) {
		addFile(name, new File(path.endsWith("/") ? path : path + "/" + fileName));
	}

	public void addFile(String name, File file) {
		if (!files.containsKey(name))
			files.put(name, file);
	}

	public void addDirectory(String name, String file) {
		addDirectory(name, new File(file));
	}

	public void addDirectory(String name, File file) {
		if (!files.containsKey(name))
			files.put("dir_" + name, file);
	}

	public void removeFile(String name) {
		files.get(name).delete();
	}

	public void removeDirectory(String name) {
		removeFile("dir_" + name);
	}
	
	public void renameFile(String name, File to) {
		files.get(name).renameTo(to);
		files.put(name, to);
	}

	public Collection<File> getAllFiles() {
		return files.values();
	}

	public Set<String> getAllFileKeyNames() {
		return files.keySet();
	}

	public static void logException(Exception e) {
		logException(e, new File(ERROR_LOGS_FOLDER, getTime()));
	}

	public static void logException(Exception e, String path) {
		logException(e, new File(path));
	}

	public static void logException(Exception e, String path, String file) {
		logException(e, new File(path.endsWith("/") ? path : path + "/" + file));
	}

	public static void logException(Exception e, File file) {
		try (PrintWriter writer = new PrintWriter(file)) {
			e.printStackTrace(writer);

			writer.flush();
			writer.close();
		} catch (IOException e2) {
			e.printStackTrace();
			e2.printStackTrace();
		}
	}

	public static String getTime() {
		return new SimpleDateFormat("yyyy-MM-dd HH-mm-ss'.txt'").format(new Date());
	}
}
