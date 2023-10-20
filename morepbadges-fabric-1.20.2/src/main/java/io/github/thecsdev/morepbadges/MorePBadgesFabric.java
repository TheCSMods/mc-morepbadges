package io.github.thecsdev.morepbadges;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.api.DedicatedServerModInitializer;

/**
 * Fabric Mod Loader entry-points for this mod.
 */
public final class MorePBadgesFabric implements ClientModInitializer, DedicatedServerModInitializer
{
	// ==================================================
	public @Override void onInitializeClient() { new io.github.thecsdev.morepbadges.client.MorePBadgesClient(); }
	public @Override void onInitializeServer() { new io.github.thecsdev.morepbadges.server.MorePBadgesServer(); }
	// ==================================================
}