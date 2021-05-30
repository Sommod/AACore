package cold.fyre.API.Packets.minecraft.support;

public class PlayerAbilities {
	
	private NBTTagCompound nbt;
	
	public PlayerAbilities() { this(new NBTTagCompound()); }
	
	public PlayerAbilities(NBTTagCompound nbt) {
		this.nbt = nbt;
		setDefaults();
	}
	
	private void setDefaults() {
		if(!nbt.hasKey("isInvulnerable"))
			nbt.setBoolean("isInvulnerable", false);
		if(!nbt.hasKey("isFlying"))
			nbt.setBoolean("isFly", false);
		if(!nbt.hasKey("canFly"))
			nbt.setBoolean("canFly", false);
		if(!nbt.hasKey("canInstantBuild"))
			nbt.setBoolean("canInstantBuild", false);
		if(!nbt.hasKey("mayBuild"))
			nbt.setBoolean("mayBuild", true);
		if(!nbt.hasKey("flySpeed"))
			nbt.setFloat("flySpeed", 0.05F);
		if(!nbt.hasKey("walkSpeed"))
			nbt.setFloat("walkSpeed", 0.1F);
	}
	
	public void setInvulnerable(boolean value) { nbt.setBoolean("isInvulnerable", value); }
	
	public void setFlying(boolean value) { nbt.setBoolean("isFlying", value); }
	
	public void setCanFly(boolean value) { nbt.setBoolean("canFly", value); }
	
	public void setCanInstantBuild(boolean value) { nbt.setBoolean("canInstantBuild", value); }
	
	public void setMayBuild(boolean value) { nbt.setBoolean("mayBuild", value); }
	
	public void setFlySpeed(float value) { nbt.setFloat("flySpeed", value); }
	
	public void setWalkSpeed(float value) { nbt.setFloat("walkSpeed", value); }
	
	public boolean isInvulnerable() { return nbt.getBoolean("isInulnverable"); }
	
	public boolean isFlying() { return nbt.getBoolean("isFlying"); }
	
	public boolean canFly() { return nbt.getBoolean("canFly");	}
	
	public boolean canInstantBuild() { return nbt.getBoolean("canInstantBuild"); }
	
	public boolean mayBuild() { return nbt.getBoolean("mayBuild"); }
	
	public float getFlySpeed() { return nbt.getFloat("flySpeed"); }
	
	public float getWalkSpeed() { return nbt.getFloat("walkSpeed"); }
	
	public NBTTagCompound getNBT() { return nbt; }

}
