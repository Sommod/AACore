package cold.fyre.API.Packets.minecraft.support;

import java.lang.reflect.InvocationTargetException;

import cold.fyre.API.Packets.PacketHandler;

public class WorldBorder {
	
	private double centerX, centerZ, damageAmount, damageBuffer, size;
	private int warningDistance, warningTime;
	private BlockPosition position;
	
	public WorldBorder() { this(0, 0, 0.5D, 0.2D, 29999984, 4, 2); }
	
	public WorldBorder(BlockPosition center, double size, double damageAmount, double damageBuffer, int warningDistance, int warningTime) {
		centerX = center.getX();
		centerZ = center.getZ();
		this.damageBuffer = damageBuffer;
		this.size = size;
		this.damageAmount = damageAmount;
		this.warningDistance = warningDistance;
		this.warningTime = warningTime;
		this.position = center;
	}
	
	public WorldBorder(double centerX, double centerZ, double damageAmount, double damageBuffer, double size, int warningDistance, int warningTime) {
		this.centerX = centerX;
		this.centerZ = centerZ;
		this.damageAmount = damageAmount;
		this.damageBuffer = damageBuffer;
		this.size = size;
		this.warningDistance = warningDistance;
		this.warningTime = warningTime;
		position = new BlockPosition(centerX, 64, centerZ);
	}
	
	public WorldBorder(WorldBorder border) {
		this(border.getCenterX(), border.getCenterZ(), border.getDamageAmount(), border.getDamageBuffer(), border.getSize(), border.getWarningDistance(), border.getWarningTime());
	}
	
	public WorldBorder(Object NMSWorldBorder) {
		Class<?> wb = new PacketHandler("WorldBorder").getPacket().getClass();
		
		if(NMSWorldBorder.getClass().isInstance(wb)) {
			wb = NMSWorldBorder.getClass();
			try {
				centerX = (double) wb.getMethod("getCenterX").invoke(NMSWorldBorder);
				centerZ = (double) wb.getMethod("getCenterZ").invoke(NMSWorldBorder);
				damageAmount = (double) wb.getMethod("getDamageAmount").invoke(NMSWorldBorder);
				damageBuffer = (double) wb.getMethod("getDamageBuffer").invoke(NMSWorldBorder);
				warningDistance = (int) wb.getMethod("getWarningDistance").invoke(NMSWorldBorder);
				warningTime = (int) wb.getMethod("getWarningTime").invoke(NMSWorldBorder);
				size = (double) wb.getMethod("getSize").invoke(NMSWorldBorder);
			} catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException
					| NoSuchMethodException | SecurityException e) {
				e.printStackTrace();
			}
		} else
			throw new InstantiationError("Error, the given Object is not of the net.minecraft.server WorldBorder class.");
	}
	
	public double getCenterX() { return centerX; }
	
	public double getCenterZ() { return centerZ; }
	
	public double getDamageBuffer() { return damageBuffer; }
	
	public double getDamageAmount() { return damageAmount; }
	
	public double getSize() { return size; }
	
	public int getWarningDistance() { return warningDistance; }
	
	public int getWarningTime() { return warningTime; }
	
	public BlockPosition getBlockPosition() { return position; }
	
	public void setCenter(double x, double z) {
		centerX = x;
		centerZ = z;
	}
	
	public void setDamageAmount(double amount) { damageAmount = amount; }
	
	public void setDamageBuffer(double buffer) { damageBuffer = buffer; }
	
	public void setSize(double size) { this.size = size; }
	
	public void setWarningDistance(int distance) { warningDistance = distance; }
	
	public void setWarningTime(int time) { warningTime = time; }

}
