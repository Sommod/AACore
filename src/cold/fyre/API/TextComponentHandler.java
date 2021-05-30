package cold.fyre.API;

import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.PotionMeta;
import org.bukkit.potion.Potion;
import org.bukkit.potion.PotionEffect;

import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.ComponentBuilder;
import net.md_5.bungee.api.chat.HoverEvent;
import net.md_5.bungee.api.chat.TextComponent;

/**
 * Contains selected methods that make use of the TextComponent classes found within the Spigot-type
 * servers. These are basic methods and do not handle the more detailed attributes that the TextComponent
 * class can perform.<br>
 * Note: This requires the <b>Spigot</b> type server to run.
 * 
 * @author Armeriness
 * @author Sommod
 * @since 2.0
 *
 */
@SuppressWarnings("deprecation")
public class TextComponentHandler {
	
	/**
	 * This contains the different <i>Actions</i> that can be taken when using creating
	 * a TextCompononet. This only contains the mainly used actions, thus not all the
	 * options are available to to be used when creating a TextComponent class.
	 * 
	 * @author Armeriness
	 * @author Sommod
	 * @since 2.0
	 *
	 */
	public enum Action {
		RUN_COMMAND, SUGGEST_COMMAND, OPEN_URL;
	}
	
	/**
	 * Converts the message into a TextComponent object. This does not require a hover message. If you
	 * choose not to use a hover message, simply give a NULL value.
	 * @param message - Message to convert into a TextComponent object
	 * @param hoverMessage - Message to display when mouse hovers over text
	 * @return TextComponent
	 */
	public static TextComponent createMessage(String message, String hoverMessage) {
		TextComponent main = new TextComponent(message);
		
		// This is a Legacy Constructor after 1.16.1
		// 1.16.3+ has new constructors.
		if(hoverMessage != null) 
			main.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new ComponentBuilder(hoverMessage).create()));

		return main;
	}
	
	/**
	 * Creates a message that can be sent to a player. This supports three most commonly used actions
	 * of the click event.
	 * @param message - Message to convert to  a TextComponent Object
	 * @param hoverMessage - Message to display when hovering over text
	 * @param clickMessage - Message / action to perform when clicked.
	 * @return TextComponent
	 */
	public static TextComponent createClickableMessage(String message, String hoverMessage, String clickMessage, Action action) {
		TextComponent main = createMessage(message, hoverMessage);
		main.setClickEvent(new ClickEvent(ClickEvent.Action.valueOf(action.name()), clickMessage));
		
		return main;
	}
	
	/**
	 * Sends a player a message with the item stats of the item given. <b>Some</b> ItemFlags / NBT tags
	 * will be respected if applied, but not all. If the item shows potion effects, then the display of
	 * such effects will be altered slightly to make more compact.
	 * @param message - Message to display in chat.
	 * @param item - Item to display when hovering over text
	 * @return TextComponent
	 */
	public static TextComponent createItem(String message, ItemStack item) { return createMessage(message, convertItemToString(item)); }
	
	// Converts the item into a string to be used as a hover message
	// from the TextComponent. This is used in substitution of the already
	// available method in the HoverEvent of showing an item. This is because
	// I am stupid and can't figure it out.
	// -_- Don't judge me...
	private static String convertItemToString(ItemStack item) {
		boolean potionItem = false;
		StringBuilder builder = new StringBuilder();
		
		builder.append(item.getItemMeta().getDisplayName() + "\n");
		
		try {
			Potion.fromItemStack(item);
			potionItem = true;
		} catch (IllegalArgumentException e) { }
		
		if(potionItem && !item.getItemMeta().hasItemFlag(ItemFlag.HIDE_POTION_EFFECTS)) {
			PotionMeta meta = (PotionMeta) item.getItemMeta();
			
			for(String s : convertPotionEffectsToString(meta.getCustomEffects()))
				builder.append(s + "\n");
		}
		
		if(!item.getItemMeta().hasItemFlag(ItemFlag.HIDE_ENCHANTS)) {
			for(String s : convertEnchantsToString(item.getItemMeta().getEnchants()))
				builder.append(s + "\n");
		}
		
		for(String s : item.getItemMeta().getLore())
			builder.append(s + "\n");
		
		if(!item.getItemMeta().hasItemFlag(ItemFlag.HIDE_UNBREAKABLE))
			builder.append("§3Unbreakable\n");
		
		builder.append("minecraft:" + item.getType().name());
		return builder.toString();
	}
	
	// Converts any potion effects into an array of strings. The format for the
	// effects will be: Name (*:**)
	private static String[] convertPotionEffectsToString(List<PotionEffect> effects) {
		String[] names = new String[effects.size()];
		
		for(int pEffect = 0; pEffect < effects.size(); pEffect++) {
			String displayName = effects.get(pEffect).getType().getName().toLowerCase();
			String[] split = displayName.split("_");
			split = capitalizeFirstLetter(split);
			displayName = "";
			
			for(String s : split)
				displayName += (s + " ");
			
			displayName += "(" + convertTimeToString(effects.get(pEffect).getDuration()) + ")";
			names[pEffect] = displayName;
		}
		
		return names;
	}
	
	// Converts any enchants into their name and level The format is:
	// Name Level
	private static String[] convertEnchantsToString(Map<Enchantment, Integer> enchants) {
		String[] names = new String[enchants.size()];
		
		int i = 0;
		for(Iterator<Enchantment> e = enchants.keySet().iterator(); e.hasNext();) {
			Enchantment ent = e.next();
			String[] split = capitalizeFirstLetter(ent.getName().toLowerCase().split("_"));
			String convert = "";
			
			for(String s : split)
				convert += (s + " ");
			names[i] = (convert + enchants.get(ent));
			i++;
		}
		
		return names;
	}
	
	// Converts the time of a potion into a string value in the form
	// of *:**.
	private static String convertTimeToString(int time) { return ("" + ((time / 20) / 60) + ":" + ((time / 20) % 60)); }
	
	// Capitalizes the first letter of the word rather than
	// the entire word
	private static String capitalizeFirstLetter(String word) {
		String letter = word.charAt(0) + "";
		letter = letter.toUpperCase();
		word = letter + word.substring(1);
		return word;
	}
	
	// Capitalizes all the words in the array of the first letter
	private static String[] capitalizeFirstLetter(String[] words) {
		for(int i = 0; i < words.length; i++)
			words[i] = capitalizeFirstLetter(words[i]);
		return words;
	}

}
