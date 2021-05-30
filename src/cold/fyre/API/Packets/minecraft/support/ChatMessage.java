package cold.fyre.API.Packets.minecraft.support;

public class ChatMessage {
	
	private static final Object[] zero = new Object[0];
	private String key;
	private Object [] args;
	
	public ChatMessage(String key) {
		this.key = key;
		args = zero;
	}
	
	public ChatMessage(String key, Object... values) {
		this.key = key;
		args = values;
	}
	
	public ChatMessage copyInstance() { return new ChatMessage(key, args); }
	
	public String getKey() { return key; }
	
	public Object[] getArgs() { return args; }
	
	public void setKey(String key) { this.key = key; }
	
	public void setObjects(Object[] args) { this.args = args; }
}
