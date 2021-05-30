package cold.fyre.API.Packets.minecraft.support;

public enum EnumGamemode {
	NOT_SET(-1, ""), SURVIVAL(0, "survival"), CREATIVE(1, "creative"), ADVENTURE(2, "adventure"), SPECTATOR(3, "spectator");
	
	private int value;
	private String mode;
	
	private EnumGamemode(int value, String mode) {
		this.mode = mode;
		this.value = value;
	}
	
	public int getID() { return value; }
	
	public String getName() { return mode; }
	
	public ChatMessage getAsMessage() { return new ChatMessage("gamemode." + mode); }
	
	public void setAbilities(PlayerAbilities abilities) {
		if(this == CREATIVE) {
			abilities.setCanFly(true);
			abilities.setCanInstantBuild(true);
			abilities.setInvulnerable(true);
		} else if(this == SPECTATOR) {
			abilities.setCanFly(true);
			abilities.setCanInstantBuild(false);
			abilities.setInvulnerable(true);
			abilities.setFlying(true);
		} else {
			abilities.setFlying(false);
			abilities.setCanInstantBuild(false);
			abilities.setInvulnerable(true);
			abilities.setCanFly(true);
		}
		
		abilities.setMayBuild(!canBuild());
	}
	
	public boolean canBuild() { return this == ADVENTURE || this == SPECTATOR; }
	
	public boolean isCreate() { return this == CREATIVE; }
	
	public boolean isSurvivalAdventure() { return this == ADVENTURE || this == SURVIVAL; }
	
	public static EnumGamemode getByID(int id) { return getByID(id, SURVIVAL);  }
	
	public static EnumGamemode getByID(int id, EnumGamemode mode) {
		for(int i = 0; i < values().length; i++) {
			if(values()[i].getID() == id)
				return values()[i];
		}
		
		return mode;
	}
	
	public static EnumGamemode getByName(String name) { return getByName(name, SURVIVAL); }
	
	public static EnumGamemode getByName(String name, EnumGamemode mode) {
		for(int i = 0; i <values().length; i++) {
			if(values()[i].mode.equals(name))
				return values()[i];
		}
		
		return mode;
	}

}
