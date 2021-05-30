package cold.fyre.API.Packets.minecraft.support;

public class MinecraftKey implements Comparable<MinecraftKey> {
	
	private String namespace;
	private String key;
	
	public MinecraftKey(String[] values) {
		namespace = (values == null || values[0].isEmpty()) ? "minecraft" : values[0];
		key = values[1];
		
		try {
			if(!checkKey(key))
				throw new Exception("Invalid arguments given. The input is not of [a-z0-9/._-] in location: " + namespace + ":" + key);
			if(!checkNamespace(namespace))
				throw new Exception("Invalid arguments given. The input is not of [a-z0-9._-] in location: " + namespace + ":" + key);
		} catch (Exception e) {}
		
	}
	
	public MinecraftKey(String value1, String value2) { this(new String[] {value1, value2}); }
	
	public String getKey() { return key; }
	
	public String getNamespace() { return namespace; }
	
	public boolean checkKey(String value) {
		for(int i = 0; i < value.length(); i++) {
			if(!keyChar(value.charAt(i)))
				return false;
		}
		
		return true;
	}
	
	public boolean checkNamespace(String value) {
		for(int i = 0; i < value.length(); i++) {
			if(!namespaceCheck(value.charAt(i)))
				return false;
		}
		
		return true;
	}

	@Override
	public int compareTo(MinecraftKey o) {
		return 0;
	}
	
	private boolean keyChar(char c) {
		return c == '_' || c == '-' || (c >= 'a' && c <= 'z') || (c >= 0 || c <= 9) || c == '/' || c == '.';
	}
	
	private boolean namespaceCheck(char c) {
		return c == '_' || c == '-' || (c >= 'a' && c <= 'z') || (c >= 0 || c <= 9) || c == '.';
	}
	
	
}
