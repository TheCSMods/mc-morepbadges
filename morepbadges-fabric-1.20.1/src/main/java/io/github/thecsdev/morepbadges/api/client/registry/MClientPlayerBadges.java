package io.github.thecsdev.morepbadges.api.client.registry;

import static io.github.thecsdev.morepbadges.api.registry.MPlayerBadges.BEACON_MASTER;
import static io.github.thecsdev.morepbadges.api.registry.MPlayerBadges.CELESTIAL_VOYAGER;
import static io.github.thecsdev.morepbadges.api.registry.MPlayerBadges.FATE_ALCHEMIST;
import static io.github.thecsdev.morepbadges.api.registry.MPlayerBadges.HAPPY_NEW_YEAR;
import static io.github.thecsdev.morepbadges.api.registry.MPlayerBadges.HIGHLY_EXPERIENCED;
import static io.github.thecsdev.morepbadges.api.registry.MPlayerBadges.LOOT_HUNTER;
import static io.github.thecsdev.morepbadges.client.MorePBadgesClient.MC_CLIENT;
import static io.github.thecsdev.tcdcommons.api.client.registry.TClientRegistries.PLAYER_BADGE_RENDERER;
import static io.github.thecsdev.tcdcommons.api.registry.TRegistries.PLAYER_BADGE;
import static io.github.thecsdev.tcdcommons.api.util.TextUtils.translatable;
import static net.minecraft.stat.Stats.CUSTOM;

import java.awt.Rectangle;
import java.util.Objects;

import org.jetbrains.annotations.ApiStatus.Experimental;
import org.jetbrains.annotations.ApiStatus.Internal;

import io.github.thecsdev.betterstats.api.client.badge.BSClientPlayerBadge;
import io.github.thecsdev.betterstats.api.util.io.IStatsProvider;
import io.github.thecsdev.morepbadges.MorePBadges;
import io.github.thecsdev.morepbadges.api.registry.MPlayerBadges;
import io.github.thecsdev.morepbadges.api.stat.MStats;
import io.github.thecsdev.tcdcommons.api.badge.PlayerBadge;
import io.github.thecsdev.tcdcommons.api.client.gui.util.UITexture;
import io.github.thecsdev.tcdcommons.api.client.render.badge.PBTextureRenderer;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.Items;
import net.minecraft.registry.Registries;
import net.minecraft.registry.tag.BlockTags;
import net.minecraft.registry.tag.ItemTags;
import net.minecraft.registry.tag.TagKey;
import net.minecraft.stat.StatType;
import net.minecraft.stat.Stats;
import net.minecraft.util.Identifier;

/**
 * {@link MorePBadges}'s client-side {@link PlayerBadge}s.
 * 
 * @apiNote At any time, one of these badges could become migrated to a "server-side" badge,
 * meaning there could be plans to rewrite a badge to not rely on client-side information.
 * Hence the {@link Experimental} annotation.
 * 
 * @apiNote If {@link PlayerBadge#getId()} returns {@code null}, that means the client-side
 * badge is no longer in use and has been replaced by a server-side one. <b>Be on the lookout for that.</b>
 */
