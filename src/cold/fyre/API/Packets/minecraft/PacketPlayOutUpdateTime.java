package cold.fyre.API.Packets.minecraft;

import cold.fyre.API.Packets.PacketHandler;

public class PacketPlayOutUpdateTime extends Packet {
	
	private long worldAge, timeOfDay;
//	private boolean flag;
	
	public PacketPlayOutUpdateTime() { super("PacketPlayOutUpdateTime"); }
	
	public PacketPlayOutUpdateTime(long worldAge, long timeOfDay, boolean flag) {
		super("PacketPlayOutUpdateTime");
		this.timeOfDay = timeOfDay;
		this.worldAge = worldAge;
//		this.flag = flag;
		
		if(!flag) {
			this.timeOfDay = -timeOfDay;
			
			if(timeOfDay == 0L)
				this.timeOfDay = -1L;
		}
	}
	
	public void setTimeOfDay(long timeOfDay) { this.timeOfDay = timeOfDay; }
	
	public void setWorldAge(long worldAge) { this.worldAge = worldAge; }

	@Override
	public Object getPacket() {
		//PacketHandler ppout = new PacketHandler(getPacketName(), worldAge, timeOfDay, flag);
		PacketHandler ppout = new PacketHandler(getPacketName());
		ppout.setFieldValue("a", worldAge);
		ppout.setFieldValue("b", timeOfDay);
		
		return ppout.getPacket();
	}

}
