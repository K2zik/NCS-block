package ru.k1zik.ncsblock.mixin;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(targets = "com.noobfly.containersearcher.LibrarianRerollController", remap = false)
public abstract class LibrarianRerollControllerMixin {
	@Shadow(remap = false)
	private void stop(boolean restoreSlot, String messageKey) {
	}

	@Inject(method = "beginSelection", at = @At("RETURN"), remap = false)
	private void ncsblock$cancelSelection(CallbackInfo ci) {
		stop(false, null);
	}

	@Inject(method = {"handleEntityUse", "handleBlockUse"}, at = @At("HEAD"), cancellable = true, remap = false)
	private void ncsblock$ignoreClicks(CallbackInfoReturnable<Boolean> cir) {
		cir.setReturnValue(false);
	}

	@Inject(method = "tick", at = @At("HEAD"), cancellable = true, remap = false)
	private void ncsblock$noTick(CallbackInfo ci) {
		ci.cancel();
	}
}
