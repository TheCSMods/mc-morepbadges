package io.github.thecsdev.morepbadges;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import io.github.thecsdev.morepbadges.api.registry.MPlayerBadges;

public abstract class MorePBadges extends Object
{
	// ==================================================
	public static final Logger LOGGER = LoggerFactory.getLogger(getModID());
	// --------------------------------------------------
	static final String ModName = "More Player Badges";
	static final String ModID = "morepbadges";
	private static MorePBadges Instance;
	// --------------------------------------------------
	//protected final ModContainer modInfo; - avoid platform-specific APIs
	// ==================================================
	public MorePBadges()
	{
		//validate instance first
		if(isModInitialized())
			throw new IllegalStateException(getModID() + " has already been initialized.");
		else if(!isInstanceValid(this))
			throw new UnsupportedOperationException("Invalid " + getModID() + " type: " + this.getClass().getName());
		
		//assign instance
		Instance = this;
		//modInfo = FabricLoader.getInstance().getModContainer(getModID()).get();
		
		//log stuff
		/*LOGGER.info("Initializing '" + getModName() + "' " + modInfo.getMetadata().getVersion() +
				" as '" + getClass().getSimpleName() + "'.");*/
		LOGGER.info("Initializing '" + getModName() + "' as '" + getClass().getSimpleName() + "'.");
		
		//register stuff
		//MStats.register(); -- done by a Mixin
		MPlayerBadges.register();
		
		//initialize other stuff
		MorePBadgesConfig.updateHolidayMode();
	}
	// ==================================================
	public static MorePBadges getInstance() { return Instance; }
	//public ModContainer getModInfo() { return modInfo; }
	// --------------------------------------------------
	public static String getModName() { return ModName; }
	public static String getModID() { return ModID; }
	// --------------------------------------------------
	public static boolean isModInitialized() { return isInstanceValid(Instance); }
	private static boolean isInstanceValid(MorePBadges instance) { return isServer(instance) || isClient(instance); }
	// --------------------------------------------------
	public static boolean isServer() { return isServer(Instance); }
	public static boolean isClient() { return isClient(Instance); }
	
	private static boolean isServer(MorePBadges arg0) { return arg0 instanceof io.github.thecsdev.morepbadges.server.MorePBadgesServer; }
	private static boolean isClient(MorePBadges arg0) { return arg0 instanceof io.github.thecsdev.morepbadges.client.MorePBadgesClient; }
	// ==================================================
}