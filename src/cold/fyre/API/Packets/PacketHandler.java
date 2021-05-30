package cold.fyre.API.Packets;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Locale;

import org.bukkit.Bukkit;

import cold.fyre.API.Managers.FileManager;

public class PacketHandler implements AAPacket {
	
	private Object packet;
	private String packetLocation;
	private static String packetVersion = Bukkit.getServer().getClass().getPackage().getName().split("\\.")[3];
	private static String packetName;
	
	@SuppressWarnings("static-access")
	public PacketHandler(String packetName, Object... parameters) {
		this.packetName = packetName;
		packetLocation = "net.minecraft.server." + packetVersion + "." + packetName;
		
		try {
			if(packetName.contains("$")) {
				Class<?> mainClass = Class.forName(packetLocation.split("$")[0]);
				Class<?> subClass = Class.forName(packetLocation);
				Object mainObject = mainClass.newInstance();
				
				if(parameters.length != 0)
					packet = subClass.getConstructor(getAllParameters(mainClass, convertToClass(parameters))).newInstance(getAllObjects(mainObject, parameters));
				else
					packet = subClass.getConstructor(mainClass).newInstance(mainObject);
			} else {
				Class<?> packetClass = Class.forName(packetLocation);
				
				if(parameters.length != 0)
					packet = packetClass.getConstructor(convertToClass(parameters)).newInstance(parameters);
				else
					packet = packetClass.getConstructor().newInstance();
			}
		} catch (ClassNotFoundException | InstantiationException | IllegalAccessException | IllegalArgumentException | InvocationTargetException | NoSuchMethodException | SecurityException e) {
			FileManager.logExceptionToFile("", e);
			packet = null;
		}
	}
	
	private Class<?>[] convertToClass(Object... parameters) {
		
		if(parameters.length == 0)
			return new Class<?>[0];
		
		Class<?>[] collect = new Class<?>[parameters.length];
		
		for(int i = 0; i < parameters.length; i++)
			collect[i] = parameters[i].getClass();
		
		return collect;
	}
	
	private Class<?>[] getAllParameters(Class<?> mainClass, Class<?>[] parameters) {
		Class<?>[] collect = new Class<?>[parameters.length + 1];
		collect[0] = mainClass;
		
		if(parameters.length == 0)
			return collect;
		
		for(int i = 0; i < parameters.length; i++)
			collect[i + 1] = parameters[i];
		
		return collect;
	}
	
	private Object[] getAllObjects(Object mainObject, Object... parameters) {
		Object[] collect = new Object[parameters.length + 1];
		collect[0] = mainObject;
		
		if(parameters.length == 0)
			return collect;
		
		for(int i = 0; i < parameters.length; i++)
			collect[i + 1] = parameters[i];
		
		return collect;
	}

	@Override
	public String getName() { return packetName; }

	@Override
	public Object getPacket() { return packet; }

	@Override
	public Object runMethod(String methodName, Object... parameters) {
		if(packet != null) {
			try {
				Method run = packet.getClass().getDeclaredMethod(methodName, convertToClass(parameters));
				run.setAccessible(true);
				return run.invoke(packet, parameters);
			} catch (NoSuchMethodException | SecurityException | IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
				FileManager.logExceptionToFile("", e);
			}
		}
		
		return null;
	}

	@Override
	public Object getFieldValue(String fieldName) {
		if(packet != null) {
			try {
				return packet.getClass().getDeclaredField(fieldName).get(packet);
			} catch (SecurityException | IllegalAccessException | IllegalArgumentException | NoSuchFieldException e) {
				FileManager.logExceptionToFile("", e);
			}
		}
		
		return null;
	}

	@Override
	public void setFieldValue(String fieldName, Object value) {
		if(packet != null) {
			try {
				Field set = packet.getClass().getDeclaredField(fieldName);
				set.setAccessible(true);
				set.set(packet, value);
			} catch (NoSuchFieldException | SecurityException | IllegalArgumentException | IllegalAccessException e) {
				FileManager.logExceptionToFile("", e);
			}
		}
	}
	
	public static Enum<?> getEnum(String packetName, String enumName) {
		try {
			Class<?> eClass = Class.forName("net.minecraft.server." + packetVersion + "." + packetName);
			
			if(eClass.isEnum())
				return Enum.valueOf(eClass.asSubclass(Enum.class), enumName.toUpperCase(Locale.ROOT));
		} catch (ClassNotFoundException | ClassCastException e) {
			FileManager.logExceptionToFile("", e);
		}
		
		return null;
	}
	
	public static Class<?> getClass(String packetName) {
		try {
			return Class.forName("net.minecraft.server." + packetVersion + "." + packetName);
		} catch (ClassNotFoundException e) {
			FileManager.logExceptionToFile("", e);
			return null;
		}
	}

}
