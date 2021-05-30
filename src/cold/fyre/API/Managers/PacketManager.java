package cold.fyre.API.Managers;

//import java.lang.reflect.InvocationTargetException;

//import org.bukkit.Material;
import org.bukkit.Server;
//import org.bukkit.block.Block;
//import org.bukkit.block.Sign;
//import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
//import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.Plugin;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scheduler.BukkitTask;

//import cold.fyre.API.Events.SignInputEvent;
import cold.fyre.API.Packets.AbstractPacketManager;
//import cold.fyre.API.Packets.PacketHandler;
import cold.fyre.API.Packets.minecraft.PacketPlayOutKickDisconnect;
//import cold.fyre.API.Packets.minecraft.PacketPlayOutOpenSignEditor;
import cold.fyre.API.Packets.minecraft.PacketPlayOutPlayerListHeaderFooter;
import cold.fyre.API.Packets.minecraft.PacketPlayOutTitle;
import cold.fyre.API.Packets.minecraft.PacketPlayOutTitle.EnumTitleAction;
import cold.fyre.API.Packets.minecraft.PacketPlayOutWorldBorder;
import cold.fyre.API.Packets.minecraft.PacketPlayOutWorldBorder.EnumWorldBorderAction;
//import cold.fyre.API.Packets.minecraft.PacketPlayOutUpdateTime;
//import cold.fyre.API.Packets.minecraft.PacketPlayOutWorldBorder;
//import cold.fyre.API.Packets.minecraft.PacketPlayOutWorldBorder.EnumWorldBorderAction;
import cold.fyre.API.Packets.minecraft.PacketSender;
//import cold.fyre.API.Packets.minecraft.support.BlockPosition;
import cold.fyre.API.Packets.minecraft.support.ChatMessage;
import cold.fyre.API.Packets.minecraft.support.WorldBorder;
//import cold.fyre.API.Packets.minecraft.support.WorldBorder;
import cold.fyre.Usage.IcyHotManager;
//import io.netty.channel.Channel;
//import io.netty.channel.ChannelDuplexHandler;
//import io.netty.channel.ChannelHandlerContext;
//import io.netty.channel.ChannelPipeline;
//import io.netty.channel.ChannelPromise;

/**
 * This contains default methods that are commonly used for handling certain
 * server or player attributes. 
 * 
 * @author Armeriness
 * @author Sommod
 * @since 2.0
 *
 */
public class PacketManager extends AbstractPacketManager {

	public PacketManager(Server server, ServerVersion version, IcyHotManager coldfyre) {
		super(server, version, coldfyre);
	}

	@Override
	public void sendTitle(Player player, String message, int fadeIn, int showTime, int fadeOut) {
		PacketPlayOutTitle title = new PacketPlayOutTitle(EnumTitleAction.TITLE, new ChatMessage(message), fadeIn, showTime, fadeOut);
		PacketSender.sendPacket(player, title);
	}

	@Override
	public void sendSubtitle(Player player, String title, String subtitle, int fadeIn, int showTime, int fadeOut) {
		PacketPlayOutTitle main = new PacketPlayOutTitle(EnumTitleAction.TITLE, new ChatMessage(title), fadeIn, showTime, fadeOut);
		PacketPlayOutTitle sub = new PacketPlayOutTitle(EnumTitleAction.SUBTITLE, new ChatMessage(subtitle), fadeIn, showTime, fadeOut);
		PacketSender.sendPacket(player, main);
		PacketSender.sendPacket(player, sub);
	}

	@Override
	public void sendActionbar(Player player, String message) {
		PacketPlayOutTitle bar = new PacketPlayOutTitle(EnumTitleAction.ACTIONBAR, new ChatMessage(message));
		PacketSender.sendPacket(player, bar);
	}

