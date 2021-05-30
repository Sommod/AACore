package cold.fyre.API.Packets;

import cold.fyre.API.Packets.minecraft.support.BlockPosition;
import cold.fyre.API.Packets.minecraft.support.ChatMessage;
import cold.fyre.API.Packets.minecraft.support.ChatMessageType;
import cold.fyre.API.Packets.minecraft.support.MinecraftKey;
import cold.fyre.API.Packets.minecraft.support.NBTTagCompound;

public class Converter {
	
	public static Object convertNBT(NBTTagCompound inner) {
		PacketHandler nbt = new PacketHandler("NBTTagCompound");
		
		for(String key : inner.getKeys()) {
			if(inner.get(key) instanceof NBTTagCompound)
				nbt.runMethod("set", convertNBT(inner.getNBTTagCompound(key)));
			else {
				String name = getMethod(inner.get(key));
				
				if(inner.get(key) instanceof Byte)
					nbt.runMethod(name, inner.getByte(key));
				else if(inner.get(key) instanceof Byte[])
					nbt.runMethod(name, inner.getByteArray(key));
				else if(inner.get(key) instanceof Integer)
					nbt.runMethod(name, inner.getInt(key));
				else if(inner.get(key) instanceof Integer[])
					nbt.runMethod(name, inner.getIntArray(key));
				else if(inner.get(key) instanceof Double)
					nbt.runMethod(name, inner.getDouble(key));
				else if(inner.get(key) instanceof Float)
					nbt.runMethod(name, inner.getFloat(key));
				else if(inner.get(key) instanceof Long)
					nbt.runMethod(name, inner.getLong(key));
				else if(inner.get(key) instanceof Short)
					nbt.runMethod(name, inner.getShort(key));
				else if(inner.get(key) instanceof String)
					nbt.runMethod(name, inner.getString(key));
				else if(inner.get(key) instanceof Boolean)
					nbt.runMethod(name, inner.getBoolean(key));
			}
		}
		
		return nbt.getPacket();
	}
	
	public static Object convertMinecraftKey(MinecraftKey key) {
		Object[] pars = new Object[] {key.getNamespace(), key.getKey()};
		PacketHandler mk = new PacketHandler("MinecraftKey", pars);
		
		return mk.getPacket();
	}
	
	public static Enum<?> convertChatMessageType(ChatMessageType type) {
		return PacketHandler.getEnum("ChatMessageType", type.name());
	}
	
	public static Object convertChatMessage(ChatMessage message) {
		PacketHandler cm = new PacketHandler("ChatMessage", message.getKey(), message.getArgs());
		return cm.getPacket();
	}
	
	public static Object convertBlockPosition(BlockPosition blockPosition) {
		PacketHandler bp = new PacketHandler("BlockPosition", blockPosition.getX(), blockPosition.getY(), blockPosition.getZ());
		return bp.getPacket();
	}
	
	private static String getMethod(Object value) {
		
		if(value instanceof Byte)
			return "setByte";
		else if(value instanceof Byte[])
			return "setByteArray";
		else if(value instanceof Integer)
			return "setInt";
		else if(value instanceof Integer[])
			return "setIntArray";
		else if(value instanceof Double)
			return "setDouble";
		else if(value instanceof Float)
			return "setFloat";
		else if(value instanceof Long)
			return "setLong";
		else if(value instanceof Short)
			return "setShort";
		else if(value instanceof String)
			return "setString";
		else if(value instanceof Boolean)
			return "setBoolean";
		else
			return "set";
	}

}
