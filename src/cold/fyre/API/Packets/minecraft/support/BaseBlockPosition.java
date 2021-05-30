package cold.fyre.API.Packets.minecraft.support;

import com.google.common.base.MoreObjects;

public class BaseBlockPosition implements Comparable<BaseBlockPosition> {
	
	public static final BaseBlockPosition ZERO;
	private int x, y, z;
	
	public BaseBlockPosition(int x, int y, int z) {
		this.x = x;
		this.y = y;
		this.z = z;
	}
	
	public BaseBlockPosition(double x, double y, double z) { this((int) Math.floor(x), (int) Math.floor(y), (int) Math.floor(z)); }
	
	@Override
	public boolean equals(Object obj) {
		if(this == obj)
			return true;
		else if(!(obj instanceof BaseBlockPosition))
			return false;
		
		BaseBlockPosition bbp = (BaseBlockPosition) obj;
		if(this.getX() != bbp.getX())
			return false;
		else if(this.getY() != bbp.getY())
			return false;
		else return this.getZ() == bbp.getZ();
	}
	
	@Override
	public int hashCode() { return (this.getY() + this.getZ() * 31) * 31 + this.getX(); }
	
	@Override
	public int compareTo(BaseBlockPosition o) {
		if(this.getY() == o.getY())
			return this.getZ() == o.getZ() ? this.getX() - o.getX() : this.getZ() - o.getZ();
		else
			return this.getY() - o.getY();
	}
	
	public int getX() { return x; }
	
	public int getY() { return y; }
	
	public int getZ() { return z;}
	
	protected void setX(int x) { this.x = x; }
	
	protected void setY(int y) { this.y = y; }
	
	protected void setZ(int z) { this.z = z; }
	
	@Override
	public String toString() {
		return MoreObjects.toStringHelper((Object) this).add("x", getX()).add("y", getY()).add("z", getZ()).toString();
	}
	
	static {
		ZERO = new BaseBlockPosition(0, 0, 0);
	}
}
