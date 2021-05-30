package cold.fyre.API;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.entity.Player;
import org.bukkit.permissions.Permission;
import org.bukkit.permissions.PermissionAttachmentInfo;

/**
 * <b>This does not add / remove permissions from a player</b><br>
 * Contains methods that allow checking multiple permissions of a player
 * at the same time. This removes the necessity of handling each checking
 * of the permissions one a time. This also contains the ability to check
 * any numbers attached to a permission for hierarchy purposes.
 * 
 * @author Armeriness
 * @author Sommod
 * @version 2.0
 *
 */
public class PermissionChecker {
	
	/**
	 * Checks if the player has all the permissions given. The player can have more
	 * permissions than given, but must at least the ones given. Note that this will
	 * not check for parent or child permissions. Meaning if you input the permission:
	 * <b>perm.*</b>, it will not check for all child permissions.
	 * @param player - player to check.
	 * @param permissions - collection of String permissions
	 * @return True - if player has all permissions given.
	 */
	public static boolean hasAllPermissions(Player player, String... permissions) {
		if(player == null || !player.isOnline() || permissions == null) return false;
		
		for(String p : permissions)
			if(!player.hasPermission(p)) return false;
		
		return true;
	}
	
	/**
	 * Checks if the player has all the permissions given. The player can have more
	 * permissions than given, but must at least the ones given. Note that this will
	 * not check for parent or child permissions. Meaning if you input the permission:
	 * <b>perm.*</b>, it will not check for all child permissions.
	 * @param player - player to check.
	 * @param permissions - collection of Permission permissions
	 * @return True - if player has all permissions given.
	 */
	public static boolean hasAllPermissions(Player player, Permission... permissions) {
		if(player == null || !player.isOnline() || permissions == null) return false;
		
		for(Permission p : permissions)
			if(!player.hasPermission(p)) return false;
		
		return true;
	}
	
	/**
	 * Checks if the player has all the permissions given. The player can have more
	 * permissions than given, but must at least the ones given. Note that this will
	 * not check for parent or child permissions. Meaning if you input the permission:
	 * <b>perm.*</b>, it will not check for all child permissions. Note that this will
	 * check the enum based on the method <b>toString()</b>. This means that if you plan
	 * on using Enumerations, then you should also override the toString method.
	 * @param player - player to check.
	 * @param permissions - collection of enum permissions
	 * @return True - if player has all permissions given.
	 */
	public static boolean hasAllPermissions(Player player, Enum<?>... permissions) {
		if(player == null || !player.isOnline() || permissions == null) return false;
		
		for(Enum<?> e : permissions)
			if(!player.hasPermission(e.toString())) return false;
		
		return true;
	}
	
	/**
	 * Checks if the player has all the permissions given. The player can have more
	 * permissions than given, but must at least the ones given. Note that this will
	 * not check for parent or child permissions. Meaning if you input the permission:
	 * <b>perm.*</b>, it will not check for all child permissions.
	 * @param player - player to check.
	 * @param permissions - List of String permissions
	 * @return True - if player has all permissions given.
	 */
	public static boolean hasAllPermissions(Player player, List<String> permissions) {
		if(player == null || !player.isOnline() || permissions == null) return false;
		
		for(String s : permissions)
			if(!player.hasPermission(s)) return false;
		
		return true;
	}
	
	/**
	 * This checks if the player has at least ONE of the permissions given. If the player
	 * has anyone of these permissions, then this will return true. Note that this will
	 * not check for child or parent permissions. ie. <b>perm.*</b> will check for that
	 * specific permission and not any child permissions of said permission.
	 * @param player - player to check
	 * @param permissions - String array of permissions to check
	 * @return True - if at least one permission is found.
	 */
	public static boolean hasOneOfPermissions(Player player, String... permissions) {
		if(player == null || !player.isOnline() || permissions == null) return false;
		
		for(String s : permissions)
			if(player.hasPermission(s)) return true;
		
		return false;
	}
	
	/**
	 * This checks if the player has at least ONE of the permissions given. If the player
	 * has anyone of these permissions, then this will return true. Note that this will
	 * not check for child or parent permissions. ie. <b>perm.*</b> will check for that
	 * specific permission and not any child permissions of said permission.
	 * @param player - player to check
	 * @param permissions - Permission array of permissions to check
	 * @return True - if at least one permission is found.
	 */
	public static boolean hasOneOfPermissions(Player player, Permission... permissions) {
		if(player == null || !player.isOnline() || permissions == null) return false;
		
		for(Permission p : permissions)
			if(player.hasPermission(p)) return true;
		
		return false;
	}
	
	/**
	 * This checks if the player has at least ONE of the permissions given. If the player
	 * has anyone of these permissions, then this will return true. Note that this will
	 * not check for child or parent permissions. ie. <b>perm.*</b> will check for that
	 * specific permission and not any child permissions of said permission. Also note
	 * that with Enumerations, the <b>toString</b> method is used. If you are using
	 * Enumerations, then it's suggested you override the toString method.
	 * @param player - player to check
	 * @param permissions - Enum array of permissions to check
	 * @return True - if at least one permission is found.
	 */
	public static boolean hasOneOfPermissions(Player player, Enum<?>... permissions) {
		if(player == null || !player.isOnline() || permissions == null) return false;
		
		for(Enum<?> e : permissions)
			if(player.hasPermission(e.toString())) return true;
		
		return false;
	}
	
	/**
	 * This checks if the player has at least ONE of the permissions given. If the player
	 * has anyone of these permissions, then this will return true. Note that this will
	 * not check for child or parent permissions. ie. <b>perm.*</b> will check for that
	 * specific permission and not any child permissions of said permission.
	 * @param player - player to check
	 * @param permissions - List of permissions to check
	 * @return True - if at least one permission is found.
	 */
	public static boolean hasOneOfPermissions(Player player, List<String> permissions) {
		if(player == null || !player.isOnline() || permissions == null) return false;
		
		for(String s : permissions)
			if(player.hasPermission(s)) return true;
		
		return false;
	}
	
	/**
	 * Gets a list of permissions the player currently has. This list may be
	 * different than what is found within a permission plugin.
	 * @param player - Playerr to Obtain list of Permissions
	 * @return String List of permissions.
	 */
	public List<String> getEffectivePermissions(Player player) {
		List<String> toReturn = new ArrayList<String>();
		
		for(PermissionAttachmentInfo pai : player.getEffectivePermissions())
			toReturn.add(pai.getPermission());
		
		return toReturn;
	}
	
	/**
	 * Attempts to get the number at the end of permission. These are usually found
	 * on hierarchy permissions like <b>essentials.homes.5</b>. Since finding the
	 * exact number requires finding the permission, this makes it easier to get
	 * the number. To use, put in the permission upto the number. <br><br><b>ie.</b>
	 * essentials.homes will find the <i>essentials.homes.X</i> permission and return
	 * the value of X.<br><br> If the permission does not have a number attached at
	 * the end, then this will by default return the value of 0.
	 * @param player - player to check
	 * @param permission - permission to find number of
	 * @return Number attached to permission, otherwise 0.
	 */
	public int getNumberFromPermission(Player player, String permission) {
		for(String p : getEffectivePermissions(player)) {
			if(p.contains(permission)) {
				String[] split = p.split(".");
				String number = split[split.length - 1];
				
				try {
					return Integer.parseInt(number);
				} catch (NumberFormatException e) {
					return 0;
				}
			}
		}
		
		return 0;
	}

}
