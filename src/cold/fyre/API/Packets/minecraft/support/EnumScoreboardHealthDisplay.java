package cold.fyre.API.Packets.minecraft.support;

public enum EnumScoreboardHealthDisplay {
	HEARTS("hearts"), INTEGER("integer");
	
	private String info;
	
	private EnumScoreboardHealthDisplay(String value) { info = value; }
	
	@Override
	public String toString() { return info; }

}
