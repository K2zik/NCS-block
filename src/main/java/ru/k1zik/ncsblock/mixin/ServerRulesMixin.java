package ru.k1zik.ncsblock.mixin;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(targets = "com.noobfly.containersearcher.ServerRules", remap = false)
public abstract class ServerRulesMixin {
	@Inject(method = "rerollDisabled", at = @At("HEAD"), cancellable = true, remap = false)
	private static void ncsblock$blockReroll(CallbackInfoReturnable<Boolean> cir) {
		cir.setReturnValue(true);
	}
}
