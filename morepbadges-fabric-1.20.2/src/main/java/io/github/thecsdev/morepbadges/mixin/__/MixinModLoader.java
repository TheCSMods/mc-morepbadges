package io.github.thecsdev.morepbadges.mixin.__;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import io.github.thecsdev.morepbadges.MorePBadgesFabric;
import io.github.thecsdev.tcdcommons.api.util.integrity.SelfDefense;

@Mixin(value = MorePBadgesFabric.class, priority = 9001)
public abstract class MixinModLoader
{
	//`require = 0` is required, so as to only inject when there is one in the first place
	@Inject(method = "<clinit>", at = @At("HEAD"), cancellable = true, require = 0)
	private static void onClassInit(CallbackInfo callback)
	{
		SelfDefense.reportClassInitializer(MorePBadgesFabric.class);
		callback.cancel();
	}
}