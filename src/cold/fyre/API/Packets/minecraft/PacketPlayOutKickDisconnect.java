package cold.fyre.API.Packets.minecraft;

import cold.fyre.API.Packets.Converter;
import cold.fyre.API.Packets.PacketHandler;
import cold.fyre.API.Packets.minecraft.support.ChatMessage;

public class PacketPlayOutKickDisconnect extends Packet {
	
	private String[] lines;
	private boolean format;
	
	public PacketPlayOutKickDisconnect() { this(" "); }
	
	public PacketPlayOutKickDisconnect(String message) { lines = new String[] { message }; }
	
	public PacketPlayOutKickDisconnect(String[] message) { lines = message; }
	
	public String[] getMessage() { return lines; }
	
	public void addToMessage(String add) { lines[lines.length - 1] += (lines[lines.length - 1] + add); }
	
	public void addLine(String message) {
		String[] hold = lines;
		lines = new String[lines.length + 1];
		
		for(int i = 0; i < hold.length; i++)
			lines[i] = hold[i];
		
		lines[lines.length - 1] = message;
	}
	
	public void removeLine(int lineNumber) {
		if(!outOfRange(lineNumber)) {
			String[] hold = lines;
			lines = new String[lines.length - 1];
			
			boolean found = false;
			for(int i = 0; i < hold.length; i++) {
				if(found)
					lines[i - 1] = hold[i];
				else {
					if(i == lineNumber) {
						found = true;
						continue;
					} else
						lines[i] = hold[i];
				}
			}
		}
	}
	
	public void setMessage(String[] message) { lines = message; }
	
	public void formatToJson(boolean value) { format = value; }
	
	private boolean outOfRange(int value) {
		try {
			@SuppressWarnings("unused")
			String s = lines[value];
		} catch (ArrayIndexOutOfBoundsException e) {
			return true;
		}
		
		return false;
	}
	
	private String convertToSingleLine() {
		StringBuilder builder = new StringBuilder();
		
		for(int i = 0; i < lines.length; i++) {
			builder.append(lines[i]);
			
			if(i + 1 < lines.length)
				builder.append("\n");
		}
		
		return builder.toString();
	}
	
	private String formatJson(String string) { return "{\"text\":\"" + string + "\"}"; }
	
	@Override
	public Object getPacket() {
		try {
			//Object ICBC = PacketHandler.getClass("IChatBaseComponent$ChatSerializer").getMethod("a", String.class).invoke(null, format ? formatJson(convertToSingleLine()) : convertToSingleLine());
			ChatMessage ICBC = new ChatMessage(format ? formatJson(convertToSingleLine()) : convertToSingleLine());
			PacketHandler ppokd = new PacketHandler("PacketPlayOutKickDisconnect");
			ppokd.setFieldValue("a", Converter.convertChatMessage(ICBC));
			return ppokd.getPacket();
		} catch (IllegalArgumentException | SecurityException e) {
			e.printStackTrace();
			return null;
		}
	}

}
