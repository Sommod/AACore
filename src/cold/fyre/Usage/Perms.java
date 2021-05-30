package cold.fyre.Usage;

/**
 * Contains all permissions for this plugin.
 * 
 * @author Armeriness
 * @author Sommod
 * @version 2.0
 *
 */
public enum Perms {
	
	
	OP("*"), ENABLE("enable"), DISABLE("disable");
	
	private String name;
	
	private Perms(String value) { name = value; }
	
	@Override
	public String toString() { return "aacore." + name; }

}
