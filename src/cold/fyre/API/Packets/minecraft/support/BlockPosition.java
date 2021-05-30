package cold.fyre.API.Packets.minecraft.support;

public class BlockPosition extends BaseBlockPosition {

	public BlockPosition(double x, double y, double z) { super(x, y, z); }
	
	public BlockPosition(int x, int y, int z) { super(x, y, z); }
	
	public BlockPosition(BaseBlockPosition pos) { this(pos.getX(), pos.getY(), pos.getZ()); }
	
}
