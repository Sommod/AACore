package cold.fyre.API.Packets.minecraft;

import cold.fyre.API.Packets.Converter;
import cold.fyre.API.Packets.PacketHandler;
import cold.fyre.API.Packets.minecraft.support.ChatMessage;

public class PacketPlayOutPlayerListHeaderFooter extends Packet {
	
	private ChatMessage header, footer;
	
	public PacketPlayOutPlayerListHeaderFooter() { super("PacketPlayOutPlayerListHeaderFooter"); }
	
	public PacketPlayOutPlayerListHeaderFooter(ChatMessage header, ChatMessage footer) {
		super("PacketPlayOutPlayerListHeaderFooter");
		this.header = header;
		this.footer = footer;
	}
	
	public void setHeader(ChatMessage header) { this.header = header; }
	
	public void setFooter(ChatMessage footer) { this.footer = footer; }

	@Override
	public Object getPacket() {
		PacketHandler ppoplhf = new PacketHandler(getPacketName());
		ppoplhf.setFieldValue("header", Converter.convertChatMessage(header));
		ppoplhf.setFieldValue("footer", Converter.convertChatMessage(footer));
		return ppoplhf.getPacket();
	}

}
