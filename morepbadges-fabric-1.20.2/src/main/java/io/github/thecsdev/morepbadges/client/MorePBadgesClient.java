package io.github.thecsdev.morepbadges.client;

import io.github.thecsdev.morepbadges.MorePBadges;
import io.github.thecsdev.morepbadges.api.client.registry.MClientPlayerBadges;
import net.minecraft.client.MinecraftClient;

public final class MorePBadgesClient extends MorePBadges
{
	// ==================================================
	public static final MinecraftClient MC_CLIENT = MinecraftClient.getInstance();
	// ==================================================
	public MorePBadgesClient()
	{
		//initialize and register stuff
		MClientPlayerBadges.register();
	}
	// ==================================================
}