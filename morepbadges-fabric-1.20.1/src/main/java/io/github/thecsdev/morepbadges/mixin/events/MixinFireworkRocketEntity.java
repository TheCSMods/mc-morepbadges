package io.github.thecsdev.morepbadges.mixin.events;

import static io.github.thecsdev.morepbadges.api.registry.MPlayerBadges.HAPPY_NEW_YEAR;

import java.util.concurrent.ThreadLocalRandom;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import io.github.thecsdev.morepbadges.MorePBadgesConfig;
import io.github.thecsdev.morepbadges.MorePBadgesConfig.HolidayMode;
import io.github.thecsdev.morepbadges.api.registry.MPlayerBadges;
import net.minecraft.entity.projectile.FireworkRocketEntity;
import net.minecraft.entity.projectile.ProjectileEntity;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.util.math.Vec3i;

@Mixin(value = FireworkRocketEntity.class, priority = 9001)
public abstract class MixinFireworkRocketEntity
{
	private static final @Unique Vec3i SPAWN_POS = new Vec3i(0, 100, 0);
	protected abstract @Shadow boolean hasExplosionEffects();
	
	@Inject(method = "explodeAndRemove", at = @At("RETURN"))
	public void onExplodeAndRemove(CallbackInfo ci)
	{
		//only do this during the "New year" holiday;
		//during the holiday however, make sure it has explosion effects
		if(MorePBadgesConfig.HOLIDAY_MODE != HolidayMode.NEW_YEAR || !hasExplosionEffects())
			return;
		
		//obtain owner and make sure it's a server player
		final var owner = ((ProjectileEntity)(Object)this).getOwner();
		if(!(owner instanceof ServerPlayerEntity)) return;
		final var ownerPos = owner.getBlockPos();
		
		//initialize and calculate chance based on spawn distance
		int chance = 1;
		if(ownerPos.isWithinDistance(SPAWN_POS, 200))
			chance = 5;
		else if(ownerPos.isWithinDistance(SPAWN_POS, 500))
			chance = 3;
		else if(ownerPos.isWithinDistance(SPAWN_POS, 1000))
			chance = 2;
		
		//grant badge randomly
		if(ThreadLocalRandom.current().nextInt(1, 401) <= chance)
			return;
		HAPPY_NEW_YEAR.getId().ifPresent(id -> MPlayerBadges.tryGrantBadge((ServerPlayerEntity)owner, id));
	}
}