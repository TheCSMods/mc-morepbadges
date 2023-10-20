package io.github.thecsdev.morepbadges.mixin.events;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import io.github.thecsdev.morepbadges.api.stat.MStats;
import net.minecraft.stat.StatFormatter;
import net.minecraft.stat.Stats;
import net.minecraft.util.Identifier;

@Mixin(Stats.class)
public abstract class MixinStats
{
	private static @Unique boolean morepbadges_registered = false;
	
	@Inject(method = "register", at = @At("HEAD"))
	private static void onRegister(String id, StatFormatter statFormatter, CallbackInfoReturnable<Identifier> ci)
	{
		//prevent multiple invocations
		if(morepbadges_registered) return;
		morepbadges_registered = true;
		
		//register stats
		MStats.register();
	}
}