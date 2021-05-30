package cold.fyre.API.Packets.minecraft;

import java.lang.reflect.InvocationTargetException;
import java.util.UUID;

import cold.fyre.API.Packets.Converter;
import cold.fyre.API.Packets.PacketHandler;
import cold.fyre.API.Packets.minecraft.support.ChatMessageType;

public class PacketPlayOutChat extends Packet {
	
	private String message;
	private ChatMessageType type;
	private UUID uuid;
	
	public PacketPlayOutChat() { }
	
	public PacketPlayOutChat(String message, ChatMessageType chatMessageType, UUID uuid) {
		this.message = message;
		this.uuid = uuid;
		type = chatMessageType;
	}
	
	public boolean isSystemGameinfo() { return type == ChatMessageType.SYSTEM || type == ChatMessageType.GAME_INFO; }
	
	public ChatMessageType getChatMessageType() { return type; }
	
	public void setString(String message) { this.message = message; }
	
	public void setUUID(UUID uuid) { this.uuid = uuid; }
	
	public void setMessageType(ChatMessageType type) { this.type = type; }
	
	private String formatToJson() {	return "{\"text\":\"" + message + "\"}"; }

	@Override
	public Object getPacket() {
		try {
			Object convertString = PacketHandler.getClass("IChatBaseComponent$ChatSerializer").getMethod("a", String.class).invoke(null, formatToJson());
			PacketHandler ppoc = new PacketHandler("PacketPlayOutChat", convertString, Converter.convertChatMessageType(type), uuid);
			return ppoc.getPacket();
		} catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException | NoSuchMethodException
				| SecurityException e) {
			e.printStackTrace();
			return null;
		}
	}

}
