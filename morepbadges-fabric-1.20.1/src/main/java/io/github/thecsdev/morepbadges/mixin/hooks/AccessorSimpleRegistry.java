package io.github.thecsdev.morepbadges.mixin.hooks;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

import net.minecraft.registry.SimpleRegistry;

@Mixin(SimpleRegistry.class)
public interface AccessorSimpleRegistry
{
	public @Accessor("frozen") boolean getFrozen();
	public @Accessor("frozen") void setFrozen(boolean frozen);
}