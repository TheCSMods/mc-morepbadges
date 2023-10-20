package io.github.thecsdev.morepbadges.api.registry;

import static io.github.thecsdev.morepbadges.MorePBadges.getModID;
import static io.github.thecsdev.tcdcommons.api.badge.ServerPlayerBadgeHandler.getServerBadgeHandler;
import static io.github.thecsdev.tcdcommons.api.registry.TRegistries.PLAYER_BADGE;
import static io.github.thecsdev.tcdcommons.api.util.TextUtils.translatable;

import org.jetbrains.annotations.ApiStatus.Internal;

import io.github.thecsdev.morepbadges.MorePBadges;
import io.github.thecsdev.morepbadges.MorePBadgesConfig;
import io.github.thecsdev.morepbadges.api.stat.MStats;
import io.github.thecsdev.morepbadges.api.util.BeaconUtils;
import io.github.thecsdev.tcdcommons.api.badge.PlayerBadge;
import io.github.thecsdev.tcdcommons.api.badge.SimplePlayerBadge;
import io.github.thecsdev.tcdcommons.api.events.entity.LivingEntityEvent;
import io.github.thecsdev.tcdcommons.api.events.entity.player.PlayerEntityEvent;
import io.github.thecsdev.tcdcommons.api.events.server.MinecraftServerEvent;
import net.minecraft.block.Blocks;
import net.minecraft.entity.AreaEffectCloudEntity;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.stat.Stats;
import net.minecraft.util.Identifier;