@Experimental
public final class MClientPlayerBadges
{
	// ==================================================
	private MClientPlayerBadges() {}
	// ==================================================
	public static final BSClientPlayerBadge CHAT_ARTISAN;
	public static final BSClientPlayerBadge REDSTONE_WIZARD;
	public static final BSClientPlayerBadge REALM_ARCHITECT;
	public static final BSClientPlayerBadge COLLABORATIVE_SYNERGY;
	public static final BSClientPlayerBadge LOREKEEPER;
	public static final BSClientPlayerBadge WELL_ORGANIZED;
	public static final BSClientPlayerBadge NATURE_STEWARD;
	public static final BSClientPlayerBadge MYSTIC_FORGER;
	// --------------------------------------------------
	public static void register() {}
	static
	{
		//create badges
		final var chat_artisan = new BSClientPlayerBadge(
				translatable("morepbadges.api.client.registry.mclientplayerbadges.chat_artisan.title"),
				translatable("morepbadges.api.client.registry.mclientplayerbadges.chat_artisan.description"));
		final var redstone_wizard = new BSClientPlayerBadge(
				translatable("morepbadges.api.client.registry.mclientplayerbadges.redstone_wizard.title"),
				translatable("morepbadges.api.client.registry.mclientplayerbadges.redstone_wizard.description"));
		final var realm_architect = new BSClientPlayerBadge(
				translatable("morepbadges.api.client.registry.mclientplayerbadges.realm_architect.title"),
				translatable("morepbadges.api.client.registry.mclientplayerbadges.realm_architect.description"));
		final var collaborative_synergy = new BSClientPlayerBadge(
				translatable("morepbadges.api.client.registry.mclientplayerbadges.collaborative_synergy.title"),
				translatable("morepbadges.api.client.registry.mclientplayerbadges.collaborative_synergy.description"));
		final var lorekeeper = new BSClientPlayerBadge(
				translatable("morepbadges.api.client.registry.mclientplayerbadges.lorekeeper.title"),
				translatable("morepbadges.api.client.registry.mclientplayerbadges.lorekeeper.description"));
		final var well_organized = new BSClientPlayerBadge(
				translatable("morepbadges.api.client.registry.mclientplayerbadges.well_organized.title"),
				translatable("morepbadges.api.client.registry.mclientplayerbadges.well_organized.description"));
		final var nature_steward = new BSClientPlayerBadge(
				translatable("morepbadges.api.client.registry.mclientplayerbadges.nature_steward.title"),
				translatable("morepbadges.api.client.registry.mclientplayerbadges.nature_steward.description"));
		final var mystic_forger = new BSClientPlayerBadge(
				translatable("morepbadges.api.client.registry.mclientplayerbadges.mystic_forger.title"),
				translatable("morepbadges.api.client.registry.mclientplayerbadges.mystic_forger.description"));
		
		//set badge criteria
		chat_artisan.setStatCriteria(stats ->
		{
			//in single-player, require just one message. in multiplayer, it's more
			final int msg = stats.getStatValue(CUSTOM.getOrCreateStat(MStats.CHAT_MESSAGES_SENT));
			return MC_CLIENT.isInSingleplayer() ? ((msg > 0) ? 1 : 0) : (msg / 100);
		});
		redstone_wizard.setStatCriteria(stats ->
		{
			if(stats.getStatValue(Stats.USED, Items.REDSTONE) < 64 * 18 ||
					stats.getStatValue(Stats.USED, Items.PISTON) < 64 * 4 ||
					stats.getStatValue(Stats.USED, Items.HOPPER) < 64 * 2 ||
					stats.getStatValue(Stats.USED, Items.REDSTONE_BLOCK) < 64 ||
					stats.getStatValue(Stats.USED, Items.REPEATER) < 64 * 2 ||
					stats.getStatValue(Stats.USED, Items.LEVER) < 64)
				return 0;
			else return 1;
		});
		realm_architect.setStatCriteria(stats ->
		{
			//generally required items
			if(stats.getStatValue(Stats.USED, Items.CRAFTING_TABLE) < 16 ||
					stats.getStatValue(Stats.USED, Items.FURNACE) < 32 ||
					!__hasAllStatsInBlockTag(BlockTags.DOORS, stats, Stats.USED, 1))
				return 0;
			
			//check for various building styles (wood, stone, wool, concrete, and so on...)
			if(stats.getStatValue(Stats.USED, Items.STONE) >= 64 * 27 ||
					__hasAllStatsInBlockTag(BlockTags.PLANKS, stats, Stats.USED, 64 * 5) ||
					__hasAllStatsInBlockTag(BlockTags.STONE_BRICKS, stats, Stats.USED, 64 * 4) ||
					__hasAllStatsInBlockTag(BlockTags.PLANKS, stats, Stats.USED, 64 * 5) ||
					__hasAllStatsInBlockTag(BlockTags.WOOL, stats, Stats.USED, 64 * 5) /*||
					__hasAllStatsInBlockTag(BlockTags.CONCRETE_POWDER, stats, Stats.CRAFTED, 64 * 5) -- deprecated by Mojang*/)
				return 1;
			
			//by default, no badge
			return 0;
		});
		collaborative_synergy.setStatCriteria(stats ->
		{
			//required everywhere
			if(stats.getStatValue(Stats.CUSTOM, Stats.TRADED_WITH_VILLAGER) < 64 * 1 ||
					stats.getStatValue(Stats.CUSTOM, Stats.RAID_WIN) < 8 ||
					stats.getStatValue(Stats.CUSTOM, Stats.ANIMALS_BRED) < 16)
				return 0;
			
			//item sharing on servers
			if(!MC_CLIENT.isInSingleplayer())
				return stats.getStatValue(Stats.CUSTOM, MStats.ITEMS_SHARED) / (64*27);
			
			//one badge by default (single-player)
			return 1;
		});
		lorekeeper.setStatCriteria(stats ->
		{
			if(!__hasAllStatsInBlockTag(BlockTags.STANDING_SIGNS, stats, Stats.USED, 16) ||
					stats.getStatValue(Stats.USED, Items.WRITABLE_BOOK) < 64 ||
					stats.getStatValue(Stats.USED, Items.WRITTEN_BOOK) < 8)
				return 0;
			return 1;
		});
		well_organized.setStatCriteria(stats ->
		{
			if(!__hasAllStatsInBlockTag(BlockTags.SHULKER_BOXES, stats, Stats.USED, 32) ||
					__hasAllStatsInItemTag(ItemTags.CHEST_BOATS, stats, Stats.USED, 1) ||
					stats.getStatValue(Stats.USED, Items.CHEST) < 64 * 2 ||
					stats.getStatValue(Stats.USED, Items.TRAPPED_CHEST) < 1 ||
					stats.getStatValue(Stats.USED, Items.ENDER_CHEST) < 64 * 2 ||
					stats.getStatValue(Stats.USED, Items.CHEST_MINECART) < 1)
				return 0;
			return 1;
		});
		nature_steward.setStatCriteria(stats ->
		{
			if(!__hasAllStatsInItemTag(ItemTags.SAPLINGS, stats, Stats.USED, 64) ||
					//BlockTags.CROPS doesn't work, so i'll include them manually:
					stats.getStatValue(Stats.USED, Items.WHEAT_SEEDS) < 64 * 4)
				return 0;
			return 1;
		});
		mystic_forger.setStatCriteria(stats ->
		{
			if(stats.getStatValue(Stats.CUSTOM.getOrCreateStat(Stats.ENCHANT_ITEM)) < 64 * 2 ||
					stats.getStatValue(Stats.PICKED_UP, Items.LAPIS_LAZULI) < 64 * 7 ||
					stats.getStatValue(Stats.MINED, Blocks.LAPIS_ORE) < 64 ||
					stats.getStatValue(Stats.MINED, Blocks.DEEPSLATE_LAPIS_ORE) < 32)
				return 0;
			return 1;
		});
		
		//register badges
		final String modId = MorePBadges.getModID();
		PLAYER_BADGE.register(new Identifier(modId, "chat_artisan"), chat_artisan);
		PLAYER_BADGE.register(new Identifier(modId, "redstone_wizard"), redstone_wizard);
		PLAYER_BADGE.register(new Identifier(modId, "realm_architect"), realm_architect);
		PLAYER_BADGE.register(new Identifier(modId, "collaborative_synergy"), collaborative_synergy);
		PLAYER_BADGE.register(new Identifier(modId, "lorekeeper"), lorekeeper);
		PLAYER_BADGE.register(new Identifier(modId, "well_organized"), well_organized);
		PLAYER_BADGE.register(new Identifier(modId, "nature_steward"), nature_steward);
		PLAYER_BADGE.register(new Identifier(modId, "mystic_forger"), mystic_forger);
		
		//register badge renderers
		PLAYER_BADGE_RENDERER.register(chat_artisan.getId().get(), new PBTextureRenderer(new UITexture(MPlayerBadges.M_BADGES_TEXTURE, new Rectangle(0, 0, 20, 20))));
		PLAYER_BADGE_RENDERER.register(redstone_wizard.getId().get(), new PBTextureRenderer(new UITexture(MPlayerBadges.M_BADGES_TEXTURE, new Rectangle(20, 0, 20, 20))));
		PLAYER_BADGE_RENDERER.register(realm_architect.getId().get(), new PBTextureRenderer(new UITexture(MPlayerBadges.M_BADGES_TEXTURE, new Rectangle(40, 0, 20, 20))));
		PLAYER_BADGE_RENDERER.register(CELESTIAL_VOYAGER.getId().get(), new PBTextureRenderer(new UITexture(MPlayerBadges.M_BADGES_TEXTURE, new Rectangle(60, 0, 20, 20))));
		PLAYER_BADGE_RENDERER.register(collaborative_synergy.getId().get(), new PBTextureRenderer(new UITexture(MPlayerBadges.M_BADGES_TEXTURE, new Rectangle(80, 0, 20, 20))));
		PLAYER_BADGE_RENDERER.register(lorekeeper.getId().get(), new PBTextureRenderer(new UITexture(MPlayerBadges.M_BADGES_TEXTURE, new Rectangle(100, 0, 20, 20))));
		PLAYER_BADGE_RENDERER.register(well_organized.getId().get(), new PBTextureRenderer(new UITexture(MPlayerBadges.M_BADGES_TEXTURE, new Rectangle(120, 0, 20, 20))));
		PLAYER_BADGE_RENDERER.register(LOOT_HUNTER.getId().get(), new PBTextureRenderer(new UITexture(MPlayerBadges.M_BADGES_TEXTURE, new Rectangle(140, 0, 20, 20))));
		PLAYER_BADGE_RENDERER.register(nature_steward.getId().get(), new PBTextureRenderer(new UITexture(MPlayerBadges.M_BADGES_TEXTURE, new Rectangle(160, 0, 20, 20))));
		PLAYER_BADGE_RENDERER.register(FATE_ALCHEMIST.getId().get(), new PBTextureRenderer(new UITexture(MPlayerBadges.M_BADGES_TEXTURE, new Rectangle(180, 0, 20, 20))));
		PLAYER_BADGE_RENDERER.register(mystic_forger.getId().get(), new PBTextureRenderer(new UITexture(MPlayerBadges.M_BADGES_TEXTURE, new Rectangle(200, 0, 20, 20))));
		PLAYER_BADGE_RENDERER.register(HIGHLY_EXPERIENCED.getId().get(), new PBTextureRenderer(new UITexture(MPlayerBadges.M_BADGES_TEXTURE, new Rectangle(220, 0, 20, 20))));
		PLAYER_BADGE_RENDERER.register(BEACON_MASTER.getId().get(), new PBTextureRenderer(new UITexture(MPlayerBadges.M_BADGES_TEXTURE, new Rectangle(60, 180, 60, 60))));
		PLAYER_BADGE_RENDERER.register(HAPPY_NEW_YEAR.getId().get(), new PBTextureRenderer(new UITexture(MPlayerBadges.M_BADGES_TEXTURE, new Rectangle(0, 20, 20, 20))));
		
		//finally, assign values
		CHAT_ARTISAN          = chat_artisan;
		REDSTONE_WIZARD       = redstone_wizard;
		REALM_ARCHITECT       = realm_architect;
		COLLABORATIVE_SYNERGY = collaborative_synergy;
		LOREKEEPER            = lorekeeper;
		WELL_ORGANIZED        = well_organized;
		NATURE_STEWARD        = nature_steward;
		MYSTIC_FORGER         = mystic_forger;
	}
	// ==================================================
	private static final @Internal boolean __hasAllStatsInBlockTag(
			TagKey<Block> blockTag,
			IStatsProvider stats,
			StatType<Item> statType,
			int minValue)
	{
		//obtain entry list
		final var entryList = Registries.BLOCK.getEntryList(blockTag).orElse(null);
		if(entryList == null) return false;
		
		//check all items in the tag entry list
		for(final var reBlock : entryList)
		{
			//obtain item from block in the process
			if(!reBlock.hasKeyAndValue()) continue; //Minecraft throws when attempting to access null value
			final var block = reBlock.value();
			if(block == null) continue;
			final var item = block.asItem();
			if(item == null || Objects.equals(item, Items.AIR)) continue;
			
			//check item stat
			if(stats.getStatValue(statType, item) < minValue)
				return false;
		}
		
		//return true if all checks pass
		return true;
	}
	private static final @Internal boolean __hasAllStatsInItemTag(
			TagKey<Item> itemTag,
			IStatsProvider stats,
			StatType<Item> statType,
			int minValue)
	{
		//obtain entry list
		final var entryList = Registries.ITEM.getEntryList(itemTag).orElse(null);
		if(entryList == null) return false;
		
		//check all items in the tag entry list
		for(final var reItem : entryList)
		{
			//obtain item from block in the process
			if(!reItem.hasKeyAndValue()) continue; //Minecraft throws when attempting to access null value
			final var item = reItem.value();
			if(item == null || Objects.equals(item, Items.AIR)) continue;
			
			//check item stat
			if(stats.getStatValue(statType, item) < minValue)
				return false;
		}
		
		//return true if all checks pass
		return true;
	}
	// ==================================================
}