	/*
	@Override
	public Entity editNBTTag(Entity entity, String tag, Object value) {
		try {
			Object craftEntity = entity.getClass().getMethod("getHandle").invoke(entity);
			craftEntity.getClass().getMethod("c", PacketHandler.getClass("NBTTagCompound")).invoke(craftEntity, setTag(new PacketHandler("NBTTagCompound"), tag, value));
			return (Entity) craftEntity;
			
		} catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException | NoSuchMethodException
				| SecurityException e) {
			e.printStackTrace();
			return entity;
		}
	}

	@Override
	public ItemStack editNBTTag(ItemStack itemStack, String tag, Object value) {
		try {
			Object craftItem = itemStack.getClass().getMethod("getHandle").invoke(itemStack);
			Object nbt = craftItem.getClass().getMethod("getTag").invoke(craftItem) != null ? setTag(craftItem.getClass().getMethod("getTag").invoke(craftItem), tag, value) : setTag(new PacketHandler("NBTTagCompund").getPacket(), tag, value);
			
			craftItem.getClass().getMethod("setTag", nbt.getClass()).invoke(craftItem, nbt);
			return (ItemStack) craftItem.getClass().getMethod("asBukkitCopy").invoke(craftItem);
		} catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException | NoSuchMethodException
				| SecurityException e) {
			e.printStackTrace();
			return itemStack;
		}
	}

	@Override
	public Block editNBTTag(Block block, String tag, Object value) {
		try {
			Object tileEntity = block.getClass().cast(PacketHandler.getClass("TileEntity"));
			tileEntity.getClass().getMethod("save", new PacketHandler("NBTTagCompound").getPacket().getClass()).invoke(tileEntity, setTag(new PacketHandler("NBTTagCompound").getPacket(), tag, value));
			return (Block) tileEntity;
		} catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException | NoSuchMethodException
				| SecurityException e) {
			e.printStackTrace();
			return null;
		}
	}
	
	private Object setTag(Object nbtPacket, String tag, Object value) {
		Object enclose = nbtPacket;
		
		try {
			if (value instanceof Integer)
				enclose.getClass().getMethod("setInt", tag.getClass(), value.getClass()).invoke(enclose, tag, (int) value);
	        else if (value instanceof Double)
	        	enclose.getClass().getMethod("setDouble", tag.getClass(), value.getClass()).invoke(enclose, tag, (double) value);
	        else if (value instanceof Float)
	        	enclose.getClass().getMethod("setFloat", tag.getClass(), value.getClass()).invoke(enclose, tag, (float) value);
	        else if (value instanceof Long)
	        	enclose.getClass().getMethod("setLong", tag.getClass(), value.getClass()).invoke(enclose, tag, (long) value);
	        else if (value instanceof String)
	        	enclose.getClass().getMethod("setString", tag.getClass(), String.class).invoke(enclose, tag, String.valueOf(value));
	        else if (value instanceof Short)
	        	enclose.getClass().getMethod("setShort", tag.getClass(), value.getClass()).invoke(enclose, tag, (short) value);
	        else if (value instanceof Boolean)
	        	enclose.getClass().getMethod("setBoolean", tag.getClass(), value.getClass()).invoke(enclose, tag, (boolean) value);
	        else if (value instanceof Byte)
	        	enclose.getClass().getMethod("setByte", tag.getClass(), value.getClass()).invoke(enclose, tag, (byte) value);
	        else if (value instanceof byte[])
	        	enclose.getClass().getMethod("setByteArray", tag.getClass(), value.getClass()).invoke(enclose, tag, (byte[]) value);
	        else if (value instanceof int[])
	        	enclose.getClass().getMethod("setIntArray", tag.getClass(), value.getClass()).invoke(enclose, tag, (int[]) value);
	        else if (value.getClass().isInstance(PacketHandler.getClass("NBTBase")))
	        	enclose.getClass().getMethod("set", tag.getClass(), value.getClass()).invoke(enclose, tag, value.getClass().cast(PacketHandler.getClass("NBTBase")));
			
			return enclose;
		} catch (SecurityException | IllegalAccessException | IllegalArgumentException | InvocationTargetException | NoSuchMethodException e) {
			e.printStackTrace();
			return nbtPacket;
		}
	}
	*/

	@Override
	public BukkitTask createTablist(Plugin plugin, String header, String footer) {
		return new BukkitRunnable() {
			protected PacketPlayOutPlayerListHeaderFooter list = new PacketPlayOutPlayerListHeaderFooter(new ChatMessage(header), new ChatMessage(footer));
			
			@Override
			public void run() {
				
				
				if(getServer().getOnlinePlayers().size() == 0)
					return;
				
				for(Player player : getServer().getOnlinePlayers())
					PacketSender.sendPacket(player, list);
			}
			
		}.runTaskTimer(plugin, 0L, 20L);
	}

	@Override
	public BukkitTask createAnimatedTablist(Plugin plugin, String[] header, String[] footer, int seconds) {
		return new BukkitRunnable() {
			private int hCounter = 0;
			private int fCounter = 0;
			private String[] storeHeader = header;
			private String[] storeFooter = footer;
			PacketPlayOutPlayerListHeaderFooter list = new PacketPlayOutPlayerListHeaderFooter(new ChatMessage(header[0]), new ChatMessage(footer[0]));
			
			@Override
			public void run() {
				
				if(hCounter == storeHeader.length) {
					hCounter = 0;
					list.setHeader(new ChatMessage(header[0]));
				} else {
					list.setHeader(new ChatMessage(header[hCounter]));
					hCounter++;
				}
				
				if(fCounter == storeFooter.length) {
					fCounter = 0;
					list.setFooter(new ChatMessage(footer[0]));
				} else {
					list.setFooter(new ChatMessage(footer[fCounter]));
					fCounter++;
				}
				
				if(getServer().getOnlinePlayers().size() == 0)
					return;
				
				for(Player player : getServer().getOnlinePlayers())
					PacketSender.sendPacket(player, list);
			}
			
		}.runTaskTimer(plugin, 0L, (long)(seconds * 20));
	}

