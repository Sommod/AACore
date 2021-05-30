package cold.fyre.API.Packets.minecraft;

import org.bukkit.Bukkit;

/**
 * The super class of all packet classes.
 * 
 * @author Armeriness
 * @author Sommod
 * @since 2.1
 *
 */
public abstract class Packet {
	
	private String packetName;
	private String packetVersion = Bukkit.getServer().getClass().getPackage().getName().split("\\.")[3];;
	
	protected Packet() {}
	
	protected Packet(String packetName) { this.packetName = packetName; }
	
	public String getPacketName() { return packetName; }
	
	public String getPacketVersion() { return packetVersion; }
	
	/**
	 * Gets the packet in the version specific form
	 * of the server to be able to be sent to the player.
	 * Note that this does not need to be called manually as
	 * this is used when sending the packet.
	 * @return
	 */
	public abstract Object getPacket();

}
