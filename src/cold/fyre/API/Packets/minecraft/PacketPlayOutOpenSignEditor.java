package cold.fyre.API.Packets.minecraft;

import cold.fyre.API.Packets.PacketHandler;
import cold.fyre.API.Packets.minecraft.support.BlockPosition;

public class PacketPlayOutOpenSignEditor extends Packet {
	
	private BlockPosition position;
	
	public PacketPlayOutOpenSignEditor() { }
	
	public PacketPlayOutOpenSignEditor(BlockPosition position) { this.position = position; }
	
	public void setBlockPosition(BlockPosition position) { this.position = position; }
	
	public BlockPosition getBlockPosition() { return position; }

	@Override
	public Object getPacket() {
		if(position == null)
			position = new BlockPosition(BlockPosition.ZERO);
		
		PacketHandler bp = new PacketHandler("BlockPosition", position.getX(), position.getY(), position.getZ());
		PacketHandler ppoose = new PacketHandler("PacketPlayOutOpenSignEditor", bp.getPacket());
		return ppoose.getPacket();
	}

}
