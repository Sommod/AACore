package cold.fyre.API.Packets.minecraft.support;

public enum ChatMessageType {
	GAME_INFO((byte) 2, true), SYSTEM((byte) 1, true), CHAT((byte) 0, false);

	private byte b;
	private boolean value;
	
	private ChatMessageType(byte b, boolean value) {
		this.b = b;
		this.value = value;
	}
	
	public byte getByte() { return b; }
	
	public boolean getValue() { return value; }
}
