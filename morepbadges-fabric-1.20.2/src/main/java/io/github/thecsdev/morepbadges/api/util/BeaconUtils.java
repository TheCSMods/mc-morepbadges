package io.github.thecsdev.morepbadges.api.util;

import java.util.Objects;
import java.util.function.Predicate;

import net.minecraft.block.BeaconBlock;
import net.minecraft.block.BlockState;
import net.minecraft.registry.tag.BlockTags;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public final class BeaconUtils
{
	// ==================================================
	private BeaconUtils() {}
	// ==================================================
	/**
	 * Calculates the "level" for a {@link BeaconBlock} that should be present at the given coordinates.
	 * @param world The target {@link World}.
	 * @param x The placed {@link BeaconBlock}'s X coordinate.
	 * @param y The placed {@link BeaconBlock}'s Y coordinate.
	 * @param z The placed {@link BeaconBlock}'s Z coordinate.
	 */
	public static final int getBeaconLevel(World world, int x, int y, int z) throws NullPointerException
	{
		return getBeaconLevel(world, x, y, z, null);
	}
	
	/**
	 * Calculates the "level" for a {@link BeaconBlock} that should be present at the given coordinates.
	 * @param world The target {@link World}.
	 * @param x The placed {@link BeaconBlock}'s X coordinate.
	 * @param y The placed {@link BeaconBlock}'s Y coordinate.
	 * @param z The placed {@link BeaconBlock}'s Z coordinate.
	 * @param pyramidBlockPredicate Dictates what blocks the beacon pyramid is allowed to be made out of.
	 */
	public static final int getBeaconLevel(
			final World world, int x, int y, int z,
			Predicate<BlockState> pyramidBlockPredicate) throws NullPointerException
	{
		//null checks
		Objects.requireNonNull(world);
		if(pyramidBlockPredicate == null)
			pyramidBlockPredicate = (state -> state.isIn(BlockTags.BEACON_BASE_BLOCKS));
		
		//begin the scan
		int level = 0;
		for (int nextLevel = 1; nextLevel <= 4;)
		{
			int k = y - nextLevel;
			if (k < world.getBottomY())
				break;
			boolean layerPassed = true;
			for (int l = x - nextLevel; l <= x + nextLevel && layerPassed; l++)
			{
				for (int m = z - nextLevel; m <= z + nextLevel; m++)
				{
					if (!pyramidBlockPredicate.test(world.getBlockState(new BlockPos(l, k, m))))
					{
						layerPassed = false;
						break;
					}
				}
			}
			if (layerPassed)
			{
				level = nextLevel;
				nextLevel++;
			}
			else break; //i added this here as proper fallback
		}
		
		//return the result
		return level;
	}
	// ==================================================
}