	@Override
	public void sendKickMessage(Player player, String message) {
		PacketPlayOutKickDisconnect kick = new PacketPlayOutKickDisconnect(message);
		PacketSender.sendPacket(player, kick);
	}

	
	@Override
	public void setPlayersWorldBorder(Player player, double xCenter, double zCenter, double size, double damage, double damageBuffer, int warnDistance, int warnTime) {
		setPlayersWorldBorder(player, new WorldBorder(xCenter, zCenter, damage, damageBuffer, size, warnDistance, warnTime));
	}
	
	@Override
	public void setPlayersWorldBorder(Player player, WorldBorder border) {
		PacketPlayOutWorldBorder world = new PacketPlayOutWorldBorder(border, EnumWorldBorderAction.INITIALIZE);
		PacketSender.sendPacket(player, world);
	}
	
	/*
	@Override
	public void setTime(Player player, long worldAge, long time) {
		PacketPlayOutUpdateTime update = new PacketPlayOutUpdateTime(worldAge, time, true);
		PacketSender.sendPacket(player, update);
	}

	@SuppressWarnings("deprecation")
	@Override
	public void sendSign(Player player, Sign sign) {
		i(player, sign);
		
		PacketPlayOutOpenSignEditor signEditor = new PacketPlayOutOpenSignEditor();
		Block signBlock;
		
		if(sign == null) {
			signBlock = player.getLocation().getChunk().getBlock(0, 0, 0);
			
			if(signBlock.getType() != Material.LEGACY_SIGN_POST) {
				signBlock.setType(Material.LEGACY_SIGN_POST);
				signBlock.getState().update();
			}
			
		} else
			signBlock = sign.getBlock();
		
		BlockPosition position = new BlockPosition(signBlock.getX(), signBlock.getY(), signBlock.getZ());
		signEditor.setBlockPosition(position);
		
		try {
			Object handle = player.getClass().getMethod("getHandle").invoke(player);
			PacketHandler blockP = new PacketHandler("BlockPosition", position.getX(), position.getY(), position.getZ());
			Object craftWorld = handle.getClass().getField("world").get(handle);
			Object tesWorld = craftWorld.getClass().getMethod("getTileEntity", blockP.getPacket().getClass()).invoke(craftWorld, blockP);
			PacketHandler tesCast = new PacketHandler("TileEntitySign");
			Object tes = tesWorld.getClass().cast(tesCast.getPacket());
			tes.getClass().getField("isEditable").set(tes, true);
			
			PacketSender.sendPacket(player, signEditor);
			
		} catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException | NoSuchMethodException | SecurityException | NoSuchFieldException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void r(Player player) {
		try {
			Object handle = player.getClass().getMethod("getHandle").invoke(player);
			Object playerConnection = handle.getClass().getField("playerConnection").get(handle);
			Object networkManager = playerConnection.getClass().getField("networkManager").get(playerConnection);
			Channel channel = (Channel) networkManager.getClass().getField("channel").get(networkManager);
			
			channel.eventLoop().submit(() -> {
				channel.pipeline().remove(player.getName());
				return null;
			});
		} catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException | NoSuchMethodException
				| SecurityException | NoSuchFieldException e) {
			e.printStackTrace();
		}
	}

	@Override
	protected void i(Player player, Sign sign) {
		try {
			Object craftPlayer = player.getClass().getMethod("getHandle").invoke(player);
			Object packetObject = new PacketHandler("PacketPlayInUpdateSign").getPacket();
			PacketManager pm = this;
			
			ChannelDuplexHandler cdh = new ChannelDuplexHandler() {
				
				public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
					if(msg.getClass().isInstance(packetObject)) {
						String[] lines = (String[]) msg.getClass().getMethod("c").invoke(msg);
						SignInputEvent sie = new SignInputEvent(pm, lines, player, sign);
						getServer().getPluginManager().callEvent(sie);
						return;
					}
					super.channelRead(ctx, msg);
				}
				
				public void write(ChannelHandlerContext chc, Object packet, ChannelPromise cp) throws Exception {
					super.write(chc, packet, cp);
				}
			};
			
			Object playerConnection = craftPlayer.getClass().getField("playerConnection").get(craftPlayer);
			Object networkManager = playerConnection.getClass().getField("networkManager").get(playerConnection);
			Object channel = networkManager.getClass().getField("channel").get(networkManager);
			ChannelPipeline line = (ChannelPipeline) channel.getClass().getMethod("pipeline").invoke(channel);
			line.addBefore("packet_handler", player.getName(), cdh);
			
		} catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException | NoSuchMethodException | SecurityException | NoSuchFieldException e) {
			e.printStackTrace();
		}
	}
	*/

}