public final class MPlayerBadges
{
	// ==================================================
	public static final Identifier M_BADGES_TEXTURE = new Identifier(getModID(), "textures/gui/player_badges.png");
	// ==================================================
	private MPlayerBadges() {}
	// ==================================================
	public static final PlayerBadge CELESTIAL_VOYAGER;
	public static final PlayerBadge FATE_ALCHEMIST;
	public static final PlayerBadge HIGHLY_EXPERIENCED;
	public static final PlayerBadge BEACON_MASTER;
	public static final PlayerBadge HAPPY_NEW_YEAR;
	public static final PlayerBadge LOOT_HUNTER;
	// --------------------------------------------------
	public static void register() {}
	static
	{
		//obtain mod id
		final String modId = MorePBadges.getModID();
		
		//create the badges
		final var celestial_voyager = new SimplePlayerBadge(
				translatable("morepbadges.api.registry.mplayerbadges.celestial_voyager.title"),
				translatable("morepbadges.api.registry.mplayerbadges.celestial_voyager.description"));
		final var fate_alchemist = new SimplePlayerBadge(
				translatable("morepbadges.api.registry.mplayerbadges.fate_alchemist.title"),
				translatable("morepbadges.api.registry.mplayerbadges.fate_alchemist.description"));
		final var highly_experienced = new SimplePlayerBadge(
				translatable("morepbadges.api.registry.mplayerbadges.highly_experienced.title"),
				translatable("morepbadges.api.registry.mplayerbadges.highly_experienced.description"));
		final var beacon_master = new SimplePlayerBadge(
				translatable("morepbadges.api.registry.mplayerbadges.beacon_master.title"),
				translatable("morepbadges.api.registry.mplayerbadges.beacon_master.description"));
		final var happy_new_year = new SimplePlayerBadge(
				translatable("morepbadges.api.registry.mplayerbadges.happy_new_year.title"),
				translatable("morepbadges.api.registry.mplayerbadges.happy_new_year.description"));
		final var loot_hunter = new SimplePlayerBadge(
				translatable("morepbadges.api.registry.mplayerbadges.loot_hunter.title"),
				translatable("morepbadges.api.registry.mplayerbadges.loot_hunter.description"));
		
		//register badges
		PLAYER_BADGE.register(new Identifier(modId, "celestial_voyager"), celestial_voyager);
		PLAYER_BADGE.register(new Identifier(modId, "fate_alchemist"), fate_alchemist);
		PLAYER_BADGE.register(new Identifier(modId, "highly_experienced"), highly_experienced);
		PLAYER_BADGE.register(new Identifier(modId, "beacon_master"), beacon_master);
		PLAYER_BADGE.register(new Identifier(modId, "happy_new_year"), happy_new_year);
		PLAYER_BADGE.register(new Identifier(modId, "loot_hunter"), loot_hunter);
		
		//finally, assign the values
		CELESTIAL_VOYAGER  = celestial_voyager;
		FATE_ALCHEMIST     = fate_alchemist;
		HIGHLY_EXPERIENCED = highly_experienced;
		BEACON_MASTER      = beacon_master;
		HAPPY_NEW_YEAR     = happy_new_year;
		LOOT_HUNTER        = loot_hunter;
		
		/* 
		 * # Important note:
		 * Registered common-side badges require registered renderers on the client-side.
		 */
		
		// ------------------------------
		//additional common/server-side event handlers
		// ------------------------------
		final Identifier celestialVoyagerId = celestial_voyager.getId().get();
		MinecraftServerEvent.TICKED_WORLDS.register(server ->
		{
			final int ticks = server.getTicks();
			if(ticks % 200 == 0) //10-second updates
			{
				//iterate all players
				server.getPlayerManager().getPlayerList().forEach(player ->
				{
					//check for CELESTIAL_VOYAGER
					if(player.getBlockY() >= 30000)
						tryGrantBadge(player, celestialVoyagerId);
				});
			}
			else if(ticks % 24000 == 0) //20-minute updates
				MorePBadgesConfig.updateHolidayMode();
		});
		
		final var fateAlchemistId = fate_alchemist.getId().get();
		LivingEntityEvent.STATUS_EFFECT_ADDED.register((entity, effect, cause) ->
		{
			//make sure the receiver of the effect is a server-side player
			if(!(entity instanceof ServerPlayerEntity)) return;
			
			//if the cause is an area effect cloud, then the cause is the cloud's owner
			if(cause instanceof AreaEffectCloudEntity) cause = ((AreaEffectCloudEntity)cause).getOwner();
			//if the cause ends up null, then it's the entity's responsibility
			if(cause == null) cause = entity;
			
			//now make sure the cause is also a server-side player
			if(!(cause instanceof ServerPlayerEntity)) return;
			
			//obtain effect type
			final var effectType = (effect != null) ? effect.getEffectType() : null;
			if(effectType == null) return;
			
			//----- handle effect type
			final ServerPlayerEntity finalCause = (ServerPlayerEntity)cause;
			final Runnable grant = () -> tryGrantBadge(finalCause, fateAlchemistId);
			
			//fire resistance - applying effect while about to die in lava
			if(effectType == StatusEffects.FIRE_RESISTANCE &&
					entity.getHealth() <= ((entity == cause) ? 5 : 15) &&
					entity.getWorld().getBlockState(entity.getBlockPos()).isOf(Blocks.LAVA))
				grant.run();
			
			//instant health - applying effect while low on health
			else if(effectType == StatusEffects.INSTANT_HEALTH &&
					entity.getHealth() <= ((entity == cause) ? 3 : 5))
				grant.run();
			
			//water breathing - applying effect while drowning and low on health
			else if(effectType == StatusEffects.WATER_BREATHING &&
					entity.getHealth() < ((entity == cause) ? 3 : 5) &&
					entity.getWorld().getBlockState(entity.getBlockPos()).isOf(Blocks.WATER))
				grant.run();
		});
		
		final var highlyExperiencedId = highly_experienced.getId().get();
		PlayerEntityEvent.EXPERIENCE_ADDED.register((player, exp) ->
		{
			if(player.experienceLevel >= 255)
				tryGrantBadge(player, highlyExperiencedId);
		});
		
		final var beaconMasterId = beacon_master.getId().get();
		PlayerEntityEvent.BLOCK_PLACED.register(ctx ->
		{
			//obtain info and only handle beacons
			final var world = ctx.getWorld();
			final var pos = ctx.getBlockPos();
			if(!world.getBlockState(pos).isOf(Blocks.BEACON)) return;
			
			//handle badge
			if(BeaconUtils.getBeaconLevel(
					world, pos.getX(), pos.getY(), pos.getZ(),
					state -> state.isOf(Blocks.NETHERITE_BLOCK)) >= 4)
				tryGrantBadge((ServerPlayerEntity)ctx.getPlayer(), beaconMasterId);
		});
		
		//NOTE - happy_new_year is in the firework Mixin
		
		final var lootHunterId = loot_hunter.getId().get();
		PlayerEntityEvent.LOOT_CONTAINER_OPENED.register((player, container) ->
		{
			final int looted = player.getStatHandler().getStat(Stats.CUSTOM, MStats.LOOT_CONTAINERS_OPENED);
			final int divisor = player.getServer().isDedicated() ? 32 : 64;
			tryGrantBadge(player, lootHunterId, looted / divisor);
		});
	}
	// ==================================================
	public static final @Internal boolean tryGrantBadge(ServerPlayerEntity player, Identifier badgeId) { return tryGrantBadge(player, badgeId, 1); }
	public static final @Internal boolean tryGrantBadge(ServerPlayerEntity player, Identifier badgeId, int count)
	{
		count = Math.abs(count);
		final var spbh = getServerBadgeHandler(player);
		if(spbh.getValue(badgeId) >= count) return false;
		spbh.setValue(badgeId, count);
		return true;
	}
	// ==================================================
}