package io.github.thecsdev.morepbadges.api.stat;

import static io.github.thecsdev.morepbadges.MorePBadges.getModID;
import static net.minecraft.registry.Registries.CUSTOM_STAT;
import static net.minecraft.stat.Stats.CUSTOM;

import java.util.Objects;
import java.util.regex.Pattern;

import org.jetbrains.annotations.ApiStatus.Internal;

import io.github.thecsdev.tcdcommons.api.events.entity.player.PlayerEntityEvent;
import io.github.thecsdev.tcdcommons.api.events.server.PlayerManagerEvent;
import io.github.thecsdev.tcdcommons.api.hooks.entity.EntityHooks;
import net.minecraft.entity.Entity;
import net.minecraft.registry.Registry;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.stat.StatFormatter;
import net.minecraft.stat.Stats;
import net.minecraft.util.Identifier;

public final class MStats
{
	// ==================================================
	private MStats() {}
	// ==================================================
	public static final Identifier CHAT_MESSAGES_SENT;
	public static final Identifier ITEMS_SHARED;
	public static final Identifier LOOT_CONTAINERS_OPENED;
	// --------------------------------------------------
	public static void register() {/*Note: DO NOT execute any code here!*/}
	static
	{
		//create identifiers
		final String modId = getModID();
		CHAT_MESSAGES_SENT     = new Identifier(modId, "chat_messages_sent");
		ITEMS_SHARED           = new Identifier(modId, "items_shared");
		LOOT_CONTAINERS_OPENED = new Identifier(modId, "loot_containers_opened");
		
		//register custom stat types
		{
			//first to the custom stat registry
			Registry.register(CUSTOM_STAT, CHAT_MESSAGES_SENT, CHAT_MESSAGES_SENT);
			Registry.register(CUSTOM_STAT, ITEMS_SHARED, ITEMS_SHARED);
			Registry.register(CUSTOM_STAT, LOOT_CONTAINERS_OPENED, LOOT_CONTAINERS_OPENED);
			
			//and then to the custom stats
			CUSTOM.getOrCreateStat(CHAT_MESSAGES_SENT, StatFormatter.DEFAULT);
			CUSTOM.getOrCreateStat(ITEMS_SHARED, StatFormatter.DEFAULT);
			CUSTOM.getOrCreateStat(LOOT_CONTAINERS_OPENED, StatFormatter.DEFAULT);
		}
		
		// ---------- register event listeners
		//handling player chatting
		final Identifier lastChatMessage = new Identifier(modId, "last_chat_message");
		final Identifier lastChatMessageTime = new Identifier(modId, "last_chat_message_time");
		PlayerManagerEvent.PLAYER_CHATTED.register((player, message) ->
		{
			//obtain last message data
			final var data = EntityHooks.getCustomData(player);
			final String lastMessage = data.getProperty(lastChatMessage, null);
			final long lastMessageTime = data.getPropertyOrDefault(lastChatMessageTime, -1L);
			
			final long currentTime = System.currentTimeMillis();
			data.setProperty(lastChatMessage, message);
			data.setProperty(lastChatMessageTime, currentTime);
			
			//do not increase stat if too short or too similar, so as to avoid spam promotion
			if(message.length() < 4 || __messagesTooSimilar(message, lastMessage))
				return;
			
			//do not increase stat if too short time period has passed, so as to avoid spam promotion
			if(currentTime - lastMessageTime < 1000)
				return;
			
			//increase stat value
			player.getStatHandler().increaseStat(player, CUSTOM.getOrCreateStat(CHAT_MESSAGES_SENT), 1);
		});
		
		//handling item sharing
		//TODO - Stat is intended to work with sharing with all entity types, and not just with players
		PlayerEntityEvent.ITEM_PICKED_UP.register((playerEntity, itemEntity, count) ->
		{
			final var throwerEntity = itemEntity.getOwner();
			
			//handle ITEMS_SHARED stat; receiving items from any non-null mob counts as sharing as well
			if(throwerEntity != playerEntity)
			do
			{
				//check for requirements
				if(count < 1 || !(playerEntity instanceof ServerPlayerEntity)) break;
				else if(!(throwerEntity instanceof Entity)) break;
				
				//grant stats
				final var player = (ServerPlayerEntity)playerEntity;
				player.getStatHandler().increaseStat(player, Stats.CUSTOM.getOrCreateStat(MStats.ITEMS_SHARED), count);
				if(throwerEntity instanceof ServerPlayerEntity)
				{
					final var thrower = (ServerPlayerEntity)throwerEntity;
					thrower.getStatHandler().increaseStat(thrower, Stats.CUSTOM.getOrCreateStat(MStats.ITEMS_SHARED), count);
				}
			}
			while(false);
		});
		
		//handling loot container openings
		PlayerEntityEvent.LOOT_CONTAINER_OPENED.register((player, container) ->
		{
			player.getStatHandler().increaseStat(player, Stats.CUSTOM.getOrCreateStat(LOOT_CONTAINERS_OPENED), 1);
		});
	}
	// ==================================================
	private static @Internal final Pattern __mtsPattern = Pattern.compile("[^s\\p{L}]");
	private static @Internal boolean __messagesTooSimilar(String a, String b)
	{
		//pass when the initial message is null
		if(a == null || b == null) return false;
		
		//compare messages and return result
		a = __mtsPattern.matcher(a.toLowerCase()).replaceAll("");
		b = __mtsPattern.matcher(b.toLowerCase()).replaceAll("");
		return Objects.equals(a, b);
	}
	// ==================================================
}